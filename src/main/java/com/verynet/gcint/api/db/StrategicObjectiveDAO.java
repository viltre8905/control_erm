package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.StrategicObjective;

import java.util.List;

/**
 * Created by day on 24/02/2017.
 */
public interface StrategicObjectiveDAO {
    public StrategicObjective saveStrategicObjective(StrategicObjective strategicObjective);

    public StrategicObjective getStrategicObjective(Integer id);

    public List<StrategicObjective> getAllStrategicObjective(Integer entityId);

    public boolean deleteStrategicObjective(Integer id);

}
