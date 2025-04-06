#!/bin/bash

# Wait for PostgreSQL to start using a Python script
echo "Waiting for PostgreSQL to start..."
python << END
import time
import psycopg2
from psycopg2 import OperationalError

def wait_for_postgres():
    while True:
        try:
            conn = psycopg2.connect(
                dbname="banko",
                user="postgres",
                password="postgres",
                host="postgres-banko"
            )
            conn.close()
            print("PostgreSQL started.")
            break
        except OperationalError:
            print("PostgreSQL not ready yet. Retrying in 1 second...")
            time.sleep(1)

wait_for_postgres()
END

# Run Alembic migrations
echo "Running Alembic migrations..."
alembic upgrade head

# Start the Python application
echo "Starting the Python application..."
python main.py
