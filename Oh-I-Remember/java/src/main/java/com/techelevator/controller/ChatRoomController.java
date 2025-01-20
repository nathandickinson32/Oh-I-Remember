package com.techelevator.controller;

import com.techelevator.dao.ChatRoomDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.ChatRoom;
import com.techelevator.model.CreateChatRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path="/chat-rooms")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ChatRoomController {
    @Autowired
    private ChatRoomDao chatRoomDao;
    @Autowired
    private UserDao userDao;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/create")
    public ChatRoom createChatRoom(@RequestBody CreateChatRoomDTO createChatRoomDTO, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " created a new chat room.");
        return chatRoomDao.createChatRoom(createChatRoomDTO, userDao.getUserIdByUsername(principal.getName()));
    }

    @PostMapping(path = "/{roomId}/add-member/{userId}")
    public void addMemberToChatRoom(@PathVariable int roomId, @PathVariable int userId, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added user " + userId + " to chat room " + roomId);
        chatRoomDao.addMemberToChatRoom(roomId,userId);
    }
}
