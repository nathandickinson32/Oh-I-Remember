package com.techelevator.dao;

import com.techelevator.model.Category;
import com.techelevator.model.Question;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcCategoryDao implements CategoryDao {
    private JdbcTemplate template;
    public JdbcCategoryDao(DataSource ds){template = new JdbcTemplate(ds);}

    @Override
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM categories;";
        try{
            SqlRowSet results = template.queryForRowSet(sql);
            while (results.next()){
                Category category = new Category();
                category= mapRowToCategory(results);
                categoryList.add(category);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Category DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Category DAO] Unable to retrieve Categories");
        }
        return categoryList;
    }

    public List<Category> getAllCategoriesByQuestionId(int questionId) {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT c.category_id, c.category_name FROM categories c JOIN question_categories qc ON c.category_id = qc.category_id WHERE qc.question_id = ?;";
        try{
            SqlRowSet results = template.queryForRowSet(sql, questionId);
            while (results.next()){
                Category category = new Category();
                category= mapRowToCategory(results);
                categoryList.add(category);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Category DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Category DAO] Unable to retrieve Categories");
        }
        return categoryList;
    }

    //MAP
    public Category mapRowToCategory(SqlRowSet results){
        Category category = new Category();
        category.setCategoryId(results.getInt("category_id"));
        category.setCategoryName(results.getString("category_name"));
        return category;

    }
}
