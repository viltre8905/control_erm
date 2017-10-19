package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.GuideDAO;
import com.verynet.gcint.api.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 09/09/2016.
 */
public class HibernateGuideDAO extends HibernateGeneralDAO implements GuideDAO {
    public HibernateGuideDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Guide saveGuide(Guide guide) {
        currentSession().saveOrUpdate(guide);
        return guide;
    }

    @Override
    public Guide getGuide(Integer id) {
        return (Guide) currentSession().get(Guide.class, id);
    }


    @Override
    public List<Guide> getAllGuidesFromEntity(Integer entityId) {
        return currentSession().createCriteria(Guide.class).createAlias("process", "p")
                .createAlias("p.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<Guide> getAllGuides(String componentCode, Integer processId) {
        return currentSession().createCriteria(Guide.class).createAlias("component", "c")
                .createAlias("process", "p").add(Restrictions.and(Restrictions.eq("c.code", componentCode),
                        Restrictions.eq("p.id", processId))).list();
    }

    @Override
    public boolean deleteGuide(Integer id) {
        Guide guide = getGuide(id);
        if (guide != null) {
            currentSession().delete(guide);
            return true;
        }
        return false;
    }

    @Override
    public Aspect saveAspect(Aspect aspect) {
        currentSession().saveOrUpdate(aspect);
        return aspect;
    }

    @Override
    public Aspect getAspect(Integer id) {
        return (Aspect) currentSession().get(Aspect.class, id);
    }

    @Override
    public Long getAnswerCountFromEntity(Integer id, Integer type) {
        DetachedCriteria detachedCriteria = getCriteria(type);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e")
                .createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getQuestionsCountFromEntity(Integer id) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e")
                .add(Restrictions.eq("e.id", id))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getAnswerCount(Integer processId, Integer type) {
        DetachedCriteria detachedCriteria = getCriteria(type);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getQuestionsCount(Integer processId) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("process", "p")
                .createAlias("as.questions", "q").add(Restrictions.eq("p.id", processId))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getAnswerCount(Integer id, String componentCode, Integer type) {
        DetachedCriteria detachedCriteria = getCriteria(type);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e")
                .createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Restrictions.eq("c.code", componentCode), Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getQuestionsCount(Integer id, String componentCode) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Restrictions.eq("c.code", componentCode)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getAnswerCount(String componentCode, Integer processId, Integer type) {
        DetachedCriteria detachedCriteria = getCriteria(type);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q").createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Restrictions.eq("c.code", componentCode), Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getQuestionsCount(String componentCode, Integer processId) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Restrictions.eq("c.code", componentCode)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getProcedureCount(Integer id, String componentCode) {
        DetachedCriteria detachedCriteria = getCriteria(2);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Restrictions.eq("c.code", componentCode), Restrictions.eq("q.procedure", true)))
                .setProjection(Projections.rowCount()).uniqueResult() - (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c").createAlias("process", "p")
                .createAlias("as.questions", "q").createAlias("p.entity", "e").createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Restrictions.eq("c.code", componentCode), Restrictions.eq("q.procedure", true),
                        Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getProcedureCount(String componentCode, Integer processId) {
        DetachedCriteria detachedCriteria = getCriteria(2);
        detachedCriteria.setProjection(Property.forName("id"));
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Restrictions.eq("c.code", componentCode), Restrictions.eq("q.procedure", true)))
                .setProjection(Projections.rowCount()).uniqueResult() - (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q").createAlias("q.answers", "answ")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Restrictions.eq("c.code", componentCode), Restrictions.eq("q.procedure", true),
                        Property.forName("answ.id").in(detachedCriteria)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getProcedureAcceptedCount(Integer id, String componentCode) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q")
                .createAlias("p.entity", "e").createAlias("q.answers", "answ").createAlias("answ.evidence", "evid")
                .add(Restrictions.and(Restrictions.eq("e.id", id), Restrictions.eq("c.code", componentCode),
                        Restrictions.isNotNull("evid.document"), Restrictions.eq("q.procedure", true)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getProcedureAcceptedCount(String componentCode, Integer processId) {
        return (Long) currentSession().createCriteria(Guide.class)
                .createAlias("aspects", "as").createAlias("component", "c")
                .createAlias("process", "p").createAlias("as.questions", "q")
                .createAlias("q.answers", "answ").createAlias("answ.evidence", "evid")
                .add(Restrictions.and(Restrictions.eq("p.id", processId), Restrictions.eq("c.code", componentCode),
                        Restrictions.isNotNull("evid.document"), Restrictions.eq("q.procedure", true)))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public boolean deleteAspect(Integer id) {
        Aspect aspect = getAspect(id);
        if (aspect != null) {
            currentSession().delete(aspect);
            return true;
        }
        return false;
    }

    private DetachedCriteria getCriteria(Integer type) {
        DetachedCriteria detachedCriteria = null;
        switch (type) {
            case 1:
                detachedCriteria = DetachedCriteria.forClass(AffirmativeAnswer.class, "a_anws");
                break;
            case 2:
                detachedCriteria = DetachedCriteria.forClass(RejectAnswer.class, "r_anws");
                break;
            default:
                detachedCriteria = DetachedCriteria.forClass(NegativeAnswer.class, "n_anws");
        }
        return detachedCriteria;
    }
}
