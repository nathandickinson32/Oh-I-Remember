package com.techelevator.controller;

import com.techelevator.dao.CategoryDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Category;
import com.techelevator.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserDao userDao;

    @GetMapping(path = "/categories")
    public List<Category> getAllCategories(Principal principal){
        System.out.println(LocalDateTime.now() + " User: " + principal.getName() + " retrieved all categories");
        return categoryDao.getAllCategories();
    }
}
