#!/bin/bash
set -e

# Initialize LMS Database Script
# This script runs during PostgreSQL container initialization
# It only imports schema and data if the database/tables don't exist

echo "=== LMS Database Initialization Started ==="

# Database connection parameters
DB_NAME="${POSTGRES_DB:-lms_db}"
DB_USER="${POSTGRES_USER:-lms_user}"

# Function to check if tables exist
check_tables_exist() {
    local table_count
    table_count=$(psql -U "$DB_USER" -d "$DB_NAME" -t -c "
        SELECT COUNT(*) 
        FROM information_schema.tables 
        WHERE table_schema = 'public' 
        AND table_type = 'BASE TABLE';"
    )
    echo "$table_count"
}

# Function to check if data exists in main tables
check_data_exists() {
    local data_count
    data_count=$(psql -U "$DB_USER" -d "$DB_NAME" -t -c "
        SELECT COALESCE(
            (SELECT COUNT(*) FROM users) + 
            (SELECT COUNT(*) FROM departments) + 
            (SELECT COUNT(*) FROM courses), 
            0
        );"
    )
    echo "$data_count"
}

# Check if database exists and has tables
echo "Checking if LMS database tables exist..."
table_count=$(check_tables_exist)
echo "Found $table_count tables in database"

if [ "$table_count" -eq 0 ]; then
    echo "No tables found. Importing database schema..."
    
    # Import schema
    echo "Importing schema from 01-schema.sql..."
    psql -U "$DB_USER" -d "$DB_NAME" -f /docker-entrypoint-initdb.d/01-schema.sql
    
    if [ $? -eq 0 ]; then
        echo "✅ Schema imported successfully"
        
        # Verify tables were created
        new_table_count=$(check_tables_exist)
        echo "Created $new_table_count tables"
        
        # Import sample data
        echo "Importing sample data from 02-data.sql..."
        psql -U "$DB_USER" -d "$DB_NAME" -f /docker-entrypoint-initdb.d/02-data.sql
        
        if [ $? -eq 0 ]; then
            echo "✅ Sample data imported successfully"
            
            # Verify data was inserted
            data_count=$(check_data_exists)
            echo "Inserted data for $data_count core records"
            
            # Display summary
            echo ""
            echo "=== Database Initialization Summary ==="
            psql -U "$DB_USER" -d "$DB_NAME" -c "
                SELECT 
                    'users' as table_name, COUNT(*) as record_count 
                FROM users
                UNION ALL
                SELECT 'departments', COUNT(*) FROM departments
                UNION ALL
                SELECT 'courses', COUNT(*) FROM courses
                UNION ALL
                SELECT 'course_instances', COUNT(*) FROM course_instances
                UNION ALL
                SELECT 'enrollments', COUNT(*) FROM enrollments
                UNION ALL
                SELECT 'assignments', COUNT(*) FROM assignments
                UNION ALL
                SELECT 'submissions', COUNT(*) FROM submissions
                UNION ALL
                SELECT 'forum_posts', COUNT(*) FROM forum_posts
                ORDER BY record_count DESC;
            "
            
        else
            echo "❌ Error importing sample data"
            exit 1
        fi
    else
        echo "❌ Error importing schema"
        exit 1
    fi
else
    echo "Database tables already exist. Checking for data..."
    data_count=$(check_data_exists)
    
    if [ "$data_count" -eq 0 ]; then
        echo "Tables exist but no data found. Importing sample data..."
        psql -U "$DB_USER" -d "$DB_NAME" -f /docker-entrypoint-initdb.d/02-data.sql
        
        if [ $? -eq 0 ]; then
            echo "✅ Sample data imported to existing tables"
        else
            echo "❌ Error importing sample data to existing tables"
            exit 1
        fi
    else
        echo "✅ Database already contains data ($data_count core records). Skipping initialization."
    fi
fi

echo ""
echo "=== LMS Database Ready for Connections ==="
echo "Database: $DB_NAME"
echo "User: $DB_USER"
echo "Port: 5432"
echo "Connection string: postgresql://$DB_USER:$POSTGRES_PASSWORD@localhost:5432/$DB_NAME"
echo ""

# Create a connection test script
cat > /tmp/test_connection.sql << EOF
-- LMS Database Connection Test
SELECT 'LMS Database Connection Successful!' as status;

-- Quick data verification
SELECT 
    (SELECT COUNT(*) FROM users) as users_count,
    (SELECT COUNT(*) FROM courses) as courses_count,
    (SELECT COUNT(*) FROM enrollments) as enrollments_count;

-- Sample query: Current active course instances
SELECT 
    c.title,
    ci.semester,
    ci.year,
    ci.current_enrollment,
    u.first_name || ' ' || u.last_name as instructor
FROM course_instances ci
JOIN courses c ON ci.course_id = c.course_id
JOIN users u ON ci.instructor_id = u.user_id
WHERE ci.status = 'active'
ORDER BY c.title
LIMIT 5;
EOF

echo "To test the database connection, run:"
echo "psql -U $DB_USER -d $DB_NAME -f /tmp/test_connection.sql"
echo ""

echo "=== LMS Database Initialization Completed Successfully ==="