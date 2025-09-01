# Dockerfile for PostgreSQL with LMS Database
FROM postgres:15-alpine

# Install additional utilities
RUN apk add --no-cache bash

# Set environment variables
ENV POSTGRES_DB=lms_db
ENV POSTGRES_USER=lms_user
ENV POSTGRES_PASSWORD=lms_password

# Create directory for initialization scripts
RUN mkdir -p /docker-entrypoint-initdb.d

# Copy SQL files and initialization script
COPY lms_database_schema.sql /docker-entrypoint-initdb.d/01-schema.sql
COPY insert_sample_data.sql /docker-entrypoint-initdb.d/02-data.sql
COPY init_db.sh /docker-entrypoint-initdb.d/03-init.sh

# Make the initialization script executable
RUN chmod +x /docker-entrypoint-initdb.d/03-init.sh

# Expose PostgreSQL port
EXPOSE 5432

# Use the default postgres entrypoint
ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["postgres"]