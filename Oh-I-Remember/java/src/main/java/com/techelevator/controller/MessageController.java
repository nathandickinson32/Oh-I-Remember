package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.messages.CreateMessageDto;
import com.techelevator.model.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path= "/messages")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class MessageController {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserDao userDao;

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/create")
    public Message createMessage(@RequestBody CreateMessageDto createMessageDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " created a new message.");
        return messageDao.createMessage(createMessageDto, userDao.getUserIdByUsername(principal.getName()));
    }



    //READ
    @GetMapping(path="/{messageId}")
    public Message getMessageById(@PathVariable int messageId, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " accessed message " + messageId);
        return messageDao.getMessageById(messageId);
    }



    //UPDATE




    //DELETE
}
