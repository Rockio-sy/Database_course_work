import psycopg2
from psycopg2 import sql

# Database connection parameters
db_params = {
    'dbname': 'tickets',  # Replace with your database name
    'user': 'postgres',
    'password': 'postgres',
    'host': 'localhost',
    'port': 5432
}

# Connect to the PostgreSQL database
conn = psycopg2.connect(**db_params)
cur = conn.cursor()

# Insert data into the movies table
movies_data = [
    ('Inception', 'Sci-Fi', '2010-07-16', 'Christopher Nolan', 8.8),
    ('The Dark Knight', 'Action', '2008-07-18', 'Christopher Nolan', 9.0),
    ('Interstellar', 'Adventure', '2014-11-07', 'Christopher Nolan', 8.6)
]

for movie in movies_data:
    cur.execute(
        sql.SQL("INSERT INTO movies (title, genre, release_date, director, rating) VALUES (%s, %s, %s, %s, %s)"),
        movie
    )

# Insert data into the clients table
clients_data = [
    ('John Doe', 'john.doe@example.com', '1234567890'),
    ('Jane Smith', 'jane.smith@example.com', '0987654321')
]

for client in clients_data:
    cur.execute(
        sql.SQL("INSERT INTO clients (name, email, phone_number) VALUES (%s, %s, %s)"),
        client
    )

# Insert data into the subscriptions table
subscriptions_data = [
    ('Basic', 9.99, '{"HD": true, "SD": true, "Simultaneous Streams": 1}'),
    ('Premium', 14.99, '{"HD": true, "4K": true, "Simultaneous Streams": 4}')
]

for subscription in subscriptions_data:
    cur.execute(
        sql.SQL("INSERT INTO subscriptions (name, price, features) VALUES (%s, %s, %s)"),
        subscription
    )

# Commit the transaction to ensure the data is saved before referencing it
conn.commit()

# Insert data into the client_subscriptions table
client_subscriptions_data = [
    (1, 1, '2023-01-01', '2023-12-31'),  # client_id=1, subscription_id=1
    (2, 2, '2023-01-01', None)           # client_id=2, subscription_id=2
]

for cs in client_subscriptions_data:
    cur.execute(
        sql.SQL("INSERT INTO client_subscriptions (client_id, subscription_id, start_date, end_date) VALUES (%s, %s, %s, %s)"),
        cs
    )

# Insert data into the watchlist table
watchlist_data = [
    (1, 1),  # client_id=1, movie_id=1
    (1, 2),  # client_id=1, movie_id=2
    (2, 3)   # client_id=2, movie_id=3
]

for watch in watchlist_data:
    cur.execute(
        sql.SQL("INSERT INTO watchlist (client_id, movie_id) VALUES (%s, %s)"),
        watch
    )

# Insert data into the ratings table
ratings_data = [
    (1, 1, 9.5),  # client_id=1, movie_id=1, rating=9.5
    (2, 2, 9.0),  # client_id=2, movie_id=2, rating=9.0
    (1, 3, 8.7)   # client_id=1, movie_id=3, rating=8.7
]

for rating in ratings_data:
    cur.execute(
        sql.SQL("INSERT INTO ratings (client_id, movie_id, rating) VALUES (%s, %s, %s)"),
        rating
    )

# Insert data into the reviews table
reviews_data = [
    (1, 1, 'A mind-bending masterpiece!'),  # client_id=1, movie_id=1
    (2, 2, 'The best superhero movie ever made.'),  # client_id=2, movie_id=2
    (1, 3, 'A visually stunning journey through space and time.')  # client_id=1, movie_id=3
]

for review in reviews_data:
    cur.execute(
        sql.SQL("INSERT INTO reviews (client_id, movie_id, review) VALUES (%s, %s, %s)"),
        review
    )

# Commit the transaction
conn.commit()

# Close the cursor and connection
cur.close()
conn.close()

print("Data inserted successfully!")