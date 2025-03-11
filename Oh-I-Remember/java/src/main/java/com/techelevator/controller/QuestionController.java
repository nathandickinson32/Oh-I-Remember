package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.dao.QuestionDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

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

    //READ
    @GetMapping(path = "/questions")
    public List<Question> getQuestionsByReceiverId(Principal principal){
       int receiverId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved questions for receiver id " + receiverId);
        return questionDao.getQuestionsByReceiverId(receiverId);
    }
    @GetMapping(path = "/do-you-remember")
    public List<Question> getQuestionsBySenderId(Principal principal){
        int senderId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved questions for sender id " + senderId);
        return questionDao.getQuestionsBySenderId(senderId);
    }
    @GetMapping(path = "/questions-by-user-id")
    public List<Question> getQuestionsByUserId(Principal principal){
        int userId = userDao.getUserIdByUsername(principal.getName());
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved questions for sender id " + userId);
        return questionDao.getQuestionsByUserId(userId);
    }

    @GetMapping(path = "/question-id/{questionId}")
    public Question getQuestionByQuestionId(@PathVariable int questionId, Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved questions for question id " + questionId);
        return questionDao.getQuestionById(questionId);
    }



    //UPDATE
    @PutMapping(path = "/answer")
    public Question answerQuestion(@RequestBody AnswerDto answerDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added answer to question " + answerDto.getQuestionId());
        return questionDao.answerQuestion(answerDto, userDao.getUserIdByUsername(principal.getName()));
    }

    @PutMapping(path = "/update-question")
    public Question updateQuestion(@RequestBody UpdateQuestionDto updateQuestionDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added answer to question " + updateQuestionDto.getQuestionId());
        return questionDao.updateQuestion(updateQuestionDto, userDao.getUserIdByUsername(principal.getName()));
    }

    @PutMapping(path = "/update-question-categories")
    public Question updateQuestionCategories(@RequestBody UpdateQuestionDto updateQuestionDto, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " added answer to question " + updateQuestionDto.getQuestionId());
        List<Integer> categoryIds = updateQuestionDto.getCategoryIds();
        int questionId = updateQuestionDto.getQuestionId();
        return questionDao.updateQuestionCategories(questionId, categoryIds);
    }
    //DELETE
    @DeleteMapping(path="/delete/{questionId}")
    public void deleteQuestion(@PathVariable int questionId, Principal principal){
        System.out.println(LocalDateTime.now() + "User: " + principal.getName() + " deleted question " + questionId);
        questionDao.deleteQuestion(questionId);


    }

}
