package com.techelevator.dao;

import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcQuestionDao implements QuestionDao{
    private JdbcTemplate template;
    public JdbcQuestionDao(DataSource ds){template = new JdbcTemplate(ds);}

    @Autowired
    private NotificationDao notificationDao;
    @Autowired
    private CategoryDao categoryDao;

    //CREATE
    @Override
    public Question createQuestion(QuestionDto questionDto, int userId) {
        String sql = "INSERT INTO questions(sender_id, receiver_id, question, created_at) VALUES ( ?, ?, ?, ?) RETURNING question_id;";
        int questionId = -1;
        try {
            questionId = template.queryForObject(
                    sql,
                    Integer.class,
                    userId,
                    questionDto.getReceiverId(),
                    questionDto.getQuestion(),
                    LocalDateTime.now()

            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to create a new Question.");
        }

        if(questionDto.getCategoryIds()!=null && !questionDto.getCategoryIds().isEmpty()){
            assignCategoryToQuestion(questionId, questionDto.getCategoryIds());
        }

        CreateNotificationDto createNotificationDto = new CreateNotificationDto();
        createNotificationDto.setUserId(questionDto.getReceiverId());
        createNotificationDto.setType("new_question");
        createNotificationDto.setReferenceId(questionId);
        notificationDao.createNotification(createNotificationDto);

        return getQuestionById(questionId);
    }


    public void assignCategoryToQuestion(int questionId, List<Integer> categoryIds){
        String sql = "Insert INTO question_categories (question_id, category_id) VALUES(?, ?);";

        try {
            for(int categoryId: categoryIds){
                template.update(sql, questionId, categoryId);

            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to add to categories to question.");
        }
    }


    //READ
    public Question getQuestionById(int questionId){
        Question question = null;
        String sql = "SELECT * FROM questions WHERE question_id = ?;";

        try{
            SqlRowSet results = template.queryForRowSet(sql,questionId);
            if(results.next()){
                question = mapRowToQuestion(results);
                List<Category>categories = categoryDao.getAllCategoriesByQuestionId(questionId);
                if(categories!=null&& !categories.isEmpty()){
                    question.setCategories(categories);
                }
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to retrieve question by id: " + questionId);
        }

        return question;
    }

    public List<Question> getQuestionsByReceiverId(int receiverId){
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE receiver_id = ? AND is_answered = false;";

        try{
            SqlRowSet results = template.queryForRowSet(sql,receiverId);
            while(results.next()){
                Question question = new Question();
                question = mapRowToQuestion(results);
                questions.add(question);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to retrieve questions by receiver id: " + receiverId);
        }

        for(Question question: questions){
            List<Category>categories = categoryDao.getAllCategoriesByQuestionId(question.getQuestionId());
            question.setCategories(categories);
        }
            return questions;
    }

    public List<Question> getQuestionsBySenderId(int senderId){
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE sender_id = ? AND is_answered = false;";

        try{
            SqlRowSet results = template.queryForRowSet(sql,senderId);
            while(results.next()){
                Question question = new Question();
                question = mapRowToQuestion(results);
                questions.add(question);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to retrieve questions by receiver id: " + senderId);
        }
        for(Question question: questions){
            List<Category>categories = categoryDao.getAllCategoriesByQuestionId(question.getQuestionId());
            question.setCategories(categories);
        }
        return questions;
    }
//question history

    public List<Question> getQuestionsByUserId(int userId){
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE (sender_id = ? OR receiver_id = ?) AND is_answered = true ORDER BY question_id DESC;";

        try{
            SqlRowSet results = template.queryForRowSet(sql,userId,userId);
            while(results.next()){
                Question question = new Question();
                question = mapRowToQuestion(results);
                questions.add(question);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Message DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Message DAO] Unable to retrieve questions by user id: " + userId);
        }
        for(Question question: questions){
            List<Category>categories = categoryDao.getAllCategoriesByQuestionId(question.getQuestionId());
            question.setCategories(categories);
        }
        return questions;
    }

    //UPDATE
    @Override
    public Question answerQuestion(AnswerDto answerDto, int userId) {
        String sql = "UPDATE questions SET answer = ?, is_answered = ?, answered_at = ? WHERE question_id =?";
        try {
             template.update(
                    sql,
                    answerDto.getAnswer(),
                    true,
                    LocalDateTime.now(),
                    answerDto.getQuestionId()


            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to create a new Question.");
        }
        CreateNotificationDto createNotificationDto = new CreateNotificationDto();
        createNotificationDto.setUserId(answerDto.getSenderId());
        createNotificationDto.setType("answer_response");
        createNotificationDto.setReferenceId(answerDto.getQuestionId());
        notificationDao.createNotification(createNotificationDto);
        return getQuestionById(answerDto.getQuestionId());
    }
    @Override
    public Question updateQuestion(UpdateQuestionDto updateQuestionDto, int userId) {
        String updateSql = "UPDATE questions SET question = ? WHERE question_id = ?";

        try {
            template.update(updateSql, updateQuestionDto.getQuestion(), updateQuestionDto.getQuestionId());

            if (updateQuestionDto.getCategoryIds() != null) {
                updateQuestionCategories(updateQuestionDto.getQuestionId(), updateQuestionDto.getCategoryIds());
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to update the Question.");
        }

        return getQuestionById(updateQuestionDto.getQuestionId());
    }

    public Question updateQuestionCategories(int questionId, List<Integer> categoryIds) {
        String deleteSql = "DELETE FROM question_categories WHERE question_id = ?";
        String insertSql = "INSERT INTO question_categories (question_id, category_id) VALUES (?, ?)";

        try {
            template.update(deleteSql, questionId);

            for (int categoryId : categoryIds) {
                template.update(insertSql, questionId, categoryId);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to update categories for question.");
        }
        return getQuestionById(questionId);
    }



    //DELETE
    public void deleteQuestion(int questionId){
        String deleteCategories = "DELETE FROM question_categories WHERE question_id = ?";
        String deleteNotifications = "DELETE FROM notifications WHERE reference_id = ?";
        String sql = "DELETE FROM questions WHERE question_id =?;";

        try{
            template.update(deleteCategories, questionId);
            template.update(deleteNotifications, questionId);
            template.update(sql,questionId);
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to delete Question.");
        }

    }

    //MAP
    public Question mapRowToQuestion(SqlRowSet results){
       Question question = new Question();
        question.setQuestionId(results.getInt("question_id"));
        question.setSenderId(results.getInt("sender_id"));
        question.setReceiverId(results.getInt("receiver_id"));
        question.setQuestion(results.getString("question"));
        question.setAnswer(results.getString("answer"));
        question.setAnswered(results.getBoolean("is_answered"));
        question.setCreatedAt(results.getTimestamp("created_at") != null
                ? results.getTimestamp("created_at").toLocalDateTime()
                : null);
        question.setAnsweredAt(results.getTimestamp("answered_at") != null
                ? results.getTimestamp("answered_at").toLocalDateTime()
                : null);

        return question;
    }




}
