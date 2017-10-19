package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.AnswerDAO;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.AffirmativeAnswer;
import com.verynet.gcint.api.model.Answer;
import com.verynet.gcint.api.model.NegativeAnswer;

import java.util.List;
import java.util.Map;

/**
 * Created by day on 18/09/2016.
 */
public interface AnswerService {
    public void setAnswerDAO(AnswerDAO dao);

    public Answer saveAnswer(Answer answer);

    public Answer getAnswer(Integer id);

    public List<Answer> getAllAnswers(Integer processId);

    public List<NegativeAnswer> getAllNegativeAnswers(String componentCode, Integer processId);

    public List<List<Map<String, List<NegativeAnswer>>>> getAllDeepNegativeAnswers(String componentCode, Integer processId);

    public List<AffirmativeAnswer> getAffirmativeAnswers(String componentCode, Integer processId);

    public List<AffirmativeAnswer> getAffirmativeAnswers(Integer entityId, String componentCode);

    public NegativeAnswer getNegativeAnswer(Integer activityId);

    public List<AffirmativeAnswer> getAllAffirmativeAnswers();

    public Activity addActivityToNegativeAnswer(Integer negativeAnswerId, Activity activity);

    public List<Activity> getActivities(Integer negativeAnswerId);

    public boolean deleteAnswer(Integer id);

    public void deleteAnswers(List<Answer> answers);

    public void deleteAllAnswer(Integer entityId);
}
