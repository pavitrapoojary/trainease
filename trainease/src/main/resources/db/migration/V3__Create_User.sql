CREATE TABLE IF NOT EXISTS users (
    email_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    batch_id VARCHAR(255),
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id)
);
