package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.StrategicObjectiveDAO;
import com.verynet.gcint.api.model.StrategicObjective;
import com.verynet.gcint.api.services.StrategicObjectiveService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 24/02/2017.
 */
@Transactional
public class StrategicObjectiveServiceImpl implements StrategicObjectiveService {
    private StrategicObjectiveDAO dao;

    @Override
    public void setStrategicObjectiveDAO(StrategicObjectiveDAO dao) {
        this.dao = dao;
    }

    @Override
    public StrategicObjective saveStrategicObjective(StrategicObjective strategicObjective) {
        return dao.saveStrategicObjective(strategicObjective);
    }

    @Override
    @Transactional(readOnly = true)
    public StrategicObjective getStrategicObjective(Integer id) {
        return dao.getStrategicObjective(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StrategicObjective> getAllStrategicObjective(Integer entityId) {
        return dao.getAllStrategicObjective(entityId);
    }

    @Override
    public boolean deleteStrategicObjective(Integer id) {
        return dao.deleteStrategicObjective(id);
    }
}
