package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.QuestionDAO;
import com.verynet.gcint.api.model.Answer;
import com.verynet.gcint.api.model.Question;

import java.util.List;

/**
 * Created by day on 11/09/2016.
 */
public interface QuestionService {
    public void setQuestionDAO(QuestionDAO dao);

    public Question saveQuestion(Question question);

    public void setAnswer(Integer questionId, Answer answer);

    public Question getQuestion(Integer id);

    public Question getQuestionFromAnswer(Integer answerId, boolean negativeAnswer);

    public List<Question> getAllQuestions();

    public List<Answer> getAllAnswers(Integer questionId);

    public boolean deleteQuestion(Integer id);
}
