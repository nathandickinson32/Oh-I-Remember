package com.techelevator.dao;

import com.techelevator.model.ChatRoom;
import com.techelevator.model.CreateChatRoomDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Component
public class JdbcChatRoomDao implements ChatRoomDao{

    private JdbcTemplate template;

    public JdbcChatRoomDao(DataSource ds){template = new JdbcTemplate(ds);}


    //CREATE
    @Override
    public ChatRoom createChatRoom(CreateChatRoomDTO createChatRoomDTO, int userId) {
        String sql = "INSERT INTO chat_rooms(room_name, is_group, created_by_user_id, created_at) VALUES (?,?,?,?) RETURNING room_id;";
        int chatRoomId = -1;

        try {
        chatRoomId = template.queryForObject(
                sql,
                int.class,
                createChatRoomDTO.getRoomName(),
                createChatRoomDTO.isGroup(),
                userId,
                LocalDateTime.now()
        );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Chat Room DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Chat Room DAO] Unable to create a new Chat Room.");
        }
        return getChatRoomById(chatRoomId);
    }


    //READ
    public ChatRoom getChatRoomById(int id){
        ChatRoom chatRoom = null;
        String sql = "SELECT * FROM chat_rooms WHERE room_id = ?;";
        try{
            SqlRowSet results = template.queryForRowSet(sql,id);
            if(results.next()){
                chatRoom = mapRowToChatRoom(results);
            }
        } catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Chat Room DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Chat Room DAO] Unable to get chat room: " + id);
        }
        return chatRoom;
    }

public void addMemberToChatRoom(int roomId, int userId){
        String sql = "INSERT INTO room_members (room_id, user_id, joined_at) VALUES (?,?,?);";
        template.update(sql,roomId,userId,LocalDateTime.now());
}



    //MAP
    public ChatRoom mapRowToChatRoom(SqlRowSet results){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomId(results.getInt("room_id"));
        chatRoom.setRoomName(results.getString("room_name"));
        chatRoom.setGroup(results.getBoolean("is_group"));
        chatRoom.setCreatedByUserId(results.getInt("created_by_user_id"));
        chatRoom.setCreatedAt(results.getTimestamp("created_at").toLocalDateTime());
        return chatRoom;
    }

}
