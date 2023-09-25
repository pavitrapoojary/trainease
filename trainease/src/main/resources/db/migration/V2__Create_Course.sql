CREATE TABLE IF NOT EXISTS courses (
    course_id VARCHAR(255) PRIMARY KEY,
    batch_id VARCHAR(255) NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    link VARCHAR(255),
    duration_in_hours DOUBLE,
    estimated_start_date DATE,
    estimated_end_date DATE,
    subject_matter_expert VARCHAR(255),
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id)
);
