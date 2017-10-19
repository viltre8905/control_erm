package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.EntityDAO;
import com.verynet.gcint.api.model.EntityData;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public class HibernateEntityDAO extends HibernateGeneralDAO implements EntityDAO {
    public HibernateEntityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public EntityData saveEntity(EntityData entityData) {
        currentSession().saveOrUpdate(entityData);
        return entityData;
    }

    @Override
    public EntityData getEntityData(String name) {
        List<EntityData> entityDataList = currentSession().createCriteria(EntityData.class).add(Restrictions.eq("name", name)).list();
        return entityDataList.size() > 0 ? entityDataList.get(0) : null;
    }

    @Override
    public EntityData getEntityData(Integer id) {
        return (EntityData) currentSession().get(EntityData.class, id);
    }

    @Override
    public List<EntityData> getAllEntitiesData() {
        return currentSession().createCriteria(EntityData.class).list();
    }

    @Override
    public List<EntityData> getAllEntitiesDataWithOutParent() {
        return currentSession().createCriteria(EntityData.class).add(Restrictions.isNull("parent")).list();
    }

    @Override
    public boolean deleteEntityData(Integer id) {
        EntityData entityData = getEntityData(id);
        if (entityData != null) {
            currentSession().delete(entityData);
            return true;
        }
        return false;
    }
}
