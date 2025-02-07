package com.techelevator.dao;

import com.techelevator.model.user.RegisterUserDto;
import com.techelevator.model.user.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);
    public boolean doesUserExist(String username);
    User createUser(RegisterUserDto user);
    public int getUserIdByUsername(String username);
}
