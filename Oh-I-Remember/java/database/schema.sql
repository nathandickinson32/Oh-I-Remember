BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS chat_rooms;
DROP TABLE IF EXISTS room_members;
DROP TABLE IF EXISTS messages;

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



COMMIT TRANSACTION;
