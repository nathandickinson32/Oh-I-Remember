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
import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> getMembersByRoomId(int roomId){
        List<Integer> memberIds = new ArrayList<>();
        String sql = "SELECT room_members.user_id FROM users JOIN room_members ON users.user_id = room_members.user_id WHERE room_members.room_id = ?;";
         try {
             SqlRowSet results = template.queryForRowSet(sql,roomId);
             while(results.next()){
                 Integer num = results.getInt("user_id");
                 memberIds.add(num);
             }

         }catch (CannotGetJdbcConnectionException e){
             throw new CannotGetJdbcConnectionException("[JDBC Chat Room DAO] Unable to connect to the database.");
         } catch (DataIntegrityViolationException e){
             throw new DataIntegrityViolationException("[JDBC Chat Room DAO] Unable to get members by Room Id.");
         }
        return memberIds;
    }

    public List<Integer> getRoomIdsByMemberId(int memberId){
        List<Integer> roomIds = new ArrayList<>();
        String sql = "SELECT room_members.room_id FROM room_members JOIN users ON users.user_id = room_members.user_id WHERE room_members.user_id = ?;";
        try {
            SqlRowSet results = template.queryForRowSet(sql,memberId);
            while(results.next()){
                Integer num = results.getInt("room_id");
                roomIds.add(num);
            }

        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Chat Room DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Chat Room DAO] Unable to get members by Room Id.");
        }
        return roomIds;
    }

    //UPDATE
public void addMemberToChatRoom(int roomId, int userId){
        String sql = "INSERT INTO room_members (room_id, user_id, joined_at) VALUES (?,?,?);";
        try {
            template.update(sql,roomId,userId,LocalDateTime.now());

        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Chat Room DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Chat Room DAO] Unable to add member to Chat Room.");
        }
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
