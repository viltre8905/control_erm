package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.RiskDAO;
import com.verynet.gcint.api.model.Risk;
import com.verynet.gcint.api.services.RiskService;
import com.verynet.gcint.api.util.enums.RiskLevels;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 26/09/2016.
 */
@Transactional
public class RiskServiceImpl implements RiskService {
    private RiskDAO dao;

    @Override
    public void setRiskDAO(RiskDAO dao) {
        this.dao = dao;
    }

    @Override
    public Risk saveRisk(Risk risk) {
        return dao.saveRisk(risk);
    }

    @Override
    @Transactional(readOnly = true)
    public Risk getRisk(Integer id) {
        return dao.getRisk(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Risk> getAllRisks(Integer entityId) {
        return dao.getAllRisks(entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Risk> getAllRisksFromProcess(Integer processId) {
        return dao.getAllRisksFromProcess(processId);
    }

    @Override
    @Transactional(readOnly = true)
    public Map getLevelPercent(Integer processId, Integer entityId) {
        Map<String, Object> result = new HashMap<>();
        List<Risk> risks;
        if (processId != -1) {
            risks = getAllRisksFromProcess(processId);
        } else {
            risks = getAllRisks(entityId);
        }
        double low = 0, moderated = 0, high = 0, extreme = 0;
        double total = risks.size();
        for (Risk risk : risks) {
            if (risk.getLevel().equals(RiskLevels.Bajo.toString())) {
                low++;
            } else if (risk.getLevel().equals(RiskLevels.Moderado.toString())) {
                moderated++;
            } else if (risk.getLevel().equals(RiskLevels.Alto.toString())) {
                high++;
            } else {
                extreme++;
            }
        }
        if (total > 0) {
            result.put("low", (double) Math.round((low / total * 100) * 100) / 100);
            result.put("moderated", (double) Math.round((moderated / total * 100) * 100) / 100);
            result.put("high", (double) Math.round((high / total * 100) * 100) / 100);
            result.put("extreme", (double) Math.round((extreme / total * 100) * 100) / 100);
        }
        result.put("riskTotal", total);
        return result;
    }

    @Override
    public boolean deleteRisk(Integer id) {
        return dao.deleteRisk(id);
    }
}
