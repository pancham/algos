# LMS Database Docker Setup

A complete Learning Management System PostgreSQL database with Docker deployment. This setup automatically creates and populates a comprehensive LMS database with sample data for testing and development.

## 🚀 Quick Start

### Prerequisites
- Docker
- Docker Compose

### 1. Launch the Database

```bash
# Clone or navigate to this directory
cd /path/to/lms

# Start the PostgreSQL container
docker-compose up -d

# Or with pgAdmin for database management
docker-compose --profile admin up -d
```

### 2. Connect to the Database

**Connection Details:**
- **Host:** localhost
- **Port:** 5432
- **Database:** lms_db
- **Username:** lms_user
- **Password:** lms_password

**Connection String:**
```
postgresql://lms_user:lms_password@localhost:5432/lms_db
```

### 3. Access pgAdmin (Optional)
If you started with the admin profile:
- **URL:** http://localhost:8080
- **Email:** admin@lms.local
- **Password:** admin123

## 📊 Database Contents

The database automatically includes:

### Core Data
- **40+ Users:** Admins, instructors, and students
- **8 Departments:** Computer Science, Math, Physics, Business, etc.
- **33 Courses:** Across multiple departments with prerequisites
- **24 Course Instances:** Fall 2023, Spring 2024, Fall 2024
- **80+ Enrollments:** With grades and completion status

### Rich Sample Data
- **Course Prerequisites:** Complex prerequisite chains
- **Mentorship Relationships:** Student-to-student mentoring
- **Assignments & Submissions:** With grades and feedback
- **Discussion Forums:** Threaded forum posts
- **Tags & Categories:** Course classification system

## 🔍 Sample Queries

Test the database with these GROUP BY and HAVING examples:

```sql
-- Course enrollment statistics
SELECT 
    c.title,
    ci.semester,
    ci.year,
    COUNT(e.user_id) as total_enrollments,
    AVG(e.final_grade) as avg_grade
FROM course_instances ci
JOIN courses c ON ci.course_id = c.course_id
LEFT JOIN enrollments e ON ci.instance_id = e.instance_id
GROUP BY c.course_id, c.title, ci.semester, ci.year
HAVING COUNT(e.user_id) > 5
ORDER BY total_enrollments DESC;

-- High-performing students
SELECT 
    u.first_name || ' ' || u.last_name as student_name,
    COUNT(e.enrollment_id) as courses_completed,
    AVG(e.final_grade) as avg_grade
FROM users u
JOIN enrollments e ON u.user_id = e.user_id
WHERE u.role = 'student' AND e.status = 'completed'
GROUP BY u.user_id, u.first_name, u.last_name
HAVING COUNT(e.enrollment_id) > 3 AND AVG(e.final_grade) > 90
ORDER BY avg_grade DESC;
```

See `test_group_by_queries.sql` for 13+ comprehensive example queries.

## 🛠️ Management Commands

### Start/Stop Services
```bash
# Start database only
docker-compose up -d lms-postgres

# Start with pgAdmin
docker-compose --profile admin up -d

# Stop all services
docker-compose down

# Stop and remove volumes (⚠️ deletes data)
docker-compose down -v
```

### Database Operations
```bash
# Access PostgreSQL shell
docker exec -it lms-database psql -U lms_user -d lms_db

# Run test queries
docker exec -it lms-database psql -U lms_user -d lms_db -f /tmp/test_connection.sql

# View logs
docker-compose logs lms-postgres

# Check service status
docker-compose ps
```

### Backup and Restore
```bash
# Create backup
docker exec lms-database pg_dump -U lms_user -d lms_db > backup_$(date +%Y%m%d_%H%M%S).sql

# Restore from backup
docker exec -i lms-database psql -U lms_user -d lms_db < backup_file.sql
```

## 📁 Project Structure

```
lms/
├── docker-compose.yml          # Docker Compose configuration
├── Dockerfile                  # Custom PostgreSQL image
├── .env                       # Environment variables
├── init_db.sh                 # Database initialization script
├── lms_database_schema.sql    # Database schema
├── insert_sample_data.sql     # Sample data
├── test_group_by_queries.sql  # Example queries
├── backups/                   # Backup directory (auto-created)
└── README.md                  # This file
```

## 🔧 Configuration

### Environment Variables
Edit `.env` to customize:
- Database credentials
- Port mappings
- pgAdmin settings
- Container names

### Custom Initialization
The database only imports schema and data if:
1. No tables exist in the database, OR
2. Tables exist but contain no data

This prevents data duplication on container restarts.

## 🗂️ Database Schema

### Main Entity Tables
- `users` - System users (students, instructors, admins)
- `departments` - Academic departments
- `courses` - Course catalog
- `course_instances` - Specific course offerings

### Relationship Tables
- `enrollments` - Student course enrollments
- `course_prerequisites` - Course prerequisite chains
- `user_mentorships` - Student mentoring relationships

### Content Tables
- `lessons` - Course lessons and materials
- `assignments` - Course assignments
- `submissions` - Student assignment submissions
- `forums` / `forum_posts` - Discussion forums

## 🚨 Troubleshooting

### Common Issues

**Port already in use:**
```bash
# Check what's using port 5432
lsof -i :5432

# Use different port in docker-compose.yml
ports:
  - "5433:5432"
```

**Permission denied:**
```bash
# Fix permissions on init script
chmod +x init_db.sh
```

**Database won't initialize:**
```bash
# Check logs
docker-compose logs lms-postgres

# Remove volumes and restart
docker-compose down -v
docker-compose up -d
```

**Connection refused:**
```bash
# Wait for database to be ready
docker-compose logs -f lms-postgres

# Check health status
docker-compose ps
```

### Reset Database
To completely reset the database:
```bash
docker-compose down -v
docker-compose up -d
```

## 📈 Performance Notes

- Database includes appropriate indexes for common queries
- Sample data is sized for development/testing (not production scale)
- Use connection pooling for production applications
- Consider read replicas for analytics workloads

## 🤝 Contributing

To add more sample data:
1. Edit `insert_sample_data.sql`
2. Rebuild container: `docker-compose up -d --build`
3. Test with new queries in `test_group_by_queries.sql`

## 📄 License

This project is provided as-is for educational and development purposes.