package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Component
public class JdbcFriendRequestDao implements FriendRequestDao{
    private JdbcTemplate template;
    JdbcFriendRequestDao(DataSource ds){template = new JdbcTemplate(ds);}

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
}
