package com.techelevator.controller;

import com.techelevator.dao.ChatRoomDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.ChatRoom;
import com.techelevator.model.CreateChatRoomDTO;
import com.techelevator.model.IdDto;
import com.techelevator.model.RoomMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path="/chat-rooms")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ChatRoomController {
    @Autowired
    private ChatRoomDao chatRoomDao;
    @Autowired
    private UserDao userDao;


    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path="/create")
    public ChatRoom createChatRoom(@RequestBody CreateChatRoomDTO createChatRoomDTO, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " created a new chat room.");
        return chatRoomDao.createChatRoom(createChatRoomDTO, userDao.getUserIdByUsername(principal.getName()));
    }

    //READ
    @GetMapping(path = "/room-members")
    public List<Integer> getMembersByRoomId(@RequestBody IdDto idDto, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved members for chat room " + idDto.getId());
        return chatRoomDao.getMembersByRoomId(idDto.getId());
    }

    @GetMapping(path = "/chat-rooms")
    public List<Integer> getRoomIdsByUserId(@RequestBody IdDto idDto, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved room ids for member " + idDto.getId());
        return chatRoomDao.getRoomIdsByMemberId(idDto.getId());
    }

    @PostMapping(path = "/add-member")
    public void addMemberToChatRoom(@RequestBody RoomMemberDto roomMemberDto, Principal principal){
        int roomId = roomMemberDto.getRoomId();
        int userId = roomMemberDto.getUserId();
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added user " + userId + " to chat room " + roomId);
        chatRoomDao.addMemberToChatRoom(roomId,userId);
    }
}
