SELECT * FROM users;
SELECT * FROM chat_rooms;
SELECT * FROM room_members;
SELECT * FROM messages;
SELECT message FROM messages WHERE room_id = 1 ORDER BY created_at DESC;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 1;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 3;
SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = 1;
SELECT room_members.room_id FROM room_members JOIN users ON users.user_id = room_members.user_id WHERE room_members.user_id = 2;