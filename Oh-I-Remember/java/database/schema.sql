BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS chat_rooms;
DROP TABLE IF EXISTS room_members;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS friend_requests;
DROP TABLE IF EXISTS friends;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
CREATE TABLE chat_rooms (
room_id SERIAL PRIMARY KEY,
room_name VARCHAR(100),
is_group BOOLEAN,
created_by_user_id INT NOT NULL,
created_at TIMESTAMP
);

CREATE TABLE room_members (
room_id INT NOT NULL,
user_id INT NOT NULL,
joined_at TIMESTAMP NOT NULL,
PRIMARY KEY (room_id, user_id)
);

 CREATE TABLE messages (
 message_id SERIAL PRIMARY KEY,
 room_id INT NOT NULL,
 sender_id INT NOT NULL,
 message VARCHAR NOT NULL,
 created_at TIMESTAMP NOT NULL,
 is_archived BOOLEAN NOT NULL,
 FOREIGN KEY (room_id) REFERENCES chat_rooms (room_id),
 FOREIGN KEY (sender_id) REFERENCES users (user_id)
 );

 CREATE TABLE questions(
 question_id SERIAL PRIMARY KEY,
 sender_id INT NOT NULL,
 receiver_id INT NOT NULL,
 question VARCHAR NOT NULL,
 answer VARCHAR,
 is_answered BOOLEAN DEFAULT FALSE,
 created_at TIMESTAMP,
 answered_at TIMESTAMP,
 FOREIGN KEY (sender_id) REFERENCES users (user_id),
 FOREIGN KEY (receiver_id) REFERENCES users (user_id)
 );

 CREATE TABLE statuses (
 status_id SERIAL PRIMARY KEY,
 status_name VARCHAR(20) UNIQUE NOT NULL
 );

CREATE TABLE friend_requests(
request_id SERIAL PRIMARY KEY,
sender_id INT NOT NULL,
receiver_id INT NOT NULL,
status_id INT NOT NULL,
created_at TIMESTAMP NOT NULL,
FOREIGN KEY (sender_id) REFERENCES users (user_id),
FOREIGN KEY (receiver_id) REFERENCES users (user_id)
);

CREATE TABLE friends (
friendship_id SERIAL PRIMARY KEY,
user_id1 INT NOT NULL,
user_id2 INT NOT NULL,
created_at TIMESTAMP NOT NULL,
FOREIGN KEY (user_id1) REFERENCES users(user_id),
FOREIGN KEY (user_id2) REFERENCES users(user_id)
);


COMMIT TRANSACTION;
