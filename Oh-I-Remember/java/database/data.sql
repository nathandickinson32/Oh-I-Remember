BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role,first_name, last_name) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Bob', 'Boberson');
INSERT INTO users (username,password_hash,role,first_name, last_name) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'Nathan', 'Dickinson');

INSERT INTO chat_rooms(room_name, is_group, created_at) VAlUES('Default Group', true, '2025-01-10 08:00:00');
INSERT INTO room_members(room_id, user_id, joined_at) VALUES (1, 2, '2025-01-10 08:00:00');
INSERT INTO room_members(room_id, user_id, joined_at) VALUES (1, 1, '2025-01-10 08:00:01');
INSERT INTO messages(room_id, sender_id, message, created_at, is_archived) VALUES (1, 2, 'Hello World!', '2025-01-10 09:00:00', false);
INSERT INTO messages(room_id, sender_id, message, created_at, is_archived) VALUES (1, 1, 'It is just me... Bob ', '2025-01-10 09:01:00', false);


COMMIT TRANSACTION;
