SELECT * FROM users;
SELECT * FROM chat_rooms;
SELECT * FROM room_members;
SELECT * FROM messages;
SELECT * FROM friend_requests;
SELECT * FROM friends;
SELECT * FROM questions;
SELECT * FROM notifications;

SELECT COUNT(*) FROM notifications WHERE user_id = 3 AND type = 'friend_request'
SELECT COUNT(*) FROM notifications WHERE user_id = 3 AND type = 'new_question';

SELECT message FROM messages WHERE room_id = 1 ORDER BY created_at DESC;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 1;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 3;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 1;
SELECT room_members.room_id FROM room_members JOIN users ON users.user_id = room_members.user_id WHERE room_members.user_id = 2;
SELECT * FROM messages WHERE room_id = 1;
INSERT INTO friend_requests(sender_id, receiver_id, created_at) VALUES (3, 2, '2025-01-10 09:00:00') RETURNING request_id;
SELECT * FROM friend_requests WHERE request_id = 6;

INSERT INTO questions(room_id, sender_id, receiver_id, question) VALUES(1,2,3,'Is this the second question?');
INSERT INTO questions(room_id, sender_id, receiver_id, question) VALUES(1,2,3,'Is this the third question?');
INSERT INTO questions(room_id, sender_id, receiver_id, question) VALUES(1,1,3,'Is this the fourth question?');
INSERT INTO questions(room_id, sender_id, receiver_id, question) VALUES(1,3,2,'Is this the fifth question?');
INSERT INTO questions(room_id, sender_id, receiver_id, question) VALUES(1,3,1,'Is this the sixth question?');
UPDATE questions SET is_answered = true WHERE question_id = 6

INSERT INTO friends(user_id1,user_id2, created_at) VALUES (3,4,'2025-01-10 09:00:00');

SELECT u.user_id, u.username, u.first_name, u.last_name FROM friend_requests f 
JOIN users u ON f.receiver_id = u.user_id  
WHERE receiver_id =1;

SELECT u.user_id, u.username, u.first_name, u.last_name
FROM friend_requests f
JOIN users u ON f.sender_id = u.user_id
WHERE f.receiver_id = 3 AND u.user_id != 3;