package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.ACInformDAO;
import com.verynet.gcint.api.model.ActionControlInform;

import java.util.List;
import java.util.Map;

/**
 * Created by day on 01/10/2016.
 */
public interface ACInformService {
    public void setACInformDAO(ACInformDAO dao);

    public ActionControlInform saveActionControlInform(ActionControlInform actionControlInform);

    public ActionControlInform getActionControlInform(Integer id);

    public ActionControlInform getHeavyActionControlInform(Integer id);

    public Map<String,List<ActionControlInform>> getAllACIGroupByCAction(Integer entityId);

    public List<ActionControlInform> getAllActionControlInforms(Integer entityId);

    public boolean deleteActionControlInform(Integer id);

    public void deleteAllActionControlInform(Integer entityId);
}
