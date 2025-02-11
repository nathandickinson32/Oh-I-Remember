package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Question;

import java.util.List;

public interface CategoryDao {


    //READ
    public List<Category> getAllCategories();
    public List<Category> getAllCategoriesByQuestionId(int questionId);
}
