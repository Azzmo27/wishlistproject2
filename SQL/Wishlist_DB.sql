
CREATE DATABASE IF NOT EXISTS wishlist_DB;
CREATE SCHEMA IF NOT EXISTS wishlist_DB;
USE wishlist_DB;

CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    birthday DATE
);


CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_name VARCHAR(255),
    wishlist_description VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE IF NOT EXISTS item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(255),
    item_description VARCHAR(255),
    wishlist_id INT,
    reserved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist(wishlist_id)
);


