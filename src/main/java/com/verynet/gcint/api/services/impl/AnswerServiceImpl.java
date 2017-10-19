package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.AnswerDAO;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.services.AnswerService;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 18/09/2016.
 */
@Transactional
public class AnswerServiceImpl implements AnswerService {
    private AnswerDAO dao;

    @Override
    public void setAnswerDAO(AnswerDAO dao) {
        this.dao = dao;
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        return dao.saveAnswer(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public Answer getAnswer(Integer id) {
        return dao.getAnswer(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Answer> getAllAnswers(Integer processId) {
        return dao.getAllAnswers(processId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NegativeAnswer> getAllNegativeAnswers(String componentCode, Integer processId) {
        List<NegativeAnswer> negativeAnswers = dao.getAllNegativeAnswers(componentCode, processId);
        for (NegativeAnswer negativeAnswer : negativeAnswers) {
            Hibernate.initialize(negativeAnswer.getDeficiency());
        }
        return negativeAnswers;
    }

    @Override
    public List<List<Map<String, List<NegativeAnswer>>>> getAllDeepNegativeAnswers(String componentCode, Integer processId) {
        List<List<Map<String, List<NegativeAnswer>>>> result = new ArrayList<>();
        List<Map<String, List<NegativeAnswer>>> negativeAnswerMapList = new ArrayList<>();
        List<NegativeAnswer> negativeAnswers = getAllNegativeAnswers(componentCode, processId);
        Map<String, List<NegativeAnswer>> map;
        if (negativeAnswers.size() > 0) {
            map = new HashMap<>();
            map.put(negativeAnswers.get(0).getProcess().getName(), negativeAnswers);
            negativeAnswerMapList.add(map);
        }
        List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
        map = new HashMap<>();
        for (GeneralProcess process : activityProcessList) {
            negativeAnswers = getAllNegativeAnswers(componentCode, process.getId());
            if (negativeAnswers.size() > 0) {
                map.put(negativeAnswers.get(0).getProcess().getName(), negativeAnswers);
            }
        }
        negativeAnswerMapList.add(map);
        result.add(negativeAnswerMapList);
        negativeAnswerMapList = new ArrayList<>();
        List<SubProcess> processList = Context.getProcessService().getAllSubProcesses(processId);
        activityProcessList = new ArrayList<>();
        map = new HashMap<>();
        for (GeneralProcess process : processList) {
            negativeAnswers = getAllNegativeAnswers(componentCode, process.getId());
            if (negativeAnswers.size() > 0) {
                map.put(negativeAnswers.get(0).getProcess().getName(), negativeAnswers);
            }
            activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(process.getId()));
        }
        negativeAnswerMapList.add(map);
        map = new HashMap<>();
        for (GeneralProcess process : activityProcessList) {
            negativeAnswers = getAllNegativeAnswers(componentCode, process.getId());
            if (negativeAnswers.size() > 0) {
                map.put(negativeAnswers.get(0).getProcess().getName(), negativeAnswers);
            }
        }
        negativeAnswerMapList.add(map);
        result.add(negativeAnswerMapList);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffirmativeAnswer> getAffirmativeAnswers(String componentCode, Integer processId) {
        return dao.getAffirmativeAnswers(componentCode, processId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffirmativeAnswer> getAffirmativeAnswers(Integer entityId, String componentCode) {
        return dao.getAffirmativeAnswers(entityId, componentCode);
    }

    @Override
    @Transactional(readOnly = true)
    public NegativeAnswer getNegativeAnswer(Integer activityId) {
        return dao.getNegativeAnswer(activityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AffirmativeAnswer> getAllAffirmativeAnswers() {
        return dao.getAllAffirmativeAnswers();
    }

    @Override
    public Activity addActivityToNegativeAnswer(Integer negativeAnswerId, Activity activity) {
        NegativeAnswer negativeAnswer = (NegativeAnswer) getAnswer(negativeAnswerId);
        activity = Context.getActivityService().getActivity(activity.getId());
        activity.setDeficiency(negativeAnswer.getDeficiency());
        Context.getActivityService().saveActivity(activity);
        return activity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> getActivities(Integer negativeAnswerId) {
        List<Activity> result = ((NegativeAnswer) getAnswer(negativeAnswerId)).getDeficiency().getActivities();
        Hibernate.initialize(result);
        return result;
    }

    @Override
    public boolean deleteAnswer(Integer id) {
        return dao.deleteAnswer(id);
    }

    @Override
    public void deleteAnswers(List<Answer> answers) {
        for (Answer answer : answers) {
            if (!(answer instanceof RejectAnswer)) {
                deleteAnswer(answer.getId());
            }
        }
    }

    @Override
    public void deleteAllAnswer(Integer entityId) {
        dao.deleteAllAnswer(entityId);
    }
}
