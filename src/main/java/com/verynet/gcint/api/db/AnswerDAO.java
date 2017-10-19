package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.AffirmativeAnswer;
import com.verynet.gcint.api.model.Answer;
import com.verynet.gcint.api.model.NegativeAnswer;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public interface AnswerDAO {
    public Answer saveAnswer(Answer answer);

    public Answer getAnswer(Integer id);

    public List<Answer> getAllAnswers(Integer processId);

    public List<NegativeAnswer> getAllNegativeAnswers(String componentCode, Integer processId);

    public List<AffirmativeAnswer> getAffirmativeAnswers(String componentCode, Integer processId);

    public List<AffirmativeAnswer> getAffirmativeAnswers(Integer entityId, String componentCode);

    public NegativeAnswer getNegativeAnswer(Integer activityId);

    public List<AffirmativeAnswer> getAllAffirmativeAnswers();

    public boolean deleteAnswer(Integer id);

    public void deleteAllAnswer(Integer entityId);
}
