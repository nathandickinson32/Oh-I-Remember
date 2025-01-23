package com.techelevator.dao;

import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.IdDto;
import com.techelevator.model.Question;
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


    //UPDATE
    public FriendRequest friendRequestResponse(IdDto requestId, )


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
}
