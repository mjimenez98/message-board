CREATE DATABASE message_board;
USE message_board;

SET GLOBAL max_allowed_packet = 16177215;

CREATE TABLE posts (
    post_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    membership VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    message TEXT NOT NULL
);

CREATE TABLE attachments (
    attachment_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    post_id INTEGER NOT NULL,
    file MEDIUMBLOB NOT NULL,
    name VARCHAR(255) NOT NULL,
    size INTEGER NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT fk_post_id FOREIGN KEY (post_id) REFERENCES Posts(post_id)
);
