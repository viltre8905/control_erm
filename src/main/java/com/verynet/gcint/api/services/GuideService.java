package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.GuideDAO;
import com.verynet.gcint.api.model.Aspect;
import com.verynet.gcint.api.model.Guide;
import com.verynet.gcint.api.model.Question;

import java.util.List;
import java.util.Map;

/**
 * Created by day on 09/09/2016.
 */
public interface GuideService {
    public void setGuideDAO(GuideDAO dao);

    public Guide saveGuide(Guide guide);

    public Guide getGuide(Integer id);

    public List<Guide> getAllGuidesFromEntity(Integer entityId);

    public List<Guide> getAllHeavyGuides(String componentCode, Integer processId);

    public List<Map<String, List<Guide>>>  getAllDeepHeavyGuides(String componentCode, Integer processId);

    public boolean deleteGuide(Integer id);

    public Aspect saveAspect(Aspect aspect);

    public Aspect addAspect(Integer guideId, Aspect aspect);

    public Aspect getAspect(Integer id);

    public List<Aspect> getAllAspects(Integer guideId);

    public boolean deleteAspect(Integer id);

    public void saveAllAspects(Integer guideId, Integer guideToLoadId);

    public Question addQuestion(Integer aspectId, Question question);

    public List<Question> getAllQuestions(Integer aspectId);

    public Map getAllDashboardData(Integer entityId, String componentCode, Integer processId);

    public Map getAllDashboardData(String componentCode, Integer processId);

    public Map getAllGeneralDashboardData(Integer entityId, Integer processId, Integer val);

    public Map getAllSupervisoryGeneralDashboardData(Integer entityId);
}
