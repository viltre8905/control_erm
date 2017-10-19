package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.AnswerDAO;
import com.verynet.gcint.api.model.AffirmativeAnswer;
import com.verynet.gcint.api.model.Answer;
import com.verynet.gcint.api.model.NegativeAnswer;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public class HibernateAnswerDAO extends HibernateGeneralDAO implements AnswerDAO {
    public HibernateAnswerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        currentSession().saveOrUpdate(answer);
        return answer;
    }

    @Override
    public Answer getAnswer(Integer id) {
        return (Answer) currentSession().get(Answer.class, id);
    }

    @Override
    public List<Answer> getAllAnswers(Integer processId) {
        return currentSession().createCriteria(Answer.class).createAlias("process", "p")
                .add(Restrictions.eq("p.id", processId)).list();
    }

    @Override
    public List<NegativeAnswer> getAllNegativeAnswers(String componentCode, Integer processId) {
        return currentSession().createCriteria(NegativeAnswer.class).createAlias("process", "p")
                .createAlias("component", "c").add(Restrictions.and(Restrictions.eq("p.id", processId),
                        Restrictions.eq("c.code", componentCode))).list();
    }

    @Override
    public List<AffirmativeAnswer> getAffirmativeAnswers(String componentCode, Integer processId) {
        return currentSession().createCriteria(AffirmativeAnswer.class).createAlias("process", "p")
                .createAlias("component", "c").add(Restrictions.and(Restrictions.eq("p.id", processId),
                        Restrictions.eq("c.code", componentCode))).list();
    }

    @Override
    public List<AffirmativeAnswer> getAffirmativeAnswers(Integer entityId, String componentCode) {
        return currentSession().createCriteria(AffirmativeAnswer.class).createAlias("component", "c")
                .createAlias("process", "p").createAlias("p.entity", "e").add(Restrictions.and
                        (Restrictions.eq("c.code", componentCode), Restrictions.eq("e.id", entityId))).list();
    }

    @Override
    public NegativeAnswer getNegativeAnswer(Integer activityId) {
        List<NegativeAnswer> answers = currentSession().createCriteria(NegativeAnswer.class)
                .createAlias("activity", "ac").add(Restrictions.eq("ac.id", activityId)).list();
        return answers.size() > 0 ? answers.get(0) : null;
    }

    @Override
    public List<AffirmativeAnswer> getAllAffirmativeAnswers() {
        return currentSession().createCriteria(AffirmativeAnswer.class).list();
    }

    @Override
    public boolean deleteAnswer(Integer id) {
        Answer answer = getAnswer(id);
        if (answer != null) {
            currentSession().delete(answer);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllAnswer(Integer entityId) {
        List<Answer> answers = currentSession().createCriteria(Answer.class).createAlias("process", "p")
                .createAlias("p.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
        for (Answer answer : answers) {
            currentSession().delete(answer);
        }
    }
}
