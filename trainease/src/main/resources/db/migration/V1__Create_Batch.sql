CREATE TABLE IF NOT EXISTS batches (
    batch_id VARCHAR(255) PRIMARY KEY,
    batch_name VARCHAR(255) NOT NULL,
    batch_description VARCHAR(255)
);
