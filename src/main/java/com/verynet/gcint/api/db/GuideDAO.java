package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Aspect;
import com.verynet.gcint.api.model.Guide;

import java.util.List;

/**
 * Created by day on 09/09/2016.
 */
public interface GuideDAO {
    public Guide saveGuide(Guide guide);

    public Guide getGuide(Integer id);

    public List<Guide> getAllGuidesFromEntity(Integer entityId);

    public List<Guide> getAllGuides(String componentCode, Integer processId);

    public boolean deleteGuide(Integer id);

    public Aspect saveAspect(Aspect aspect);

    public Aspect getAspect(Integer id);

    public Long getAnswerCountFromEntity(Integer id, Integer type);

    public Long getQuestionsCountFromEntity(Integer id);

    public Long getAnswerCount(Integer processId, Integer type);

    public Long getQuestionsCount(Integer processId);

    public Long getAnswerCount(Integer id, String componentCode, Integer type);

    public Long getQuestionsCount(Integer id, String componentCode);

    public Long getAnswerCount(String componentCode, Integer processId, Integer type);

    public Long getQuestionsCount(String componentCode, Integer processId);

    public Long getProcedureCount(Integer id, String componentCode);

    public Long getProcedureCount(String componentCode, Integer processId);

    public Long getProcedureAcceptedCount(Integer id, String componentCode);

    public Long getProcedureAcceptedCount(String componentCode, Integer processId);

    public boolean deleteAspect(Integer id);
}
