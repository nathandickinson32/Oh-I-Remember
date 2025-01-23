package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping(path = "/get-user")
    public User getUser(Principal principal){
        System.out.println(LocalDateTime.now() + " git [User Controller] Accessing user " + principal.getName());
        return userDao.getUserByUsername(principal.getName());
    }

    @GetMapping(path = "/get-user/{id}")
    public User getUserById(@PathVariable int id, Principal principal){
        System.out.println(LocalDateTime.now() + " [User Controller] Accessing user id: " + id + " by user: " + principal.getName());
        return userDao.getUserById(id);
    }


}