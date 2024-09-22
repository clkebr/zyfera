-- Create the 'students' table
CREATE TABLE students
(
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(255)        NOT NULL,
    surname               VARCHAR(255)        NOT NULL,
    std_number            VARCHAR(255) UNIQUE NOT NULL,
    insert_date_time      TIMESTAMP           NOT NULL,
    insert_user_id        BIGINT              NOT NULL,
    last_update_date_time TIMESTAMP           NOT NULL,
    last_update_user_id   BIGINT              NOT NULL,
    is_deleted            BOOLEAN DEFAULT FALSE
);

-- Create the 'grades' table
CREATE TABLE grades
(
    id                    BIGSERIAL PRIMARY KEY,
    code                  VARCHAR(255),
    value                 INT CHECK (value >= 0 AND value <= 100),
    insert_date_time      TIMESTAMP NOT NULL,
    insert_user_id        BIGINT    NOT NULL,
    last_update_date_time TIMESTAMP NOT NULL,
    last_update_user_id   BIGINT    NOT NULL,
    is_deleted            BOOLEAN DEFAULT FALSE,
    student_id            BIGINT,
    CONSTRAINT fk_student_grade FOREIGN KEY (student_id) REFERENCES students (id)
);
