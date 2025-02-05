package com.techelevator.controller;

import com.techelevator.dao.NotificationDao;
import com.techelevator.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/notifications")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class NotificationController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao notificationDao;

    //READ
    @GetMapping(path = "/question-notifications")
    public int numberOfQuestionNotifications(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + "Retrieved question notifications for User: " + principal.getName());
        return notificationDao.numberOfQuestionNotifications(userId);
    }
    @GetMapping(path = "/answer-notifications")
    public int numberOfNewAnswerNotifications(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + "Retrieved new answer notifications for User: " + principal.getName());
        return notificationDao.numberOfNewAnswerResponseNotifications(userId);
    }
    @GetMapping(path = "/new-friends-notifications")
    public int numberOfNewFriendRequestsNotifications(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + "Retrieved new friend notifications for User: " + principal.getName());
        return notificationDao.numberOfNewFriendsNotifications(userId);
    }
    @GetMapping(path = "/friend-request-notifications")
    public int numberOfFriendRequestNotifications(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + "Retrieved Friend Request notifications for User: " + principal.getName());
        return notificationDao.numberOfFriendRequestNotifications(userId);
    }
}
