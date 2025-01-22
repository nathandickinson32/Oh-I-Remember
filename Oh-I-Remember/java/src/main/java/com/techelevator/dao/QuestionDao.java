package com.techelevator.dao;

import com.techelevator.model.AnswerDto;
import com.techelevator.model.Question;
import com.techelevator.model.QuestionDto;

public interface QuestionDao {

    //CREATE
    public Question createQuestion(QuestionDto questionDto, int userId);

    //READ
    public Question getQuestionById(int questionId);


    //UPDATE
    public Question answerQuestion(AnswerDto answerDto, int userId);
}
