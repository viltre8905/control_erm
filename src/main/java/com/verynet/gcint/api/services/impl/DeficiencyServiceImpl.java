package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.DeficiencyDAO;
import com.verynet.gcint.api.model.Deficiency;
import com.verynet.gcint.api.services.DeficiencyService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 02/10/2016.
 */
@Transactional
public class DeficiencyServiceImpl implements DeficiencyService {
    private DeficiencyDAO dao;

    @Override
    public void setDeficiencyDAO(DeficiencyDAO dao) {
        this.dao = dao;
    }

    @Override
    public Deficiency saveDeficiency(Deficiency deficiency) {
        return dao.saveDeficiency(deficiency);
    }

    @Override
    @Transactional(readOnly = true)
    public Deficiency getDeficiency(Integer id) {
        return dao.getDeficiency(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Deficiency> getAllDeficiencies() {
        return dao.getAllDeficiencies();
    }

    @Override
    public boolean deleteDeficiency(Integer id) {
        return dao.deleteDeficiency(id);
    }
}
