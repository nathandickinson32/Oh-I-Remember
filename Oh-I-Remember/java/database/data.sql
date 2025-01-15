BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role,first_name, last_name) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Bob', 'Boberson');
INSERT INTO users (username,password_hash,role,first_name, last_name) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'Nathan', 'Dickinson');


INSERT INTO messages(sent_by_id, received_by_id, message, date, is_archived, archived_notes ) VALUES (1, 2, 'Default Message', '2025-01-15', false, 'Default Archive Notes');

COMMIT TRANSACTION;
