SELECT * FROM users;
SELECT * FROM chat_rooms;
SELECT * FROM room_members;
SELECT * FROM messages;
SELECT message FROM messages WHERE room_id = 1 ORDER BY created_at DESC;