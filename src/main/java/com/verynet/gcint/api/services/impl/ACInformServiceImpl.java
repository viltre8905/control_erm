package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.ACInformDAO;
import com.verynet.gcint.api.model.ActionControlInform;
import com.verynet.gcint.api.model.ControlAction;
import com.verynet.gcint.api.services.ACInformService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 01/10/2016.
 */
@Transactional
public class ACInformServiceImpl implements ACInformService {
    private ACInformDAO dao;

    @Override
    public void setACInformDAO(ACInformDAO dao) {
        this.dao = dao;
    }

    @Override
    public ActionControlInform saveActionControlInform(ActionControlInform actionControlInform) {
        return dao.saveActionControlInform(actionControlInform);
    }

    @Override
    @Transactional(readOnly = true)
    public ActionControlInform getActionControlInform(Integer id) {
        return dao.getActionControlInform(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ActionControlInform getHeavyActionControlInform(Integer id) {
        ActionControlInform actionControlInform = getActionControlInform(id);
        Hibernate.initialize(actionControlInform.getDeficiencies());
        return actionControlInform;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<ActionControlInform>> getAllACIGroupByCAction(Integer entityId) {
        Map<String, List<ActionControlInform>> result = new HashMap<>();
        List<ControlAction> controlActions = Context.getNomenclatureService().getAllControlActions();
        for (ControlAction controlAction : controlActions) {
            result.put(String.format("%s,,,%s", controlAction.getName(), controlAction.getId()), new ArrayList<>());
        }
        List<ActionControlInform> actionControlInformList = getAllActionControlInforms(entityId);
        for (ActionControlInform actionControlInfom : actionControlInformList) {
            List<ActionControlInform> actionControlInforms = result.get(String.format("%s,,,%s", actionControlInfom.getControlAction().getName()
                    , actionControlInfom.getControlAction().getId()));
            if (actionControlInforms != null) {
                actionControlInforms.add(actionControlInfom);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActionControlInform> getAllActionControlInforms(Integer entityId) {
        return dao.getAllActionControlInforms(entityId);
    }

    @Override
    public boolean deleteActionControlInform(Integer id) {
        return dao.deleteActionControlInform(id);
    }

    @Override
    public void deleteAllActionControlInform(Integer entityId) {
        dao.deleteAllActionControlInform(entityId);
    }
}
