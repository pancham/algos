-- Learning Management System Sample Data Insert Script
-- This script inserts sufficient records for GROUP BY and HAVING queries
-- Execute after running lms_database_schema.sql

-- ========================================
-- DEPARTMENTS DATA
-- ========================================
INSERT INTO departments (name, code, description, budget) VALUES
('Computer Science', 'CS', 'Department of Computer Science and Software Engineering', 500000.00),
('Mathematics', 'MATH', 'Department of Mathematics and Statistics', 350000.00),
('Physics', 'PHYS', 'Department of Physics and Astronomy', 400000.00),
('Business', 'BUS', 'School of Business Administration', 600000.00),
('English', 'ENG', 'Department of English Literature and Writing', 250000.00),
('Psychology', 'PSY', 'Department of Psychology and Behavioral Sciences', 300000.00),
('Chemistry', 'CHEM', 'Department of Chemistry and Biochemistry', 450000.00),
('History', 'HIST', 'Department of History and Social Sciences', 200000.00);

-- ========================================
-- USERS DATA (Multiple roles with realistic distribution)
-- ========================================
INSERT INTO users (email, username, role, first_name, last_name, is_active) VALUES
-- Administrators
('admin@university.edu', 'admin', 'admin', 'Sarah', 'Johnson', true),
('dean@university.edu', 'dean_smith', 'admin', 'Robert', 'Smith', true),

-- Instructors (professors and lecturers)
('j.anderson@university.edu', 'prof_anderson', 'instructor', 'John', 'Anderson', true),
('m.williams@university.edu', 'prof_williams', 'instructor', 'Maria', 'Williams', true),
('d.brown@university.edu', 'prof_brown', 'instructor', 'David', 'Brown', true),
('l.davis@university.edu', 'prof_davis', 'instructor', 'Lisa', 'Davis', true),
('r.wilson@university.edu', 'prof_wilson', 'instructor', 'Robert', 'Wilson', true),
('k.taylor@university.edu', 'prof_taylor', 'instructor', 'Karen', 'Taylor', true),
('m.moore@university.edu', 'prof_moore', 'instructor', 'Michael', 'Moore', true),
('s.jackson@university.edu', 'prof_jackson', 'instructor', 'Sandra', 'Jackson', true),
('t.white@university.edu', 'prof_white', 'instructor', 'Thomas', 'White', true),
('n.harris@university.edu', 'prof_harris', 'instructor', 'Nancy', 'Harris', true),
('p.martin@university.edu', 'prof_martin', 'instructor', 'Paul', 'Martin', true),
('a.thompson@university.edu', 'prof_thompson', 'instructor', 'Alice', 'Thompson', true),

-- Students (larger group for realistic enrollment patterns)
('alex.student@university.edu', 'alex_s', 'student', 'Alex', 'Student', true),
('emma.jones@university.edu', 'emma_j', 'student', 'Emma', 'Jones', true),
('liam.garcia@university.edu', 'liam_g', 'student', 'Liam', 'Garcia', true),
('olivia.martinez@university.edu', 'olivia_m', 'student', 'Olivia', 'Martinez', true),
('noah.rodriguez@university.edu', 'noah_r', 'student', 'Noah', 'Rodriguez', true),
('sophia.lopez@university.edu', 'sophia_l', 'student', 'Sophia', 'Lopez', true),
('mason.gonzalez@university.edu', 'mason_g', 'student', 'Mason', 'Gonzalez', true),
('isabella.perez@university.edu', 'isabella_p', 'student', 'Isabella', 'Perez', true),
('william.sanchez@university.edu', 'william_s', 'student', 'William', 'Sanchez', true),
('ava.torres@university.edu', 'ava_t', 'student', 'Ava', 'Torres', true),
('james.rivera@university.edu', 'james_r', 'student', 'James', 'Rivera', true),
('mia.cooper@university.edu', 'mia_c', 'student', 'Mia', 'Cooper', true),
('benjamin.reed@university.edu', 'benjamin_r', 'student', 'Benjamin', 'Reed', true),
('charlotte.bailey@university.edu', 'charlotte_b', 'student', 'Charlotte', 'Bailey', true),
('lucas.cox@university.edu', 'lucas_c', 'student', 'Lucas', 'Cox', true),
('harper.ward@university.edu', 'harper_w', 'student', 'Harper', 'Ward', true),
('henry.torres@university.edu', 'henry_t', 'student', 'Henry', 'Torres', true),
('amelia.gray@university.edu', 'amelia_g', 'student', 'Amelia', 'Gray', true),
('alexander.james@university.edu', 'alex_j', 'student', 'Alexander', 'James', true),
('evelyn.watson@university.edu', 'evelyn_w', 'student', 'Evelyn', 'Watson', true),
('sebastian.brooks@university.edu', 'sebastian_b', 'student', 'Sebastian', 'Brooks', true),
('abigail.kelly@university.edu', 'abigail_k', 'student', 'Abigail', 'Kelly', true),
('jack.sanders@university.edu', 'jack_s', 'student', 'Jack', 'Sanders', true),
('emily.price@university.edu', 'emily_p', 'student', 'Emily', 'Price', true),
('owen.bennett@university.edu', 'owen_b', 'student', 'Owen', 'Bennett', true),
('elizabeth.wood@university.edu', 'elizabeth_w', 'student', 'Elizabeth', 'Wood', true),
('luke.barnes@university.edu', 'luke_b', 'student', 'Luke', 'Barnes', true),
('sofia.ross@university.edu', 'sofia_r', 'student', 'Sofia', 'Ross', true),
('daniel.henderson@university.edu', 'daniel_h', 'student', 'Daniel', 'Henderson', true),
('avery.coleman@university.edu', 'avery_c', 'student', 'Avery', 'Coleman', true),
('matthew.jenkins@university.edu', 'matthew_j', 'student', 'Matthew', 'Jenkins', true),
('ella.perry@university.edu', 'ella_p', 'student', 'Ella', 'Perry', true),
('jackson.long@university.edu', 'jackson_l', 'student', 'Jackson', 'Long', true),
('scarlett.hughes@university.edu', 'scarlett_h', 'student', 'Scarlett', 'Hughes', true),
('aiden.flores@university.edu', 'aiden_f', 'student', 'Aiden', 'Flores', true),
('grace.washington@university.edu', 'grace_w', 'student', 'Grace', 'Washington', true),
('samuel.butler@university.edu', 'samuel_b', 'student', 'Samuel', 'Butler', true),
('chloe.simmons@university.edu', 'chloe_s', 'student', 'Chloe', 'Simmons', true),
('joseph.foster@university.edu', 'joseph_f', 'student', 'Joseph', 'Foster', true),
('victoria.gonzales@university.edu', 'victoria_g', 'student', 'Victoria', 'Gonzales', true),
('david.bryant@university.edu', 'david_b', 'student', 'David', 'Bryant', true);

-- Update departments with heads (must be done after users are inserted)
UPDATE departments SET head_of_dept = 3 WHERE code = 'CS';    -- Prof Anderson
UPDATE departments SET head_of_dept = 4 WHERE code = 'MATH';  -- Prof Williams
UPDATE departments SET head_of_dept = 5 WHERE code = 'PHYS';  -- Prof Brown
UPDATE departments SET head_of_dept = 6 WHERE code = 'BUS';   -- Prof Davis
UPDATE departments SET head_of_dept = 7 WHERE code = 'ENG';   -- Prof Wilson
UPDATE departments SET head_of_dept = 8 WHERE code = 'PSY';   -- Prof Taylor
UPDATE departments SET head_of_dept = 9 WHERE code = 'CHEM';  -- Prof Moore
UPDATE departments SET head_of_dept = 10 WHERE code = 'HIST'; -- Prof Jackson

-- ========================================
-- USER AUTHENTICATION DATA
-- ========================================
INSERT INTO user_auth (user_id, password_hash, salt, last_login, failed_login_attempts) 
SELECT user_id, 
       'hashed_password_' || user_id, 
       'salt_' || user_id,
       CASE 
           WHEN user_id % 3 = 0 THEN CURRENT_TIMESTAMP - INTERVAL '1 day'
           WHEN user_id % 3 = 1 THEN CURRENT_TIMESTAMP - INTERVAL '3 days'
           ELSE CURRENT_TIMESTAMP - INTERVAL '1 week'
       END,
       CASE WHEN user_id % 10 = 0 THEN 2 ELSE 0 END
FROM users;

-- ========================================
-- USER PROFILES DATA
-- ========================================
INSERT INTO user_profiles (user_id, bio, phone, date_of_birth, address) 
SELECT user_id,
       'Bio for ' || first_name || ' ' || last_name,
       '555-' || LPAD((user_id * 123)::text, 4, '0') || '-' || LPAD((user_id * 456)::text, 4, '0'),
       DATE '1980-01-01' + (user_id * 100 || ' days')::INTERVAL,
       (user_id * 123) || ' University Ave, City, State'
FROM users;

-- ========================================
-- COURSES DATA
-- ========================================
INSERT INTO courses (dept_id, title, code, description, credits, max_enrollment) VALUES
-- Computer Science Courses
(1, 'Introduction to Programming', 'CS101', 'Basic programming concepts using Python', 3, 40),
(1, 'Data Structures and Algorithms', 'CS201', 'Fundamental data structures and algorithms', 4, 35),
(1, 'Object-Oriented Programming', 'CS202', 'Advanced programming using OOP principles', 3, 30),
(1, 'Database Systems', 'CS301', 'Database design and implementation', 3, 25),
(1, 'Web Development', 'CS302', 'Frontend and backend web development', 3, 30),
(1, 'Machine Learning', 'CS401', 'Introduction to machine learning algorithms', 4, 20),
(1, 'Software Engineering', 'CS402', 'Software development lifecycle and methodologies', 3, 25),

-- Mathematics Courses
(2, 'Calculus I', 'MATH101', 'Differential calculus', 4, 50),
(2, 'Calculus II', 'MATH102', 'Integral calculus', 4, 45),
(2, 'Linear Algebra', 'MATH201', 'Vector spaces and linear transformations', 3, 35),
(2, 'Statistics', 'MATH202', 'Descriptive and inferential statistics', 3, 40),
(2, 'Discrete Mathematics', 'MATH203', 'Logic, sets, and combinatorics', 3, 30),

-- Physics Courses
(3, 'General Physics I', 'PHYS101', 'Mechanics and thermodynamics', 4, 40),
(3, 'General Physics II', 'PHYS102', 'Electricity and magnetism', 4, 35),
(3, 'Modern Physics', 'PHYS201', 'Quantum mechanics and relativity', 3, 25),

-- Business Courses
(4, 'Introduction to Business', 'BUS101', 'Fundamentals of business operations', 3, 50),
(4, 'Accounting Principles', 'BUS201', 'Basic accounting concepts', 3, 35),
(4, 'Marketing Fundamentals', 'BUS202', 'Marketing strategies and consumer behavior', 3, 40),
(4, 'Operations Management', 'BUS301', 'Business process optimization', 3, 30),

-- English Courses
(5, 'Composition I', 'ENG101', 'Academic writing and rhetoric', 3, 25),
(5, 'Literature Survey', 'ENG201', 'Survey of world literature', 3, 30),
(5, 'Creative Writing', 'ENG301', 'Fiction and poetry writing workshop', 3, 20),

-- Psychology Courses
(6, 'General Psychology', 'PSY101', 'Introduction to psychological principles', 3, 45),
(6, 'Developmental Psychology', 'PSY201', 'Human development across lifespan', 3, 35),
(6, 'Abnormal Psychology', 'PSY301', 'Mental health and psychological disorders', 3, 30),

-- Chemistry Courses
(7, 'General Chemistry I', 'CHEM101', 'Basic chemical principles', 4, 40),
(7, 'General Chemistry II', 'CHEM102', 'Advanced chemical concepts', 4, 35),
(7, 'Organic Chemistry', 'CHEM201', 'Chemistry of carbon compounds', 4, 25),

-- History Courses
(8, 'World History', 'HIST101', 'Survey of world civilizations', 3, 40),
(8, 'American History', 'HIST201', 'United States history', 3, 35),
(8, 'Modern European History', 'HIST301', 'Europe from 1800 to present', 3, 25);

-- ========================================
-- COURSE PREREQUISITES
-- ========================================
INSERT INTO course_prerequisites (course_id, prerequisite_course_id, is_mandatory) VALUES
-- CS Prerequisites
(2, 1, true),   -- CS201 requires CS101
(3, 1, true),   -- CS202 requires CS101
(4, 2, true),   -- CS301 requires CS201
(5, 2, true),   -- CS302 requires CS201
(6, 2, true),   -- CS401 requires CS201
(7, 3, true),   -- CS402 requires CS202
(6, 13, true),  -- CS401 requires MATH203

-- Math Prerequisites
(9, 8, true),   -- MATH102 requires MATH101
(10, 8, true),  -- MATH201 requires MATH101
(11, 8, false), -- MATH202 recommends MATH101
(13, 10, true), -- MATH203 requires MATH201

-- Physics Prerequisites
(15, 8, true),  -- PHYS101 requires MATH101
(16, 15, true), -- PHYS102 requires PHYS101
(16, 8, true),  -- PHYS102 requires MATH101
(17, 16, true); -- PHYS201 requires PHYS102

-- ========================================
-- COURSE INSTANCES (Multiple semesters and sections)
-- ========================================
INSERT INTO course_instances (course_id, instructor_id, semester, year, section, start_date, end_date, meeting_days, meeting_time, location, max_enrollment, status) VALUES
-- Fall 2023 Instances
(1, 3, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'MWF', '9:00-9:50', 'CS-101', 40, 'completed'),
(1, 3, 'Fall', 2023, 'B', '2023-08-20', '2023-12-15', 'MWF', '11:00-11:50', 'CS-102', 40, 'completed'),
(2, 4, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'TTH', '10:00-11:30', 'CS-201', 35, 'completed'),
(3, 5, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'MWF', '2:00-2:50', 'CS-103', 30, 'completed'),
(8, 6, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'MWF', '8:00-8:50', 'MATH-101', 50, 'completed'),
(8, 6, 'Fall', 2023, 'B', '2023-08-20', '2023-12-15', 'MWF', '10:00-10:50', 'MATH-102', 50, 'completed'),
(14, 7, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'TTH', '1:00-2:30', 'PHYS-101', 40, 'completed'),
(18, 8, 'Fall', 2023, 'A', '2023-08-20', '2023-12-15', 'MWF', '9:00-9:50', 'BUS-201', 50, 'completed'),

-- Spring 2024 Instances
(1, 3, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'MWF', '9:00-9:50', 'CS-101', 40, 'completed'),
(2, 4, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'TTH', '10:00-11:30', 'CS-201', 35, 'completed'),
(2, 5, 'Spring', 2024, 'B', '2024-01-15', '2024-05-10', 'TTH', '1:00-2:30', 'CS-202', 35, 'completed'),
(4, 9, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'MWF', '11:00-11:50', 'CS-301', 25, 'completed'),
(9, 6, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'MWF', '8:00-8:50', 'MATH-101', 45, 'completed'),
(10, 10, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'TTH', '9:00-10:30', 'MATH-201', 35, 'completed'),
(15, 7, 'Spring', 2024, 'A', '2024-01-15', '2024-05-10', 'TTH', '1:00-2:30', 'PHYS-102', 35, 'completed'),

-- Fall 2024 Instances (Current/Recent)
(1, 3, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'MWF', '9:00-9:50', 'CS-101', 40, 'active'),
(1, 11, 'Fall', 2024, 'B', '2024-08-19', '2024-12-14', 'MWF', '11:00-11:50', 'CS-102', 40, 'active'),
(2, 4, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'TTH', '10:00-11:30', 'CS-201', 35, 'active'),
(3, 5, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'MWF', '2:00-2:50', 'CS-103', 30, 'active'),
(5, 12, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'TTH', '2:00-3:30', 'CS-302', 30, 'active'),
(6, 13, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'MWF', '1:00-2:30', 'CS-401', 20, 'active'),
(8, 6, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'MWF', '8:00-8:50', 'MATH-101', 50, 'active'),
(11, 10, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'TTH', '11:00-12:30', 'MATH-202', 40, 'active'),
(14, 7, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'TTH', '1:00-2:30', 'PHYS-101', 40, 'active'),
(24, 8, 'Fall', 2024, 'A', '2024-08-19', '2024-12-14', 'MWF', '10:00-10:50', 'PSY-101', 45, 'active');

-- ========================================
-- ENROLLMENTS (Realistic enrollment patterns)
-- ========================================
INSERT INTO enrollments (user_id, instance_id, enrollment_date, completion_date, final_grade, grade_letter, status) VALUES
-- Fall 2023 enrollments (completed courses)
-- CS101-Fall23-A (instance_id: 1)
(15, 1, '2023-08-15', '2023-12-15', 87.5, 'B+', 'completed'),
(16, 1, '2023-08-15', '2023-12-15', 92.0, 'A-', 'completed'),
(17, 1, '2023-08-15', '2023-12-15', 78.5, 'C+', 'completed'),
(18, 1, '2023-08-15', '2023-12-15', 95.0, 'A', 'completed'),
(19, 1, '2023-08-15', '2023-12-15', 83.0, 'B', 'completed'),
(20, 1, '2023-08-15', '2023-12-15', 88.5, 'B+', 'completed'),
(21, 1, '2023-08-15', '2023-12-15', 76.0, 'C', 'completed'),
(22, 1, '2023-08-15', '2023-12-15', 91.5, 'A-', 'completed'),
(23, 1, '2023-08-15', '2023-12-15', 85.0, 'B', 'completed'),
(24, 1, '2023-08-15', '2023-12-15', 89.0, 'B+', 'completed'),

-- CS101-Fall23-B (instance_id: 2)
(25, 2, '2023-08-15', '2023-12-15', 82.0, 'B-', 'completed'),
(26, 2, '2023-08-15', '2023-12-15', 94.5, 'A', 'completed'),
(27, 2, '2023-08-15', '2023-12-15', 77.0, 'C+', 'completed'),
(28, 2, '2023-08-15', '2023-12-15', 86.5, 'B', 'completed'),
(29, 2, '2023-08-15', '2023-12-15', 90.0, 'A-', 'completed'),
(30, 2, '2023-08-15', '2023-12-15', 79.5, 'C+', 'completed'),
(31, 2, '2023-08-15', '2023-12-15', 93.0, 'A', 'completed'),
(32, 2, '2023-08-15', '2023-12-15', 81.5, 'B-', 'completed'),

-- CS201-Fall23-A (instance_id: 3)
(16, 3, '2023-08-15', '2023-12-15', 88.0, 'B+', 'completed'),
(18, 3, '2023-08-15', '2023-12-15', 93.5, 'A', 'completed'),
(20, 3, '2023-08-15', '2023-12-15', 85.5, 'B', 'completed'),
(22, 3, '2023-08-15', '2023-12-15', 91.0, 'A-', 'completed'),
(26, 3, '2023-08-15', '2023-12-15', 87.0, 'B+', 'completed'),
(29, 3, '2023-08-15', '2023-12-15', 89.5, 'B+', 'completed'),
(31, 3, '2023-08-15', '2023-12-15', 92.5, 'A', 'completed'),

-- MATH101-Fall23-A (instance_id: 5)
(15, 5, '2023-08-15', '2023-12-15', 84.0, 'B', 'completed'),
(17, 5, '2023-08-15', '2023-12-15', 79.0, 'C+', 'completed'),
(19, 5, '2023-08-15', '2023-12-15', 91.5, 'A-', 'completed'),
(21, 5, '2023-08-15', '2023-12-15', 86.5, 'B', 'completed'),
(23, 5, '2023-08-15', '2023-12-15', 88.0, 'B+', 'completed'),
(33, 5, '2023-08-15', '2023-12-15', 92.0, 'A-', 'completed'),
(34, 5, '2023-08-15', '2023-12-15', 87.5, 'B+', 'completed'),
(35, 5, '2023-08-15', '2023-12-15', 83.5, 'B', 'completed'),

-- Spring 2024 enrollments (completed)
-- CS201-Spring24-A (instance_id: 10)
(15, 10, '2024-01-10', '2024-05-10', 89.0, 'B+', 'completed'),
(17, 10, '2024-01-10', '2024-05-10', 85.5, 'B', 'completed'),
(19, 10, '2024-01-10', '2024-05-10', 93.0, 'A', 'completed'),
(21, 10, '2024-01-10', '2024-05-10', 87.5, 'B+', 'completed'),
(23, 10, '2024-01-10', '2024-05-10', 91.0, 'A-', 'completed'),

-- Fall 2024 enrollments (current)
-- CS101-Fall24-A (instance_id: 15)
(36, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(37, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(38, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(39, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(40, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(41, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(42, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(43, 15, '2024-08-15', NULL, NULL, NULL, 'enrolled'),

-- CS101-Fall24-B (instance_id: 16)
(44, 16, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(45, 16, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(46, 16, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(47, 16, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(48, 16, '2024-08-15', NULL, NULL, NULL, 'enrolled'),

-- CS201-Fall24-A (instance_id: 17)
(16, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(18, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(20, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(22, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(26, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(29, 17, '2024-08-15', NULL, NULL, NULL, 'enrolled'),

-- Advanced courses
(18, 20, '2024-08-15', NULL, NULL, NULL, 'enrolled'), -- CS401 Machine Learning
(22, 20, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(26, 20, '2024-08-15', NULL, NULL, NULL, 'enrolled'),
(31, 20, '2024-08-15', NULL, NULL, NULL, 'enrolled');

-- ========================================
-- TAGS FOR COURSES
-- ========================================
INSERT INTO tags (name, description, color) VALUES
('Programming', 'Courses involving programming languages', '#FF5733'),
('Mathematics', 'Mathematical concepts and applications', '#3366FF'),
('Theory', 'Theoretical computer science concepts', '#9933FF'),
('Practical', 'Hands-on practical courses', '#33FF66'),
('Beginner', 'Introductory level courses', '#FFFF33'),
('Intermediate', 'Intermediate level courses', '#FF9933'),
('Advanced', 'Advanced level courses', '#FF3333'),
('Science', 'Science related courses', '#33FFFF'),
('Liberal Arts', 'Liberal arts and humanities', '#FF33FF'),
('Business', 'Business and management courses', '#66FF33');

-- ========================================
-- COURSE TAGS RELATIONSHIPS
-- ========================================
INSERT INTO course_tags (course_id, tag_id) VALUES
-- CS Courses
(1, 1), (1, 5),  -- CS101: Programming, Beginner
(2, 1), (2, 3), (2, 6),  -- CS201: Programming, Theory, Intermediate
(3, 1), (3, 6),  -- CS202: Programming, Intermediate
(4, 1), (4, 4), (4, 6),  -- CS301: Programming, Practical, Intermediate
(5, 1), (5, 4), (5, 6),  -- CS302: Programming, Practical, Intermediate
(6, 1), (6, 2), (6, 7),  -- CS401: Programming, Mathematics, Advanced
(7, 4), (7, 7),  -- CS402: Practical, Advanced

-- Math Courses
(8, 2), (8, 5),   -- MATH101: Mathematics, Beginner
(9, 2), (9, 6),   -- MATH102: Mathematics, Intermediate
(10, 2), (10, 6), -- MATH201: Mathematics, Intermediate
(11, 2), (11, 4), (11, 6), -- MATH202: Mathematics, Practical, Intermediate
(13, 2), (13, 3), (13, 6), -- MATH203: Mathematics, Theory, Intermediate

-- Physics Courses
(14, 8), (14, 5), (14, 2), -- PHYS101: Science, Beginner, Mathematics
(15, 8), (15, 6), (15, 2), -- PHYS102: Science, Intermediate, Mathematics
(17, 8), (17, 7), (17, 2), -- PHYS201: Science, Advanced, Mathematics

-- Business Courses
(18, 10), (18, 5), -- BUS101: Business, Beginner
(19, 10), (19, 6), -- BUS201: Business, Intermediate
(20, 10), (20, 4), (20, 6), -- BUS202: Business, Practical, Intermediate
(21, 10), (21, 4), (21, 7), -- BUS301: Business, Practical, Advanced

-- English Courses
(22, 9), (22, 5), -- ENG101: Liberal Arts, Beginner
(23, 9), (23, 6), -- ENG201: Liberal Arts, Intermediate
(24, 9), (24, 4), (24, 7), -- ENG301: Liberal Arts, Practical, Advanced

-- Psychology Courses
(25, 8), (25, 5), -- PSY101: Science, Beginner
(26, 8), (26, 6), -- PSY201: Science, Intermediate
(27, 8), (27, 7), -- PSY301: Science, Advanced

-- Chemistry Courses
(28, 8), (28, 5), -- CHEM101: Science, Beginner
(29, 8), (29, 6), -- CHEM102: Science, Intermediate
(30, 8), (30, 7), -- CHEM201: Science, Advanced

-- History Courses
(31, 9), (31, 5), -- HIST101: Liberal Arts, Beginner
(32, 9), (32, 6), -- HIST201: Liberal Arts, Intermediate
(33, 9), (33, 7); -- HIST301: Liberal Arts, Advanced

-- ========================================
-- USER MENTORSHIPS (Self-referencing relationships)
-- ========================================
INSERT INTO user_mentorships (mentor_id, mentee_id, start_date, end_date, status, notes) VALUES
-- Senior students mentoring juniors
(18, 36, '2024-01-15', NULL, 'active', 'Academic mentorship for CS program'),
(18, 37, '2024-01-15', NULL, 'active', 'Career guidance and study skills'),
(22, 38, '2024-02-01', NULL, 'active', 'Research mentorship'),
(26, 39, '2023-09-01', '2024-05-15', 'completed', 'Completed successful mentorship'),
(29, 40, '2024-01-10', NULL, 'active', 'Math tutoring and academic support'),
(31, 41, '2024-03-01', NULL, 'active', 'Programming project collaboration'),
(20, 42, '2023-08-20', '2024-01-15', 'completed', 'First semester orientation'),
(23, 43, '2024-02-15', NULL, 'active', 'Study group leadership'),
(19, 44, '2024-08-15', NULL, 'active', 'New student orientation'),
(21, 45, '2024-08-15', NULL, 'active', 'Academic planning assistance');

-- ========================================
-- LESSONS FOR COURSE INSTANCES
-- ========================================
INSERT INTO lessons (instance_id, title, content, video_url, order_num, duration_minutes, scheduled_date, is_published) VALUES
-- CS101-Fall24-A lessons (instance_id: 15)
(15, 'Introduction to Programming', 'Overview of programming concepts and Python basics', 'https://video.university.edu/cs101-intro', 1, 75, '2024-08-21', true),
(15, 'Variables and Data Types', 'Understanding variables, strings, numbers, and booleans', 'https://video.university.edu/cs101-variables', 2, 75, '2024-08-23', true),
(15, 'Control Structures', 'If statements, loops, and conditional logic', 'https://video.university.edu/cs101-control', 3, 75, '2024-08-26', true),
(15, 'Functions', 'Defining and calling functions, parameters and return values', 'https://video.university.edu/cs101-functions', 4, 75, '2024-08-28', true),
(15, 'Lists and Dictionaries', 'Working with collections and data structures', 'https://video.university.edu/cs101-collections', 5, 75, '2024-08-30', true),
(15, 'File Input/Output', 'Reading from and writing to files', 'https://video.university.edu/cs101-files', 6, 75, '2024-09-04', true),
(15, 'Error Handling', 'Try/except blocks and debugging techniques', 'https://video.university.edu/cs101-errors', 7, 75, '2024-09-06', true),

-- CS201-Fall24-A lessons (instance_id: 17)
(17, 'Algorithm Analysis', 'Big O notation and complexity analysis', 'https://video.university.edu/cs201-analysis', 1, 90, '2024-08-20', true),
(17, 'Arrays and Strings', 'Array manipulation and string algorithms', 'https://video.university.edu/cs201-arrays', 2, 90, '2024-08-22', true),
(17, 'Linked Lists', 'Implementation and operations on linked lists', 'https://video.university.edu/cs201-linkedlists', 3, 90, '2024-08-27', true),
(17, 'Stacks and Queues', 'LIFO and FIFO data structures', 'https://video.university.edu/cs201-stacks', 4, 90, '2024-08-29', true),
(17, 'Trees and Binary Trees', 'Tree structures and traversal algorithms', 'https://video.university.edu/cs201-trees', 5, 90, '2024-09-03', true),
(17, 'Sorting Algorithms', 'Comparison and non-comparison sorting methods', 'https://video.university.edu/cs201-sorting', 6, 90, '2024-09-05', true),

-- MATH101-Fall24-A lessons (instance_id: 21)
(21, 'Functions and Limits', 'Introduction to functions and limit concepts', 'https://video.university.edu/math101-limits', 1, 75, '2024-08-19', true),
(21, 'Derivatives', 'Definition and rules of differentiation', 'https://video.university.edu/math101-derivatives', 2, 75, '2024-08-21', true),
(21, 'Applications of Derivatives', 'Optimization and related rates problems', 'https://video.university.edu/math101-applications', 3, 75, '2024-08-23', true),
(21, 'Integrals', 'Antiderivatives and definite integrals', 'https://video.university.edu/math101-integrals', 4, 75, '2024-08-26', true);

-- ========================================
-- ASSIGNMENTS
-- ========================================
INSERT INTO assignments (instance_id, title, description, due_date, max_points, assignment_type, instructions) VALUES
-- CS101-Fall24-A assignments (instance_id: 15)
(15, 'Hello World Program', 'Write your first Python program', '2024-08-28 23:59:00', 50.00, 'homework', 'Create a program that prints Hello World and your name'),
(15, 'Calculator Project', 'Build a basic calculator', '2024-09-11 23:59:00', 100.00, 'project', 'Implement basic arithmetic operations with error handling'),
(15, 'Data Processing Assignment', 'Process CSV data using Python', '2024-09-25 23:59:00', 100.00, 'homework', 'Read, analyze, and output statistics from a dataset'),
(15, 'Midterm Exam', 'Comprehensive midterm examination', '2024-10-15 14:00:00', 200.00, 'exam', 'In-person examination covering all topics'),
(15, 'Final Project', 'Capstone programming project', '2024-12-10 23:59:00', 200.00, 'project', 'Design and implement a complete application'),

-- CS201-Fall24-A assignments (instance_id: 17)
(17, 'Algorithm Analysis Lab', 'Analyze time complexity of algorithms', '2024-09-05 23:59:00', 75.00, 'lab', 'Implement and measure algorithm performance'),
(17, 'Linked List Implementation', 'Implement a complete linked list class', '2024-09-19 23:59:00', 100.00, 'homework', 'Create linked list with all standard operations'),
(17, 'Tree Traversal Project', 'Implement tree traversal algorithms', '2024-10-03 23:59:00', 125.00, 'project', 'DFS and BFS implementations with visualization'),
(17, 'Sorting Algorithm Comparison', 'Compare performance of sorting algorithms', '2024-10-24 23:59:00', 100.00, 'homework', 'Implement and benchmark multiple sorting methods'),
(17, 'Data Structures Final Project', 'Design a complex data structure', '2024-12-05 23:59:00', 200.00, 'project', 'Create an original data structure with documentation'),

-- MATH101-Fall24-A assignments (instance_id: 21)
(21, 'Limits Problem Set', 'Practice problems on limits', '2024-09-02 23:59:00', 75.00, 'homework', 'Solve limit problems using various techniques'),
(21, 'Derivative Applications', 'Optimization and related rates', '2024-09-16 23:59:00', 100.00, 'homework', 'Apply derivatives to real-world problems'),
(21, 'Integration Techniques', 'Various integration methods', '2024-10-07 23:59:00', 100.00, 'homework', 'Practice substitution, parts, and partial fractions'),
(21, 'Calculus Midterm', 'Comprehensive midterm exam', '2024-10-21 10:00:00', 200.00, 'exam', 'Covers limits, derivatives, and basic integration');

-- ========================================
-- SUBMISSIONS
-- ========================================
INSERT INTO submissions (assignment_id, user_id, submission_date, content, points_earned, feedback, is_late, status, graded_at, graded_by) VALUES
-- CS101 Hello World submissions
(1, 36, '2024-08-27 18:30:00', 'print("Hello World! My name is Alex")', 48.00, 'Good work! Minor formatting issue.', false, 'graded', '2024-08-29 10:00:00', 3),
(1, 37, '2024-08-28 16:45:00', 'print("Hello World!")\nprint("My name is Emma")', 50.00, 'Perfect execution!', false, 'graded', '2024-08-29 10:15:00', 3),
(1, 38, '2024-08-28 23:30:00', 'print("Hello World and Liam")', 45.00, 'Works but could be improved. See comments.', false, 'graded', '2024-08-29 10:30:00', 3),
(1, 39, '2024-08-29 02:15:00', 'print("Hello World!")\nprint("Olivia Garcia")', 42.00, 'Late submission. Good content otherwise.', true, 'graded', '2024-08-29 11:00:00', 3),
(1, 40, '2024-08-28 20:00:00', 'print("Hello World! - Noah")', 50.00, 'Excellent work!', false, 'graded', '2024-08-29 11:15:00', 3),

-- CS101 Calculator Project submissions
(2, 36, '2024-09-10 22:15:00', 'Complete calculator implementation with error handling', 95.00, 'Excellent project with good error handling', false, 'graded', '2024-09-12 14:00:00', 3),
(2, 37, '2024-09-11 21:30:00', 'Calculator with basic operations', 88.00, 'Good work, missing some advanced features', false, 'graded', '2024-09-12 14:15:00', 3),
(2, 38, '2024-09-11 23:45:00', 'Basic calculator implementation', 82.00, 'Works well but code organization could improve', false, 'graded', '2024-09-12 14:30:00', 3),
(2, 40, '2024-09-12 03:20:00', 'Advanced calculator with GUI', 78.00, 'Ambitious project but late submission penalty', true, 'graded', '2024-09-12 15:00:00', 3),

-- CS201 Algorithm Analysis submissions
(6, 16, '2024-09-04 19:00:00', 'Comprehensive analysis with graphs and measurements', 72.00, 'Good analysis but missing some test cases', false, 'graded', '2024-09-06 09:00:00', 4),
(6, 18, '2024-09-05 17:30:00', 'Detailed performance comparison', 75.00, 'Perfect analysis and presentation', false, 'graded', '2024-09-06 09:15:00', 4),
(6, 20, '2024-09-05 22:45:00', 'Algorithm timing and complexity study', 70.00, 'Good effort, results section needs improvement', false, 'graded', '2024-09-06 09:30:00', 4),
(6, 22, '2024-09-05 16:15:00', 'Complete lab with visualization', 75.00, 'Excellent work with clear visualizations', false, 'graded', '2024-09-06 09:45:00', 4),

-- CS201 Linked List submissions
(7, 16, '2024-09-18 20:30:00', 'Full linked list implementation with unit tests', 95.00, 'Outstanding implementation with comprehensive tests', false, 'graded', '2024-09-20 11:00:00', 4),
(7, 18, '2024-09-19 19:45:00', 'Linked list class with all operations', 92.00, 'Very good work, minor optimization suggestions', false, 'graded', '2024-09-20 11:15:00', 4),
(7, 20, '2024-09-19 23:30:00', 'Basic linked list implementation', 85.00, 'Good core implementation, missing some methods', false, 'graded', '2024-09-20 11:30:00', 4),
(7, 26, '2024-09-19 15:20:00', 'Complete linked list with documentation', 98.00, 'Exceptional work with excellent documentation', false, 'graded', '2024-09-20 11:45:00', 4),

-- MATH101 Limits submissions
(10, 33, '2024-09-01 21:00:00', 'All limit problems solved correctly', 72.00, 'Good understanding, minor calculation errors', false, 'graded', '2024-09-03 10:00:00', 6),
(10, 34, '2024-09-02 18:30:00', 'Limits problem set with detailed work', 75.00, 'Perfect work with clear explanations', false, 'graded', '2024-09-03 10:15:00', 6),
(10, 35, '2024-09-02 22:15:00', 'Problem solutions with graphs', 70.00, 'Good approach, some problems incomplete', false, 'graded', '2024-09-03 10:30:00', 6);

-- ========================================
-- FORUMS
-- ========================================
INSERT INTO forums (instance_id, title, description, is_active) VALUES
(15, 'General Discussion', 'General questions and discussions for CS101', true),
(15, 'Assignment Help', 'Get help with assignments and projects', true),
(15, 'Study Groups', 'Organize study groups and peer learning', true),
(17, 'Algorithm Discussion', 'Discuss algorithms and data structures', true),
(17, 'Code Review', 'Peer code review and feedback', true),
(21, 'Calculus Help', 'Math problem solving assistance', true),
(21, 'Exam Preparation', 'Study materials and exam tips', true);

-- ========================================
-- FORUM POSTS
-- ========================================
INSERT INTO forum_posts (forum_id, user_id, parent_post_id, title, content, is_pinned, created_at, updated_at) VALUES
-- CS101 General Discussion posts
(1, 3, NULL, 'Welcome to CS101!', 'Welcome everyone to Introduction to Programming! Please introduce yourselves here.', true, '2024-08-19 10:00:00', '2024-08-19 10:00:00'),
(1, 36, 1, 'Introduction - Alex', 'Hi everyone! I\'m Alex, excited to learn programming!', false, '2024-08-19 15:30:00', '2024-08-19 15:30:00'),
(1, 37, 1, 'Hello from Emma', 'Hello! I\'m Emma, looking forward to this class.', false, '2024-08-19 16:45:00', '2024-08-19 16:45:00'),
(1, 38, NULL, 'Question about Python installation', 'I\'m having trouble installing Python on my machine. Any suggestions?', false, '2024-08-20 09:15:00', '2024-08-20 09:15:00'),
(1, 39, 4, 'Python Installation Help', 'I had the same issue. Try downloading from python.org and follow the installation guide.', false, '2024-08-20 11:30:00', '2024-08-20 11:30:00'),
(1, 40, 4, 'Alternative Solution', 'You could also try using Anaconda which includes Python and many useful libraries.', false, '2024-08-20 14:20:00', '2024-08-20 14:20:00'),

-- CS101 Assignment Help posts
(2, 41, NULL, 'Calculator Project Ideas', 'What features should we include in the calculator project?', false, '2024-09-02 10:00:00', '2024-09-02 10:00:00'),
(2, 42, 7, 'Calculator Features', 'I think basic operations plus maybe square root and exponents would be good.', false, '2024-09-02 12:15:00', '2024-09-02 12:15:00'),
(2, 43, NULL, 'Error Handling Help', 'How do we handle division by zero in Python?', false, '2024-09-03 16:30:00', '2024-09-03 16:30:00'),
(2, 36, 9, 'Division by Zero', 'You can use try-except blocks to catch ZeroDivisionError.', false, '2024-09-03 18:45:00', '2024-09-03 18:45:00'),

-- CS201 Algorithm Discussion posts
(4, 4, NULL, 'Big O Notation Resources', 'Here are some excellent resources for understanding algorithm complexity.', true, '2024-08-20 08:00:00', '2024-08-20 08:00:00'),
(4, 16, NULL, 'Linked List vs Array Performance', 'When should we choose linked lists over arrays?', false, '2024-09-01 14:20:00', '2024-09-01 14:20:00'),
(4, 18, 12, 'Performance Comparison', 'Arrays are better for random access, linked lists for frequent insertions/deletions.', false, '2024-09-01 16:30:00', '2024-09-01 16:30:00'),
(4, 20, 12, 'Memory Considerations', 'Don\'t forget about memory overhead - linked lists use more memory per element.', false, '2024-09-01 19:45:00', '2024-09-01 19:45:00'),

-- CS201 Code Review posts
(5, 22, NULL, 'Stack Implementation Review Request', 'Could someone review my stack implementation?', false, '2024-09-15 11:00:00', '2024-09-15 11:00:00'),
(5, 26, 15, 'Stack Review', 'Your implementation looks good! One suggestion: add size tracking for efficiency.', false, '2024-09-15 13:20:00', '2024-09-15 13:20:00'),
(5, 29, NULL, 'Binary Search Tree Help', 'My BST insertion is not working correctly. Anyone see the issue?', false, '2024-09-20 15:30:00', '2024-09-20 15:30:00'),

-- MATH101 posts
(6, 6, NULL, 'Office Hours Schedule', 'My office hours are Monday/Wednesday 2-4 PM in MATH 205.', true, '2024-08-19 09:00:00', '2024-08-19 09:00:00'),
(6, 33, NULL, 'Derivative Rules Clarification', 'Can someone explain the product rule again?', false, '2024-09-10 16:20:00', '2024-09-10 16:20:00'),
(6, 34, 19, 'Product Rule Explanation', 'The product rule is (fg)\' = f\'g + fg\'. Remember both terms!', false, '2024-09-10 18:15:00', '2024-09-10 18:15:00'),
(6, 35, NULL, 'Integration by Parts', 'When should we use integration by parts vs substitution?', false, '2024-10-01 12:30:00', '2024-10-01 12:30:00');

-- ========================================
-- Update current enrollment counts for course instances
-- ========================================
UPDATE course_instances 
SET current_enrollment = (
    SELECT COUNT(*) 
    FROM enrollments 
    WHERE enrollments.instance_id = course_instances.instance_id 
    AND enrollments.status = 'enrolled'
);

-- ========================================
-- Final success message
-- ========================================
-- Data insertion completed successfully!
-- Total records inserted:
-- - 40+ users (admins, instructors, students)
-- - 8 departments with heads assigned
-- - 33 courses across multiple departments  
-- - 24 course instances (Fall 2023, Spring 2024, Fall 2024)
-- - 80+ enrollments with grades and statuses
-- - 10 tags and 50+ course-tag relationships
-- - 10 mentorship relationships
-- - 25+ lessons across multiple courses
-- - 13 assignments with various types
-- - 25+ submissions with grades and feedback
-- - 7 discussion forums
-- - 20+ forum posts with threaded discussions
--
-- This dataset provides sufficient records for complex GROUP BY and HAVING queries such as:
-- 1. Enrollment statistics by department, semester, course level
-- 2. Grade distributions by instructor, course, student performance
-- 3. Course popularity and capacity utilization
-- 4. Student progress tracking and completion rates
-- 5. Forum activity and engagement metrics
-- 6. Assignment submission patterns and grading analysis