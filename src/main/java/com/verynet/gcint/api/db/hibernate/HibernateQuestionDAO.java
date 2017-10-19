package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.QuestionDAO;
import com.verynet.gcint.api.model.Question;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 10/09/2016.
 */
public class HibernateQuestionDAO extends HibernateGeneralDAO implements QuestionDAO {
    public HibernateQuestionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Question saveQuestion(Question question) {
        currentSession().saveOrUpdate(question);
        return question;
    }

    @Override
    public Question getQuestion(Integer id) {
        return (Question) currentSession().get(Question.class, id);
    }

    @Override
    public Question getQuestion(String title) {
        List<Question> questions = currentSession().createCriteria(Question.class).add(Restrictions.eq("title", title)).list();
        return questions.size() > 0 ? questions.get(0) : null;
    }

    @Override
    public Question getQuestionFromAnswer(Integer answerId) {
        List<Question> questions = currentSession().createCriteria(Question.class).createAlias("answers", "aws")
                .add(Restrictions.eq("aws.id", answerId)).list();
        return questions.size() > 0 ? questions.get(0) : null;
    }

    @Override
    public List<Question> getAllQuestions() {
        return currentSession().createCriteria(Question.class).list();
    }

    @Override
    public boolean deleteQuestion(Integer id) {
        Question question = getQuestion(id);
        if (question != null) {
            currentSession().delete(question);
            return true;
        }
        return false;
    }
}
