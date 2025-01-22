package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.dao.QuestionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.CreateMessageDto;
import com.techelevator.model.Message;
import com.techelevator.model.Question;
import com.techelevator.model.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
@RestController
@RequestMapping(path = "/questions")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class QuestionController {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    //CREATE
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create")
    public Question createQuestion(@RequestBody QuestionDto questionDto, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " created a new question");
        return questionDao.createQuestion(questionDto, userDao.getUserIdByUsername(principal.getName()));
    }

}
