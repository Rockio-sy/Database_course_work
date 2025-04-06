# Use an official Python runtime as the base image
FROM python:3.10

# Set the working directory
WORKDIR /app

# Copy requirements.txt and install dependencies
COPY requirements.txt .
RUN pip install -r requirements.txt
RUN pip install psycopg2-binary

# Install Alembic
RUN pip install alembic

# Copy the rest of the application code
COPY . .

# Copy the Alembic configuration files
COPY alembic.ini .
COPY alembic ./alembic

# Make the migration script executable
RUN chmod +x run_migrations.sh

# Expose the port your bank server runs on
EXPOSE 5000

# Command to run migrations and start the application
CMD ["./run_migrations.sh"]
