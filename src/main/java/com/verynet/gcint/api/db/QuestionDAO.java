package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Question;

import java.util.List;

/**
 * Created by day on 10/09/2016.
 */
public interface QuestionDAO {
    public Question saveQuestion(Question question);

    public Question getQuestion(Integer id);

    public Question getQuestionFromAnswer(Integer answerId);

    public Question getQuestion(String title);

    public List<Question> getAllQuestions();

    public boolean deleteQuestion(Integer id);

}
