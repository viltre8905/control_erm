package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.EntityDAO;
import com.verynet.gcint.api.model.EntityData;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public interface EntityService {

    public void setEntityDAO(EntityDAO dao);

    public EntityData saveEntity(EntityData entityData);

    public EntityData getEntityData(Integer id);

    public EntityData getLightWeightEntityData(Integer id);

    public List<EntityData> getAllEntitiesData();

    public List<EntityData>getAllEntitiesDataWithOutParent();

    public boolean deleteEntityData(Integer id);
}
