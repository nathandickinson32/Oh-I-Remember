package com.techelevator.dao;

import com.techelevator.model.*;
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


    //CREATE
    @Override
    public Question createQuestion(QuestionDto questionDto, int userId) {
        String sql = "INSERT INTO questions(room_id, sender_id, receiver_id, question, created_at) VALUES (?, ?, ?, ?, ?) RETURNING question_id;";
        int questionId = -1;
        try {
            questionId = template.queryForObject(
                    sql,
                    int.class,
                    questionDto.getRoomId(),
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
        return getQuestionById(questionId);
    }


    //READ
    public Question getQuestionById(int questionId){
        Question question = null;
        String sql = "SELECT * FROM questions WHERE question_id = ?;";

        try{
            SqlRowSet results = template.queryForRowSet(sql,questionId);
            if(results.next()){
                question = mapRowToQuestion(results);
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
        return getQuestionById(answerDto.getQuestionId());
    }
    @Override
    public Question updateQuestion(UpdateQuestionDto updateQuestionDto, int userId) {
        String sql = "UPDATE questions SET question = ? WHERE question_id =?";
        try {
            template.update(
                    sql,
                    updateQuestionDto.getQuestion(),
                    updateQuestionDto.getQuestionId()
            );
        }catch (CannotGetJdbcConnectionException e){
            throw new CannotGetJdbcConnectionException("[JDBC Question DAO] Unable to connect to the database.");
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("[JDBC Question DAO] Unable to create a new Question.");
        }
        return getQuestionById(updateQuestionDto.getQuestionId());
    }


    //MAP
    public Question mapRowToQuestion(SqlRowSet results){
       Question question = new Question();
        question.setQuestionId(results.getInt("question_id"));
        question.setRoomId(results.getInt("room_id"));
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
