CREATE TABLE Users (
id SERIAL PRIMARY KEY,
full_name VARCHAR(20) NOT NULL,
username VARCHAR(20) NOT NULL,
email VARCHAR(20) NOT NULL,
password VARCHAR(100) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
removed_at TIMESTAMP
);

CREATE TABLE video (
id SERIAL PRIMARY KEY,
title VARCHAR(50) NOT NULL,
uploader_id BIGINT NOT NULL REFERENCES users (id),
description VARCHAR(200),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
removed_at TIMESTAMP
);

CREATE TYPE subscribe AS ENUM ('Free', 'Plus', 'Premium');

CREATE TABLE subscription (
user_id BIGINT REFERENCES users (id),
plan_type subscribe DEFAULT 'Free' NOT NULL,
price DOUBLE NOT NULL,
start_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
end_date TIMESTAMP,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
removed_at CURRENT_TIMESTAMP
);

CREATE watch_list (
id SERIAL PRIMARY KEY,
video_id BIGINT REFERENCES video (id),
user_id BIGINT REFERENCES users (id),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
removed_at TIMESTAMP
);

CREATE TABLE rate (
id SERIAL PRIMARY KEY,
video_id BIGINT REFERENCES video (id),
user_id BIGINT REFERENCES users (id),
rate_value INTEGER NOT NULL DEFAULT 1,
comments VARCHAR(100),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
removed_at TIMESTAMP
);