package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.RiskDAO;
import com.verynet.gcint.api.model.Risk;

import java.util.List;
import java.util.Map;

/**
 * Created by day on 26/09/2016.
 */
public interface RiskService {

    public void setRiskDAO(RiskDAO dao);

    public Risk saveRisk(Risk risk);

    public Risk getRisk(Integer id);

    public List<Risk> getAllRisks(Integer entityId);

    public List<Risk> getAllRisksFromProcess(Integer processId);

    public Map getLevelPercent(Integer processId, Integer entityId);

    public boolean deleteRisk(Integer id);
}
