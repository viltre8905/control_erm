package com.verynet.gcint.api.db;


import com.verynet.gcint.api.model.Risk;

import java.util.List;

/**
 * Created by day on 26/09/2016.
 */
public interface RiskDAO {
    public Risk saveRisk(Risk risk);

    public Risk getRisk(Integer id);

    public List<Risk> getAllRisks(Integer entityId);

    public List<Risk> getAllRisksFromProcess(Integer processId);

    public boolean deleteRisk(Integer id);
}
