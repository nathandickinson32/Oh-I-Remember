package com.techelevator.controller;

import com.techelevator.dao.FriendRequestDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.FriendRequestResponseDto;
import com.techelevator.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/friend-requests")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class FriendRequestController {

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private UserDao userDao;

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create")
    public FriendRequest createFriendRequest(@RequestBody CreateFriendRequestDto createFriendRequestDto, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " created a new friend request");
        return friendRequestDao.createFriendRequest(createFriendRequestDto, userDao.getUserIdByUsername(principal.getName()));
    }

    //READ
    @GetMapping(path = "/friends")
    public List<User> getFriends(Principal principal){
        int userId = userDao.getUserIdByUsername(principal.getName());
        return friendRequestDao.getFriends(userId);
    }
    //UPDATE
    @PutMapping(path = "/response")
    public FriendRequest friendRequestResponse(@RequestBody FriendRequestResponseDto friendRequestResponseDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added response to friend request " + friendRequestResponseDto.getRequestId());
        return friendRequestDao.friendRequestResponse(friendRequestResponseDto);
    }
}
