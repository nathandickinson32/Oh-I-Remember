package com.techelevator.controller;

import com.techelevator.dao.FriendDao;
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
@RequestMapping(path = "/friends")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class FriendController {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserDao userDao;

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/friend-request")
    public FriendRequest createFriendRequest(@RequestBody CreateFriendRequestDto createFriendRequestDto, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " created a new friend request");
        return friendDao.createFriendRequest(createFriendRequestDto, userDao.getUserIdByUsername(principal.getName()));
    }

    //READ
    @GetMapping(path = "/friends-list")
    public List<User> getFriends(Principal principal){
        int userId = userDao.getUserIdByUsername(principal.getName());
        return friendDao.getFriends(userId);
    }
    //UPDATE
    @PutMapping(path = "/response")
    public FriendRequest friendRequestResponse(@RequestBody FriendRequestResponseDto friendRequestResponseDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added response to friend request " + friendRequestResponseDto.getRequestId());
        return friendDao.friendRequestResponse(friendRequestResponseDto);
    }
}
