BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
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
 CREATE TABLE messages (
 message_id serial PRIMARY KEY,
 sent_by_id INT NOT NULL,
 received_by_id INT NOT NULL,
 message VARCHAR NOT NULL,
 date DATE NOT NULL,
 is_archived BOOLEAN NOT NULL,
 archived_notes VARCHAR,
 FOREIGN KEY (sent_by_id) REFERENCES users (user_id),
 FOREIGN KEY (received_by_id) REFERENCES users (user_id)

 );


COMMIT TRANSACTION;
