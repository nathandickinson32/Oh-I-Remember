package com.techelevator.dao;

import com.techelevator.model.CreateNotificationDto;
import com.techelevator.model.Notification;

import java.util.List;

public interface NotificationDao {

    //CREATE
    public Notification createNotification(CreateNotificationDto createNotificationDto);

    //READ
    public int numberOfQuestionNotifications(int userId);
    public int numberOfFriendRequestNotifications(int userId);


    //UPDATE
    public Notification markAsRead(int notificationId);

}
