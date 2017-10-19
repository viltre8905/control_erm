package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.ActionControlInform;

import java.util.List;

/**
 * Created by day on 01/10/2016.
 */
public interface ACInformDAO {

    public ActionControlInform saveActionControlInform(ActionControlInform actionControlInform);

    public ActionControlInform getActionControlInform(Integer id);

    public List<ActionControlInform> getAllActionControlInforms(Integer entityId);

    public boolean deleteActionControlInform(Integer id);

    public void deleteAllActionControlInform(Integer entityId);
}
