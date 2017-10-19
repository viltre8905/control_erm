package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.ReunionDAO;
import com.verynet.gcint.api.model.Reunion;
import com.verynet.gcint.api.services.ReunionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 03/10/2016.
 */
@Transactional
public class ReunionServiceImpl implements ReunionService {
    private ReunionDAO dao;

    @Override
    public void setReunionDAO(ReunionDAO dao) {
        this.dao = dao;
    }

    @Override
    public Reunion saveReunion(Reunion reunion) {
        return dao.saveReunion(reunion);
    }

    @Override
    @Transactional(readOnly = true)
    public Reunion getReunion(Integer id) {
        return dao.getReunion(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reunion> getAllReunions(Integer entityId) {
        List<Reunion> result = new ArrayList<>();
        List<Reunion> reunions = dao.getAllReunions(entityId);
        for (Reunion reunion : reunions) {
            Reunion newReunion = new Reunion();
            newReunion.simpleClone(reunion);
            result.add(newReunion);
        }
        return result;
    }

    @Override
    public boolean deleteReunion(Integer id) {
        return dao.deleteReunion(id);
    }
}
