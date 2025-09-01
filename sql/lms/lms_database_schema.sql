-- Learning Management System Database Schema
-- Demonstrates all relationship types: 1:1, 1:M, M:M, Self-referencing, Weak entities
-- Updated: Separated auth data and course instances

-- ========================================
-- CORE ENTITIES
-- ========================================

-- Users table (basic profile information only)
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('student', 'instructor', 'admin')),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User authentication table (1:1 relationship with users)
CREATE TABLE user_auth (
    auth_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    salt VARCHAR(255),
    last_login TIMESTAMP,
    failed_login_attempts INTEGER DEFAULT 0,
    account_locked_until TIMESTAMP,
    password_reset_token VARCHAR(255),
    password_reset_expires TIMESTAMP,
    two_factor_secret VARCHAR(255),
    two_factor_enabled BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- User profiles (1:1 relationship with users)
CREATE TABLE user_profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    bio TEXT,
    avatar_url VARCHAR(500),
    phone VARCHAR(20),
    date_of_birth DATE,
    address TEXT,
    emergency_contact VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Departments table
CREATE TABLE departments (
    dept_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    description TEXT,
    head_of_dept INTEGER,
    budget DECIMAL(12,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (head_of_dept) REFERENCES users(user_id)
);

-- ========================================
-- COURSES AND CONTENT (SEPARATED DESIGN)
-- ========================================

-- Course definitions (template/catalog courses)
CREATE TABLE courses (
    course_id SERIAL PRIMARY KEY,
    dept_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    description TEXT,
    credits INTEGER NOT NULL DEFAULT 3,
    max_enrollment INTEGER DEFAULT 30,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

-- Course instances (actual offerings by semester/year)
CREATE TABLE course_instances (
    instance_id SERIAL PRIMARY KEY,
    course_id INTEGER NOT NULL,
    instructor_id INTEGER NOT NULL,
    semester VARCHAR(20) NOT NULL, -- 'Fall', 'Spring', 'Summer'
    year INTEGER NOT NULL,
    section VARCHAR(10) DEFAULT 'A',
    start_date DATE,
    end_date DATE,
    meeting_days VARCHAR(20), -- 'MWF', 'TTH', etc.
    meeting_time VARCHAR(20), -- '10:00-11:30'
    location VARCHAR(100),
    current_enrollment INTEGER DEFAULT 0,
    max_enrollment INTEGER, -- can override course default
    status VARCHAR(20) DEFAULT 'scheduled' CHECK (status IN ('scheduled', 'active', 'completed', 'cancelled')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES users(user_id),
    UNIQUE(course_id, semester, year, section)
);

-- Course prerequisites (Self-referencing M:M relationship on course definitions)
CREATE TABLE course_prerequisites (
    course_id INTEGER NOT NULL,
    prerequisite_course_id INTEGER NOT NULL,
    is_mandatory BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (course_id, prerequisite_course_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY (prerequisite_course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    CHECK (course_id != prerequisite_course_id)
);

-- Lessons table (1:M relationship with course instances)
CREATE TABLE lessons (
    lesson_id SERIAL PRIMARY KEY,
    instance_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    video_url VARCHAR(500),
    order_num INTEGER NOT NULL,
    duration_minutes INTEGER,
    scheduled_date DATE,
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instance_id) REFERENCES course_instances(instance_id) ON DELETE CASCADE
);

-- ========================================
-- MANY-TO-MANY RELATIONSHIPS
-- ========================================

-- Student enrollments (M:M relationship between users and course instances)
CREATE TABLE enrollments (
    enrollment_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    instance_id INTEGER NOT NULL,
    enrollment_date DATE DEFAULT CURRENT_DATE,
    completion_date DATE,
    final_grade DECIMAL(5,2),
    grade_letter VARCHAR(2),
    status VARCHAR(20) DEFAULT 'enrolled' CHECK (status IN ('enrolled', 'completed', 'dropped', 'failed', 'withdrawn')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, instance_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (instance_id) REFERENCES course_instances(instance_id) ON DELETE CASCADE
);

-- Tags for courses (applied to course definitions)
CREATE TABLE tags (
    tag_id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    color VARCHAR(7), -- hex color code
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Course tags (M:M relationship between courses and tags)
CREATE TABLE course_tags (
    course_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (course_id, tag_id),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

-- ========================================
-- SELF-REFERENCING RELATIONSHIPS
-- ========================================

-- User mentorship (Self-referencing relationship)
CREATE TABLE user_mentorships (
    mentorship_id SERIAL PRIMARY KEY,
    mentor_id INTEGER NOT NULL,
    mentee_id INTEGER NOT NULL,
    start_date DATE DEFAULT CURRENT_DATE,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'completed', 'terminated')),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mentor_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (mentee_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CHECK (mentor_id != mentee_id),
    UNIQUE(mentor_id, mentee_id)
);

-- ========================================
-- WEAK ENTITIES AND DEPENDENT RELATIONSHIPS
-- ========================================

-- Assignments table (belongs to course instances)
CREATE TABLE assignments (
    assignment_id SERIAL PRIMARY KEY,
    instance_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date TIMESTAMP NOT NULL,
    max_points DECIMAL(5,2) DEFAULT 100.00,
    assignment_type VARCHAR(50) DEFAULT 'homework',
    instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instance_id) REFERENCES course_instances(instance_id) ON DELETE CASCADE
);

-- Submissions (Weak entity - depends on assignments)
CREATE TABLE submissions (
    submission_id SERIAL PRIMARY KEY,
    assignment_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    submission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    file_url VARCHAR(500),
    content TEXT,
    points_earned DECIMAL(5,2),
    feedback TEXT,
    is_late BOOLEAN DEFAULT FALSE,
    attempts INTEGER DEFAULT 1,
    status VARCHAR(20) DEFAULT 'submitted' CHECK (status IN ('draft', 'submitted', 'graded', 'returned')),
    graded_at TIMESTAMP,
    graded_by INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(assignment_id, user_id),
    FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (graded_by) REFERENCES users(user_id)
);

-- ========================================
-- ADDITIONAL SUPPORTING TABLES
-- ========================================

-- Discussion forums for course instances
CREATE TABLE forums (
    forum_id SERIAL PRIMARY KEY,
    instance_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instance_id) REFERENCES course_instances(instance_id) ON DELETE CASCADE
);

-- Forum posts
CREATE TABLE forum_posts (
    post_id SERIAL PRIMARY KEY,
    forum_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    parent_post_id INTEGER, -- for threaded discussions
    title VARCHAR(255),
    content TEXT NOT NULL,
    is_pinned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (forum_id) REFERENCES forums(forum_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (parent_post_id) REFERENCES forum_posts(post_id) ON DELETE CASCADE
);

-- ========================================
-- INDEXES FOR PERFORMANCE
-- ========================================

-- Users table indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);

-- User auth table indexes
CREATE INDEX idx_user_auth_user_id ON user_auth(user_id);
CREATE INDEX idx_user_auth_reset_token ON user_auth(password_reset_token);

-- Courses table indexes
CREATE INDEX idx_courses_dept_id ON courses(dept_id);
CREATE INDEX idx_courses_code ON courses(code);

-- Course instances table indexes
CREATE INDEX idx_course_instances_course_id ON course_instances(course_id);
CREATE INDEX idx_course_instances_instructor_id ON course_instances(instructor_id);
CREATE INDEX idx_course_instances_semester_year ON course_instances(semester, year);
CREATE INDEX idx_course_instances_status ON course_instances(status);

-- Enrollments table indexes
CREATE INDEX idx_enrollments_user_id ON enrollments(user_id);
CREATE INDEX idx_enrollments_instance_id ON enrollments(instance_id);
CREATE INDEX idx_enrollments_status ON enrollments(status);

-- Lessons table indexes
CREATE INDEX idx_lessons_instance_id ON lessons(instance_id);
CREATE INDEX idx_lessons_order ON lessons(instance_id, order_num);
CREATE INDEX idx_lessons_scheduled_date ON lessons(scheduled_date);

-- Assignments table indexes
CREATE INDEX idx_assignments_instance_id ON assignments(instance_id);
CREATE INDEX idx_assignments_due_date ON assignments(due_date);

-- Submissions table indexes
CREATE INDEX idx_submissions_assignment_id ON submissions(assignment_id);
CREATE INDEX idx_submissions_user_id ON submissions(user_id);
CREATE INDEX idx_submissions_status ON submissions(status);