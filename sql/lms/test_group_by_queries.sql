-- Test queries to demonstrate GROUP BY and HAVING functionality
-- Run after executing both lms_database_schema.sql and insert_sample_data.sql

-- ========================================
-- ENROLLMENT AND GRADE ANALYSIS
-- ========================================

-- 1. Course enrollment statistics - courses with more than 5 students
SELECT 
    c.title,
    ci.semester,
    ci.year,
    COUNT(e.user_id) as total_enrollments,
    AVG(e.final_grade) as avg_grade,
    MAX(e.final_grade) as highest_grade,
    MIN(e.final_grade) as lowest_grade
FROM course_instances ci
JOIN courses c ON ci.course_id = c.course_id
LEFT JOIN enrollments e ON ci.instance_id = e.instance_id
GROUP BY c.course_id, c.title, ci.semester, ci.year
HAVING COUNT(e.user_id) > 5
ORDER BY total_enrollments DESC;

-- 2. Department performance - departments with average grade above 85
SELECT 
    d.name as department_name,
    COUNT(DISTINCT e.user_id) as total_students,
    COUNT(DISTINCT c.course_id) as courses_offered,
    AVG(e.final_grade) as avg_department_grade,
    COUNT(e.enrollment_id) as total_enrollments
FROM departments d
JOIN courses c ON d.dept_id = c.dept_id
JOIN course_instances ci ON c.course_id = ci.course_id
JOIN enrollments e ON ci.instance_id = e.instance_id
WHERE e.final_grade IS NOT NULL
GROUP BY d.dept_id, d.name
HAVING AVG(e.final_grade) > 85
ORDER BY avg_department_grade DESC;

-- 3. Instructor workload analysis - instructors teaching more than 2 course instances
SELECT 
    u.first_name || ' ' || u.last_name as instructor_name,
    COUNT(DISTINCT ci.instance_id) as courses_taught,
    COUNT(DISTINCT e.user_id) as total_students,
    AVG(e.final_grade) as avg_student_grade,
    SUM(ci.current_enrollment) as total_enrollment
FROM users u
JOIN course_instances ci ON u.user_id = ci.instructor_id
LEFT JOIN enrollments e ON ci.instance_id = e.instance_id
WHERE u.role = 'instructor'
GROUP BY u.user_id, u.first_name, u.last_name
HAVING COUNT(DISTINCT ci.instance_id) > 2
ORDER BY courses_taught DESC;

-- ========================================
-- STUDENT PERFORMANCE ANALYSIS
-- ========================================

-- 4. High-performing students - students with more than 3 completed courses and avg grade > 90
SELECT 
    u.first_name || ' ' || u.last_name as student_name,
    COUNT(e.enrollment_id) as courses_completed,
    AVG(e.final_grade) as avg_grade,
    MAX(e.final_grade) as best_grade,
    MIN(e.final_grade) as lowest_grade
FROM users u
JOIN enrollments e ON u.user_id = e.user_id
WHERE u.role = 'student' AND e.status = 'completed' AND e.final_grade IS NOT NULL
GROUP BY u.user_id, u.first_name, u.last_name
HAVING COUNT(e.enrollment_id) > 3 AND AVG(e.final_grade) > 90
ORDER BY avg_grade DESC;

-- 5. Students with multiple enrollments per semester
SELECT 
    u.first_name || ' ' || u.last_name as student_name,
    ci.semester,
    ci.year,
    COUNT(e.enrollment_id) as courses_per_semester
FROM users u
JOIN enrollments e ON u.user_id = e.user_id
JOIN course_instances ci ON e.instance_id = ci.instance_id
WHERE u.role = 'student'
GROUP BY u.user_id, u.first_name, u.last_name, ci.semester, ci.year
HAVING COUNT(e.enrollment_id) >= 2
ORDER BY courses_per_semester DESC, ci.year DESC, ci.semester;

-- ========================================
-- COURSE AND CONTENT ANALYSIS
-- ========================================

-- 6. Popular courses - courses offered multiple times with high enrollment
SELECT 
    c.title,
    c.code,
    COUNT(DISTINCT ci.instance_id) as times_offered,
    AVG(ci.current_enrollment) as avg_enrollment,
    MAX(ci.current_enrollment) as max_enrollment,
    SUM(ci.current_enrollment) as total_historical_enrollment
FROM courses c
JOIN course_instances ci ON c.course_id = ci.course_id
GROUP BY c.course_id, c.title, c.code
HAVING COUNT(DISTINCT ci.instance_id) >= 3 OR AVG(ci.current_enrollment) > 15
ORDER BY total_historical_enrollment DESC;

-- 7. Course instances with low completion rates
SELECT 
    c.title,
    ci.semester,
    ci.year,
    COUNT(e.enrollment_id) as total_enrolled,
    SUM(CASE WHEN e.status = 'completed' THEN 1 ELSE 0 END) as completed_count,
    ROUND(
        (SUM(CASE WHEN e.status = 'completed' THEN 1 ELSE 0 END)::DECIMAL / COUNT(e.enrollment_id)) * 100, 
        2
    ) as completion_rate
FROM courses c
JOIN course_instances ci ON c.course_id = ci.course_id
JOIN enrollments e ON ci.instance_id = e.instance_id
GROUP BY c.course_id, c.title, ci.instance_id, ci.semester, ci.year
HAVING COUNT(e.enrollment_id) > 3  -- Only consider instances with reasonable enrollment
ORDER BY completion_rate ASC;

-- ========================================
-- ASSIGNMENT AND SUBMISSION ANALYSIS
-- ========================================

-- 8. Assignment difficulty analysis - assignments with low average scores
SELECT 
    a.title,
    c.title as course_title,
    ci.semester,
    ci.year,
    COUNT(s.submission_id) as total_submissions,
    AVG(s.points_earned) as avg_score,
    MAX(a.max_points) as max_possible,
    ROUND((AVG(s.points_earned) / MAX(a.max_points)) * 100, 2) as avg_percentage
FROM assignments a
JOIN course_instances ci ON a.instance_id = ci.instance_id
JOIN courses c ON ci.course_id = c.course_id
LEFT JOIN submissions s ON a.assignment_id = s.assignment_id
WHERE s.points_earned IS NOT NULL
GROUP BY a.assignment_id, a.title, c.title, ci.semester, ci.year
HAVING COUNT(s.submission_id) >= 3 AND (AVG(s.points_earned) / MAX(a.max_points)) < 0.80
ORDER BY avg_percentage ASC;

-- 9. Late submission patterns by course
SELECT 
    c.title,
    COUNT(s.submission_id) as total_submissions,
    SUM(CASE WHEN s.is_late = true THEN 1 ELSE 0 END) as late_submissions,
    ROUND(
        (SUM(CASE WHEN s.is_late = true THEN 1 ELSE 0 END)::DECIMAL / COUNT(s.submission_id)) * 100, 
        2
    ) as late_percentage
FROM courses c
JOIN course_instances ci ON c.course_id = ci.course_id
JOIN assignments a ON ci.instance_id = a.instance_id
JOIN submissions s ON a.assignment_id = s.assignment_id
GROUP BY c.course_id, c.title
HAVING COUNT(s.submission_id) > 5
ORDER BY late_percentage DESC;

-- ========================================
-- FORUM ACTIVITY ANALYSIS
-- ========================================

-- 10. Most active forum discussions
SELECT 
    f.title as forum_title,
    c.title as course_title,
    COUNT(fp.post_id) as total_posts,
    COUNT(DISTINCT fp.user_id) as unique_contributors,
    MAX(fp.created_at) as last_activity
FROM forums f
JOIN course_instances ci ON f.instance_id = ci.instance_id
JOIN courses c ON ci.course_id = c.course_id
LEFT JOIN forum_posts fp ON f.forum_id = fp.forum_id
GROUP BY f.forum_id, f.title, c.title
HAVING COUNT(fp.post_id) >= 3
ORDER BY total_posts DESC;

-- 11. Most engaged students in forum discussions
SELECT 
    u.first_name || ' ' || u.last_name as student_name,
    COUNT(fp.post_id) as total_posts,
    COUNT(DISTINCT fp.forum_id) as forums_participated,
    MIN(fp.created_at) as first_post,
    MAX(fp.created_at) as latest_post
FROM users u
JOIN forum_posts fp ON u.user_id = fp.user_id
WHERE u.role = 'student'
GROUP BY u.user_id, u.first_name, u.last_name
HAVING COUNT(fp.post_id) >= 2
ORDER BY total_posts DESC;

-- ========================================
-- ADVANCED ANALYTICS
-- ========================================

-- 12. Course prerequisites effectiveness - courses with prerequisites vs without
SELECT 
    CASE 
        WHEN EXISTS (SELECT 1 FROM course_prerequisites cp WHERE cp.course_id = c.course_id) 
        THEN 'Has Prerequisites' 
        ELSE 'No Prerequisites' 
    END as prerequisite_status,
    COUNT(DISTINCT c.course_id) as course_count,
    AVG(e.final_grade) as avg_final_grade,
    COUNT(e.enrollment_id) as total_enrollments
FROM courses c
JOIN course_instances ci ON c.course_id = ci.course_id
LEFT JOIN enrollments e ON ci.instance_id = e.instance_id
WHERE e.final_grade IS NOT NULL
GROUP BY prerequisite_status
HAVING COUNT(e.enrollment_id) > 10;

-- 13. Tag-based course performance analysis
SELECT 
    t.name as tag_name,
    COUNT(DISTINCT c.course_id) as courses_with_tag,
    AVG(e.final_grade) as avg_grade,
    COUNT(e.enrollment_id) as total_enrollments
FROM tags t
JOIN course_tags ct ON t.tag_id = ct.tag_id
JOIN courses c ON ct.course_id = c.course_id
JOIN course_instances ci ON c.course_id = ci.course_id
LEFT JOIN enrollments e ON ci.instance_id = e.instance_id
WHERE e.final_grade IS NOT NULL
GROUP BY t.tag_id, t.name
HAVING COUNT(DISTINCT c.course_id) >= 3 AND COUNT(e.enrollment_id) > 10
ORDER BY avg_grade DESC;