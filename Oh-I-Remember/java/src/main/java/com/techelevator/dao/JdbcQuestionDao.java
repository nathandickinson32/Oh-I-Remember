package com.techelevator.dao;

import com.techelevator.model.Message;
import com.techelevator.model.Question;
import com.techelevator.model.QuestionDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDateTime;
@Component
public class JdbcQuestionDao implements QuestionDao{
    private JdbcTemplate template;
    public JdbcQuestionDao(DataSource ds){template = new JdbcTemplate(ds);}

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
        question.setCreatedAt(results.getTimestamp("created_at").toLocalDateTime());
        question.setAnsweredAt(results.getTimestamp("answered_at") != null
                ? results.getTimestamp("answered_at").toLocalDateTime()
                : null);
        return question;
    }

}
