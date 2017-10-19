package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.EntityDAO;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.api.services.EntityService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
@Transactional
public class EntityServiceImpl implements EntityService {
    private EntityDAO dao;

    @Override
    public void setEntityDAO(EntityDAO dao) {
        this.dao = dao;
    }

    @Override
    public EntityData saveEntity(EntityData entityData) {
        EntityData entityDataObject;
        EntityData anotherEntity = dao.getEntityData(entityData.getName());
        if (entityData.getId() != null) {
            entityDataObject = dao.getEntityData(entityData.getId());
            if (anotherEntity != null && !anotherEntity.getId().equals(entityData.getId())) {
                return null;
            }
            entityDataObject.setName(entityData.getName());
            entityDataObject.setPathLogo(entityData.getPathLogo());
            entityDataObject.setAddress(entityData.getAddress());
            entityDataObject.setWebAddress(entityData.getWebAddress());
            entityDataObject.setMission(entityData.getMission());
            entityDataObject.setEntities(entityData.getEntities());
            entityDataObject.setParent(entityData.getParent());
            entityDataObject.setVision(entityData.getVision());
        } else {
            if (anotherEntity != null) {
                return null;
            }
            entityDataObject = entityData;
        }
        return dao.saveEntity(entityDataObject);
    }

    @Override
    @Transactional(readOnly = true)
    public EntityData getEntityData(Integer id) {
        EntityData result = new EntityData();
        EntityData entityData = dao.getEntityData(id);
        result.simpleClone(entityData);
        for (int i = 0; i < entityData.getEntities().size(); i++) {
            EntityData entityData1 = new EntityData();
            entityData1.simpleClone(entityData.getEntities().get(i));
            result.getEntities().add(entityData1);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public EntityData getLightWeightEntityData(Integer id) {
        EntityData result = new EntityData();
        EntityData entityData = dao.getEntityData(id);
        result.simpleClone(entityData);
        for (int i = 0; i < entityData.getEntities().size(); i++) {
            EntityData entityData1 = new EntityData();
            entityData1.simpleClone(entityData.getEntities().get(i));
            result.getEntities().add(entityData1);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntityData> getAllEntitiesData() {
        return dao.getAllEntitiesData();
    }

    @Override
    public List<EntityData> getAllEntitiesDataWithOutParent() {
        List<EntityData> result = new ArrayList<>();
        List<EntityData> entities = dao.getAllEntitiesDataWithOutParent();
        for (int i = 0; i < entities.size(); i++) {
            EntityData entityData = new EntityData();
            entityData.simpleClone(entities.get(i));
            entityData.setEntities(entities.get(i).getEntities());
            initialized(entityData);
            result.add(entityData);
        }
        return result;
    }

    @Override
    public boolean deleteEntityData(Integer id) {
        return dao.deleteEntityData(id);
    }

    private void initialized(EntityData entityData) {
        if (entityData.getEntities() != null && entityData.getEntities().size() > 0) {
            Hibernate.initialize(entityData.getEntities());
            for (int i = 0; i < entityData.getEntities().size(); i++) {
                initialized(entityData.getEntities().get(i));
            }
        }
    }
}
