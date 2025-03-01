package com.techelevator.controller;

import com.techelevator.dao.FriendDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CreateFriendRequestDto;
import com.techelevator.model.FriendRequest;
import com.techelevator.model.FriendRequestResponseDto;
import com.techelevator.model.IdDto;
import com.techelevator.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
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

            // Check if the username exists before proceeding
            boolean userExists = userDao.doesUserExist(createFriendRequestDto.getUserName());
            if (!userExists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "The username does not exist."));
            }

            boolean exists = friendDao.checkPendingRequest(userId, createFriendRequestDto.getUserName());
            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("message", "A pending friend request already exists."));
            }

            FriendRequest request = friendDao.createFriendRequest(createFriendRequestDto, userId);
            if (request == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonMap("message", "Failed to create the friend request. Please try again."));
            }

            return ResponseEntity.ok(request);

        } catch (CannotGetJdbcConnectionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Database connection issue. Please try again later."));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Invalid username provided."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "An error occurred while processing the request."));
        }
    }


    //READ
    @GetMapping(path = "/friends-list")
    public List<User> getFriends(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        return friendDao.getFriends(userId);
    }

    @GetMapping(path = "/friend-requests")
    public List<FriendRequest> getFriendRequests(Principal principal) {
        int userId = userDao.getUserIdByUsername(principal.getName());
        return friendDao.getFriendRequests(userId);
    }

    @PostMapping(path = "/user-info")
    public List<User> getUserInfo(@RequestBody List<Integer> userIds, Principal principal) {
        return friendDao.getUsersById(userIds);
    }

    //UPDATE
    @PutMapping(path = "/request-response")
    public FriendRequest friendRequestResponse(@RequestBody FriendRequestResponseDto friendRequestResponseDto, Principal principal) {
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added response to friend request " + friendRequestResponseDto.getRequestId());
        return friendDao.friendRequestResponse(friendRequestResponseDto);
    }

    //DELETE
    @DeleteMapping(path = "/delete-request/{requestId}")
    public void deleteFriendRequest(@PathVariable int requestId, Principal principal) {
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " deleted request " + requestId);
        friendDao.deleteFriendRequest(requestId);
    }
    @DeleteMapping(path = "/delete-friend")
    public void deleteFriendship(@RequestBody IdDto idDto, Principal principal) {
        int userId1 = userDao.getUserIdByUsername(principal.getName());
        int userId2 = idDto.getId();
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " deleted friendship with user id " + userId2);
        friendDao.deleteFriendship(userId1, userId2);
    }
}
