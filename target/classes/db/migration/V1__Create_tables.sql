-- migration_1_create_movies_table.sql
CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100) NOT NULL,
    release_date DATE NOT NULL,
    director VARCHAR(255),
    rating DECIMAL(3,2), -- Allows 8.5 out of 10
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- migration_2_create_clients_table.sql
CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- migration_3_create_subscriptions_table.sql
CREATE TABLE subscriptions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL, -- E.g., 'Basic', 'Premium'
    price DECIMAL(10,2) NOT NULL, -- Monthly cost
    features JSONB, -- Structured data for plan features
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- migration_4_create_client_subscription_table.sql
CREATE TABLE client_subscriptions (
    client_id INT NOT NULL,
    subscription_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    PRIMARY KEY (client_id, subscription_id),
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (subscription_id) REFERENCES subscriptions(id) ON DELETE CASCADE
);

-- migration_5_create_watchlist_table.sql
CREATE TABLE watchlist (
    client_id INT NOT NULL,
    movie_id INT NOT NULL,
    added_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (client_id, movie_id),
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- migration_6_create_ratings_table.sql
CREATE TABLE ratings (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating DECIMAL(4,2) NOT NULL CHECK (rating BETWEEN 0 AND 10.00),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- migration_7_create_reviews_table.sql
CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    movie_id INT NOT NULL,
    review TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

-- Fix `updated_at` issue using TRIGGER
CREATE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_movies
BEFORE UPDATE ON movies
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_update_clients
BEFORE UPDATE ON clients
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER trigger_update_subscriptions
BEFORE UPDATE ON subscriptions
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Adding indexes for performance optimization
CREATE INDEX idx_client_subscriptions_client_id ON client_subscriptions(client_id);
CREATE INDEX idx_client_subscriptions_subscription_id ON client_subscriptions(subscription_id);
CREATE INDEX idx_watchlist_client_id ON watchlist(client_id);
CREATE INDEX idx_watchlist_movie_id ON watchlist(movie_id);
CREATE INDEX idx_ratings_client_id ON ratings(client_id);
CREATE INDEX idx_ratings_movie_id ON ratings(movie_id);
CREATE INDEX idx_reviews_client_id ON reviews(client_id);
CREATE INDEX idx_reviews_movie_id ON reviews(movie_id);
