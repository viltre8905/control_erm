package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.EntityData;

import java.util.List;

/**
 * Created by day on 22/08/2016.
 */
public interface EntityDAO {
    public EntityData saveEntity(EntityData entityData);

    public EntityData getEntityData(String name);

    public EntityData getEntityData(Integer id);

    public List<EntityData> getAllEntitiesData();

    public boolean deleteEntityData(Integer id);

    public List<EntityData>getAllEntitiesDataWithOutParent();
}
