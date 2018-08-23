CREATE TABLE exercise(
id BIGINT PRIMARY KEY     NOT NULL,
name VARCHAR(20),
description VARCHAR(200)
);


CREATE TABLE user(
id BIGINT PRIMARY KEY     NOT NULL,
username VARCHAR(20),
password VARCHAR(20),
role VARCHAR(20)

);


