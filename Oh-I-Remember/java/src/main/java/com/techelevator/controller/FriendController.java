package com.techelevator.controller;

import com.techelevator.dao.FriendDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.FriendRequestResponseDto;
import com.techelevator.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
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
    @PostMapping(path = "/friend-request")
    public ResponseEntity<?> createFriendRequest(@RequestBody CreateFriendRequestDto createFriendRequestDto, Principal principal) {
        try {
            int userId = userDao.getUserIdByUsername(principal.getName());

            boolean exists = friendDao.checkPendingRequest(userId, createFriendRequestDto.getReceiverId());
            if (exists) {
                //ResponseEntity allows for any object to be returned
                //Here it is returning a map with a message key and a String message for the
                //front to read and display
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("message", "A pending friend request already exists."));
            }

            FriendRequest request = friendDao.createFriendRequest(createFriendRequestDto, userId);
            return ResponseEntity.ok(request);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An error occurred while processing the request. Make sure you entered a valid ID."));
        }
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
