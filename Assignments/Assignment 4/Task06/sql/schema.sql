CREATE DATABASE IF NOT EXISTS campus_events;
USE campus_events;
CREATE TABLE IF NOT EXISTS events(
 id INT PRIMARY KEY AUTO_INCREMENT,
 title VARCHAR(150) NOT NULL,
 event_date DATE NOT NULL
);
CREATE TABLE IF NOT EXISTS registrations(
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 student_id VARCHAR(30) NOT NULL,
 student_name VARCHAR(120) NOT NULL,
 email VARCHAR(180) NOT NULL,
 event_id INT NOT NULL,
 registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_registration_event FOREIGN KEY(event_id) REFERENCES events(id),
 CONSTRAINT uq_student_event UNIQUE(student_id,event_id),
 INDEX idx_event_id(event_id),
 INDEX idx_student_id(student_id)
);
INSERT INTO events(title,event_date)
SELECT 'Java GUI Workshop','2026-12-10'
WHERE NOT EXISTS(SELECT 1 FROM events WHERE title='Java GUI Workshop');
