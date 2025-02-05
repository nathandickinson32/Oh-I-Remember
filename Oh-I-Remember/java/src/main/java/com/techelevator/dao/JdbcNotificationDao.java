package com.techelevator.dao;

import com.techelevator.model.CreateNotificationDto;
import com.techelevator.model.Notification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.relational.core.sql.Not;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class JdbcNotificationDao implements NotificationDao {
    private JdbcTemplate template;

    public JdbcNotificationDao(DataSource ds) {
        template = new JdbcTemplate(ds);
    }


    //CREATE
    @Override
    public Notification createNotification(CreateNotificationDto createNotificationDto) {
        String sql = "INSERT INTO notifications(user_id, type, reference_id, is_read, created_at) VALUES(?, ?, ?, ?, ?) RETURNING notification_id;";
        int notificationId = -1;

        try {
            notificationId = template.queryForObject(
                    sql,
                    Integer.class,
                    createNotificationDto.getUserId(),
                    createNotificationDto.getType(),
                    createNotificationDto.getReferenceId(),
                    false,
                    LocalDateTime.now()
            );
        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("[JDBC Notification DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("[JDBC Notification DAO] Unable to create a new Notification.");
        }

        return getNotificationById(notificationId);
    }


    ///READ

    @Override
    public int numberOfQuestionNotifications(int userId) {
        int numberOfNotifications = -1;
        String sql = "SELECT COUNT(*) FROM notifications WHERE user_id = ? AND type = 'new_question';";
        try {
            numberOfNotifications = template.queryForObject(sql, Integer.class, userId);
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Notification DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Notification DAO] Unable to retrieve new question Notification by id: " + userId);
        }
        return numberOfNotifications;
    }

    public int numberOfFriendRequestNotifications(int userId){
        return 0;
    }

    public Notification getNotificationById(int id) {
        Notification notification = new Notification();
        String sql = "SELECT * FROM notifications WHERE notification_id = ?;";
        try {
            SqlRowSet results = template.queryForRowSet(sql, id);
            if (results.next()) {
                notification = mapRowToNotification(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Notification DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Notification DAO] Unable to retrieve Notification by id: " + id);
        }
        return notification;
    }

    @Override
    public Notification markAsRead(int notificationId) {
        return null;
    }


    //MAP
    public Notification mapRowToNotification(SqlRowSet results) {
        Notification notification = new Notification();
        notification.setNotificationId(results.getInt("notification_id"));
        notification.setUserId(results.getInt("user_id"));
        notification.setType(results.getString("type"));
        notification.setReferenceId(results.getInt("reference_id"));
        notification.setRead(results.getBoolean("is_read"));
        notification.setCreatedAt(results.getTimestamp("created_at").toLocalDateTime());
        return notification;
    }
}