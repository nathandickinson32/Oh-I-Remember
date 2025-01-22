package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.dao.QuestionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
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

    //UPDATE
    @PutMapping(path = "/answer")
    public Question answerQuestion(@RequestBody AnswerDto answerDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added answer to question " + answerDto.getQuestionId());
        return questionDao.answerQuestion(answerDto, userDao.getUserIdByUsername(principal.getName()));
    }

}
