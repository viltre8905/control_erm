package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.QuestionDAO;
import com.verynet.gcint.api.model.Answer;
import com.verynet.gcint.api.model.NegativeAnswer;
import com.verynet.gcint.api.model.Question;
import com.verynet.gcint.api.services.QuestionService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 11/09/2016.
 */
@Transactional
public class QuestionServiceImpl implements QuestionService {
    private QuestionDAO dao;

    @Override
    public void setQuestionDAO(QuestionDAO dao) {
        this.dao = dao;
    }

    @Override
    public Question saveQuestion(Question question) {
        return dao.saveQuestion(question);
    }

    @Override
    public void setAnswer(Integer questionId, Answer answer) {
        Question question = dao.getQuestion(questionId);
        List<Answer> answers = question.getAnswers();
        if (answers != null) {
            for (int i = 0; i < answers.size(); i++) {
                if (answers.get(i).getProcess().getId().equals(answer.getProcess().getId())) {
                    Answer answer1 = answers.get(i);
                    Context.getAnswerService().deleteAnswer(answer1.getId());
                }
            }
        }
        question.getAnswers().add(answer);
        dao.saveQuestion(question);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestion(Integer id) {
        return dao.getQuestion(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionFromAnswer(Integer answerId, boolean negativeAnswer) {
        Question question = dao.getQuestionFromAnswer(answerId);
        if (negativeAnswer) {
            for (Answer answer : question.getAnswers()) {
                if (answer instanceof NegativeAnswer) {
                    Hibernate.initialize(((NegativeAnswer) answer).getDeficiency().getActivities());
                }
            }
        }
        return question;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return dao.getAllQuestions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Answer> getAllAnswers(Integer questionId) {
        Question question = getQuestion(questionId);
        Hibernate.initialize(question.getAnswers());
        return question.getAnswers();
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        return dao.deleteQuestion(id);
    }

}
