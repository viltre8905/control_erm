package com.verynet.gcint.api.db.hibernate;


import com.verynet.gcint.api.db.RiskDAO;
import com.verynet.gcint.api.model.Risk;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 26/09/2016.
 */
public class HibernateRiskDAO extends HibernateGeneralDAO implements RiskDAO {
    public HibernateRiskDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Risk saveRisk(Risk risk) {
        currentSession().saveOrUpdate(risk);
        return risk;
    }

    @Override
    public Risk getRisk(Integer id) {
        return (Risk) currentSession().get(Risk.class, id);
    }

    @Override
    public List<Risk> getAllRisks(Integer entityId) {
        return currentSession().createCriteria(Risk.class)
                .createAlias("objective", "o").createAlias("o.process", "p").
                        createAlias("p.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<Risk> getAllRisksFromProcess(Integer processId) {
        return currentSession().createCriteria(Risk.class).createAlias("objective", "o")
                .createAlias("o.process", "p").add(Restrictions.eq("p.id", processId)).list();
    }

    @Override
    public boolean deleteRisk(Integer id) {
        Risk risk = getRisk(id);
        if (risk != null) {
            currentSession().delete(risk);
            return true;
        }
        return false;
    }
}
