package com.techelevator.dao;

import com.techelevator.model.*;
import com.techelevator.model.user.User;
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
public class JdbcFriendDao implements FriendDao {
    private JdbcTemplate template;
    JdbcFriendDao(DataSource ds){template = new JdbcTemplate(ds);}

    //CREATE
    @Override
    public FriendRequest createFriendRequest(CreateFriendRequestDto createFriendRequestDto, int userId) {
        String sql = "INSERT INTO friend_requests(sender_id, receiver_id, status_id, created_at) VALUES (?, ?, ?, ?) RETURNING request_id;";
        int requestId = -1;

        try{
            requestId = template.queryForObject(
                    sql,
                    int.class,
                    userId,
                    createFriendRequestDto.getReceiverId(),
                    1,
                    LocalDateTime.now()
            );

        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Friend Request DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Friend Request DAO] Unable to create a new Friend Request.");
        }
        return getFriendRequestById(requestId);
    }

    public void createFriendship(CreateFriendshipDto createFriendshipDto){
        int friendshipId = -1;

        String sql = "INSERT INTO friends(user_id1, user_id2, created_at) VALUES(?, ?, ?) RETURNING friendship_id;";

        try {
            friendshipId = template.queryForObject(
                    sql,
                    int.class,
                    createFriendshipDto.getUserId1(),
                    createFriendshipDto.getUserId2(),
                    LocalDateTime.now()
            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Friendship Request DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Friendship Request DAO] Unable to create a new Friendship.");
        }

    }


    //READ

    public List<User> getFriends(int userId){
        List<User> friendsList = new ArrayList<>();
        String sql = "SELECT u.user_id, u.username, u.first_name, u.last_name FROM friends f " +
                "JOIN users u ON (f.user_id1 = u.user_id OR f.user_id2 = u.user_id) " +
                "WHERE u.user_id != ? AND (? IN (f.user_id1,f.user_id2));";
        try {
            SqlRowSet results = template.queryForRowSet(sql, userId, userId);
            while(results.next()){
                User user = new User();
                user= mapRowToUser(results);
                friendsList.add(user);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to retrieve friends list by user id: " + userId);
        }
        return friendsList;
    }

    @Override
    public FriendRequest getFriendRequestById(int requestId) {
       FriendRequest friendRequest = null;

       String sql = "SELECT * FROM friend_requests WHERE request_id = ?;";

       try {
           SqlRowSet results = template.queryForRowSet(sql,requestId);
           if(results.next()){
               friendRequest = mapRowToFriendRequest(results);
           }
       }catch (CannotGetJdbcConnectionException e){
        throw new CannotGetJdbcConnectionException("[JDBC Friend Request DAO] Unable to connect to the database.");
    } catch (DataIntegrityViolationException e){
           throw new DataIntegrityViolationException("[JDBC Friend Request DAO] Unable to retrieve Friend Request by id: " + requestId);
    }

        return friendRequest;
    }

    public Friendship getFriendshipById(int friendshipId){
        Friendship friendship = null;

        String sql = "SELECT * FROM friends WHERE friendship_id = ?;";

        try {
            SqlRowSet results = template.queryForRowSet(sql, friendshipId);
        if(results.next()){
            friendship = mapRowToFriendShip(results);
        }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Friend Request DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Friend Request DAO] Unable to retrieve Friendship by id: " + friendshipId);
        }
        return friendship;
    }


    public boolean checkPendingRequest(int senderId, int receiverId){
        String sql = "SELECT COUNT(*) FROM friend_requests WHERE sender_id = ? AND receiver_id = ? AND status_id = 1";
        Integer count = template.queryForObject(sql, Integer.class, senderId, receiverId);

        return count != null && count > 0;
    }

    //UPDATE
    public FriendRequest friendRequestResponse(FriendRequestResponseDto friendRequestResponseDto){
        String sql = "UPDATE friend_requests SET status_id = ? WHERE request_id =?;";
        try {
            template.update(
                    sql,
                    friendRequestResponseDto.getStatusId(),
                    friendRequestResponseDto.getRequestId()
            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC FriendRequest DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC FriendRequest DAO] Unable to update Friend Request with id:" + friendRequestResponseDto.getRequestId());
        }

        if(friendRequestResponseDto.getStatusId()== 2){
            //*******************************************************
            //method for creating new friendship data
            FriendRequest friendRequest = new FriendRequest();
            friendRequest= getFriendRequestById(friendRequestResponseDto.getRequestId());
            CreateFriendshipDto createFriendshipDto = new CreateFriendshipDto();
            createFriendshipDto.setUserId1(friendRequest.getSenderId());
            createFriendshipDto.setUserId2(friendRequest.getReceiverId());
            createFriendship(createFriendshipDto);
            return getFriendRequestById(friendRequestResponseDto.getRequestId());

        }else{
            return getFriendRequestById(friendRequestResponseDto.getRequestId());
        }
    }


    //MAP
    public FriendRequest mapRowToFriendRequest(SqlRowSet results){
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setRequestId(results.getInt("request_id"));
        friendRequest.setSenderId(results.getInt("sender_id"));
        friendRequest.setReceiverId(results.getInt("receiver_id"));
        friendRequest.setStatusId(results.getInt("status_id"));
        friendRequest.setCreatedAt(results.getTimestamp("created_at").toLocalDateTime());

        return friendRequest;
    }

    public Friendship mapRowToFriendShip(SqlRowSet results){
        Friendship friendship = new Friendship();
        friendship.setFriendship_id(results.getInt("friendship_id"));
        friendship.setUserId1(results.getInt("user_id1"));
        friendship.setUserId2(results.getInt("user_id2"));
        friendship.setCreatedAt(results.getTimestamp("created_at").toLocalDateTime());
        return friendship;
    }
    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));

        return user;
    }
}
