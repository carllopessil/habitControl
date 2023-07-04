DROP TABLE IF EXISTS tasks;


CREATE TABLE tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    due_date DATE NOT NULL,
    priority INT NOT NULL
);
