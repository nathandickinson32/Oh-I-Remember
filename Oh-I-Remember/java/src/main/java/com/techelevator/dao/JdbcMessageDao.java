package com.techelevator.dao;

import com.techelevator.model.messages.CreateMessageDto;
import com.techelevator.model.messages.Message;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JdbcMessageDao implements MessageDao {

    private JdbcTemplate template;
    public JdbcMessageDao(DataSource ds) { template = new JdbcTemplate(ds);}

    public Message createMessage(CreateMessageDto createMessageDto, int userId){
        String sql = "INSERT INTO messages(sent_by_id, received_by_id, message, date, is_archived) VALUES (?, ?, ?, ?, ?) RETURNING message_id;";
        int newMessageId = -1;
        try {
            newMessageId = template.queryForObject(
                    sql,
                     int.class,
                   userId,
                    createMessageDto.getReceivedById(),
                    createMessageDto.getMessage(),
                   new Date(),
                    false
            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to create message.");
        }
        return getMessageById(newMessageId);
    }

    public Message getMessageById(int messageId){
        Message message = null;
        String sql = "SELECT * FROM messages WHERE message_id = ?;";

        try {
            SqlRowSet results = template.queryForRowSet(sql,messageId);
            if(results.next()){
                message = mapRowToMessage(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to get kudos by ID: " + messageId);
        }
        return message;
    }

   public Message mapRowToMessage(SqlRowSet results) {
        Message message = new Message();
        message.setMessageId(results.getInt("message_id"));
        message.setSentById(results.getInt("sent_by_id"));
        message.setReceivedById(results.getInt("received_by_id"));
        message.setMessage(results.getString("message"));
        message.setDate(results.getDate("date"));
        message.setArchived(results.getBoolean("is_archived"));
        message.setArchivedNotes(results.getString("archived_notes"));
        return message;
    }
}
