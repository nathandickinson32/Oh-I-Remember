package com.techelevator.dao;

import com.techelevator.model.AnswerDto;
import com.techelevator.model.Question;
import com.techelevator.model.QuestionDto;

import java.util.List;

public interface QuestionDao {

    //CREATE
    public Question createQuestion(QuestionDto questionDto, int userId);

    //READ
    public Question getQuestionById(int questionId);
    public List<Question> getQuestionsByReceiverId(int receiverId);


    //UPDATE
    public Question answerQuestion(AnswerDto answerDto, int userId);
}
