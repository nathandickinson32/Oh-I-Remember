package com.techelevator.dao;

import com.techelevator.model.Question;
import com.techelevator.model.QuestionDto;

public interface QuestionDao {

    //CREATE
    public Question createQuestion(QuestionDto questionDto, int userId);
}
