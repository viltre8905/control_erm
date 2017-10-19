package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.GuideDAO;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.services.GuideService;
import com.verynet.gcint.api.util.enums.Components;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 09/09/2016.
 */
@Transactional
public class GuideServiceImpl implements GuideService {
    private GuideDAO dao;

    @Override
    public void setGuideDAO(GuideDAO dao) {
        this.dao = dao;
    }

    @Override
    public Guide saveGuide(Guide guide) {
        return dao.saveGuide(guide);
    }

    @Override
    @Transactional(readOnly = true)
    public Guide getGuide(Integer id) {
        return dao.getGuide(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Guide> getAllGuidesFromEntity(Integer entityId) {
        return dao.getAllGuidesFromEntity(entityId);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Guide> getAllHeavyGuides(String componentCode, Integer processId) {
        List<Guide> guides = dao.getAllGuides(componentCode, processId);
        for (Guide guide : guides) {
            Hibernate.initialize(guide.getAspects());
            for (Aspect aspect : guide.getAspects()) {
                Hibernate.initialize(aspect.getQuestions());
                for (Question question : aspect.getQuestions()) {
                    Hibernate.initialize(question.getAnswers());
                    for (Answer answer : question.getAnswers()) {
                        if (answer instanceof NegativeAnswer) {
                            Hibernate.initialize(((NegativeAnswer) answer).getDeficiency().getActivities());
                        }
                    }

                }
            }
        }
        return guides;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, List<Guide>>> getAllDeepHeavyGuides(String componentCode, Integer processId) {
        List<Map<String, List<Guide>>> result = new ArrayList<>();
        List<Guide> guides = getAllHeavyGuides(componentCode, processId);
        if (guides.size() > 0) {
            Map<String, List<Guide>> map = new HashMap<>();
            map.put(guides.get(0).getProcess().getName(), guides);
            result.add(map);
        }
        List<SubProcess> processList = Context.getProcessService().getAllSubProcesses(processId);
        List<ActivityProcess> activityProcessList = new ArrayList<>();
        Map<String, List<Guide>> map = new HashMap<>();
        for (GeneralProcess process : processList) {
            List<Guide> guides1 = getAllHeavyGuides(componentCode, process.getId());
            if (guides1.size() > 0) {
                map.put(guides1.get(0).getProcess().getName(), guides1);
            }
            activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(process.getId()));
        }
        result.add(map);
        map = new HashMap<>();
        activityProcessList.addAll(0, Context.getActivityProcessService().getAllActivityProcess(processId));
        for (GeneralProcess process : activityProcessList) {
            List<Guide> guides1 = getAllHeavyGuides(componentCode, process.getId());
            if (guides1.size() > 0) {
                map.put(guides1.get(0).getProcess().getName(), guides1);
            }
        }
        result.add(map);
        return result;
    }

    @Override
    public boolean deleteGuide(Integer id) {
        return dao.deleteGuide(id);
    }

    @Override
    public Aspect saveAspect(Aspect aspect) {
        return dao.saveAspect(aspect);
    }

    @Override
    public Aspect addAspect(Integer guideId, Aspect aspect) {
        Guide guide = getGuide(guideId);
        guide.getAspects().add(aspect);
        saveGuide(guide);
        return aspect;
    }

    @Override
    @Transactional(readOnly = true)
    public Aspect getAspect(Integer id) {
        return dao.getAspect(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Aspect> getAllAspects(Integer guideId) {
        List<Aspect> aspects = getGuide(guideId).getAspects();
        for (Aspect aspect : aspects) {
            Hibernate.initialize(aspect.getQuestions());
        }
        Hibernate.initialize(aspects);
        return aspects;
    }

    @Override
    public boolean deleteAspect(Integer id) {
        return dao.deleteAspect(id);
    }

    @Override
    public void saveAllAspects(Integer guideId, Integer guideToLoadId) {
        Guide target = getGuide(guideId);
        Guide guide = getGuide(guideToLoadId);
        for (Aspect aspect : guide.getAspects()) {
            Aspect newAspect = new Aspect();
            newAspect.setName(aspect.getName());
            newAspect.setNo(aspect.getNo());
            for (Question question : aspect.getQuestions()) {
                Question newQuestion = new Question();
                newQuestion.setTitle(question.getTitle());
                newQuestion.setProcedure(question.getProcedure());
                newQuestion.setStart(question.getStart());
                newQuestion.setDescription(question.getDescription());
                newQuestion.setCode(question.getCode());
                newQuestion = Context.getQuestionService().saveQuestion(newQuestion);
                newAspect.getQuestions().add(newQuestion);
            }
            newAspect = saveAspect(newAspect);
            target.getAspects().add(newAspect);
        }
        saveGuide(target);
    }

    @Override
    public Question addQuestion(Integer aspectId, Question question) {
        Aspect aspect = getAspect(aspectId);
        aspect.getQuestions().add(question);
        saveAspect(aspect);
        return question;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions(Integer aspectId) {
        Hibernate.initialize(getAspect(aspectId).getQuestions());
        return getAspect(aspectId).getQuestions();
    }

    @Override
    @Transactional(readOnly = true)
    public Map getAllDashboardData(Integer entityId, String componentCode, Integer processId) {
        double procedureCount = dao.getProcedureCount(entityId, componentCode);
        double procedureAcceptedCount = dao.getProcedureAcceptedCount(entityId, componentCode);
        double total = dao.getQuestionsCount(entityId, componentCode);
        double countA = dao.getAnswerCount(entityId, componentCode, 1);
        double countNP = dao.getAnswerCount(entityId, componentCode, 2);
        double countN = dao.getAnswerCount(entityId, componentCode, 3);
        double[] data = {procedureCount, procedureAcceptedCount, total, countA, countNP, countN};
        return getAllDashboardData(data);
    }


    @Override
    @Transactional(readOnly = true)
    public Map getAllDashboardData(String componentCode, Integer processId) {
        double procedureCount = dao.getProcedureCount(componentCode, processId);
        double procedureAcceptedCount = dao.getProcedureAcceptedCount(componentCode, processId);
        double total = dao.getQuestionsCount(componentCode, processId);
        double countA = dao.getAnswerCount(componentCode, processId, 1);
        double countNP = dao.getAnswerCount(componentCode, processId, 2);
        double countN = dao.getAnswerCount(componentCode, processId, 3);
        double[] data = {procedureCount, procedureAcceptedCount, total, countA, countNP, countN};
        return getAllDashboardData(data);
    }

    private Map getAllDashboardData(double[] data) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        double percent;
        double procedurePercent = 100;
        double procedureCount = data[0];
        double procedureAcceptedCount = data[1];
        double total = data[2];
        double countA = data[3];
        double countNP = data[4];
        double countN = data[5];


        double totalR = total - countNP;

        percent = countA / totalR * 100;
        Nefficacy nefficacy = Context.getNomenclatureService().getNefficacyByPercent(percent);
        if (nefficacy != null) {
            result.put("efficacy", nefficacy.getName());
        }
        result.put("countA", (int) countA + "");
        result.put("countNP", (int) countNP + "");
        result.put("countN", (int) countN + "");
        result.put("questionCount", (int) total + "");
        result.put("nAnswer", ((int) total - (int) countA - (int) countN - (int) countNP) + "");
        if (procedureCount > 0) {
            result.put("procedurePercent", (double) Math.round((procedureAcceptedCount / procedureCount * 100) * 100) / 100);
        } else {
            result.put("procedurePercent", procedurePercent);
        }
        return result;
    }

    @Override
    public Map getAllGeneralDashboardData(Integer entityId, Integer processId, Integer val) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        double percent;
        List<Guide> guides;
        double total = 0;
        double countA = 0;
        double countNP = 0;
        double countN = 0;
        switch (val) {
            case 1:
                if (processId != -1) {
                    total = dao.getQuestionsCount(processId);
                    countA = dao.getAnswerCount(processId, 1);
                    countNP = dao.getAnswerCount(processId, 2);
                    countN = dao.getAnswerCount(processId, 3);
                } else {
                    total = dao.getQuestionsCountFromEntity(entityId);
                    countA = dao.getAnswerCountFromEntity(entityId, 1);
                    countNP = dao.getAnswerCountFromEntity(entityId, 2);
                    countN = dao.getAnswerCountFromEntity(entityId, 3);
                }
                break;
            case 2:
                if (processId != -1) {
                    total = dao.getQuestionsCount(Components.ec.toString(), processId);
                    countA = dao.getAnswerCount(Components.ec.toString(), processId, 1);
                    countNP = dao.getAnswerCount(Components.ec.toString(), processId, 2);
                    countN = dao.getAnswerCount(Components.ec.toString(), processId, 3);
                } else {
                    total = dao.getQuestionsCount(entityId, Components.ec.toString());
                    countA = dao.getAnswerCount(entityId, Components.ec.toString(), 1);
                    countNP = dao.getAnswerCount(entityId, Components.ec.toString(), 2);
                    countN = dao.getAnswerCount(entityId, Components.ec.toString(), 3);
                }
                break;
            case 3:
                if (processId != -1) {
                    total = dao.getQuestionsCount(Components.er.toString(), processId);
                    countA = dao.getAnswerCount(Components.er.toString(), processId, 1);
                    countNP = dao.getAnswerCount(Components.er.toString(), processId, 2);
                    countN = dao.getAnswerCount(Components.er.toString(), processId, 3);
                } else {
                    total = dao.getQuestionsCount(entityId, Components.er.toString());
                    countA = dao.getAnswerCount(entityId, Components.er.toString(), 1);
                    countNP = dao.getAnswerCount(entityId, Components.er.toString(), 2);
                    countN = dao.getAnswerCount(entityId, Components.er.toString(), 3);
                }
                break;
            case 4:
                if (processId != -1) {
                    total = dao.getQuestionsCount(Components.ac.toString(), processId);
                    countA = dao.getAnswerCount(Components.ac.toString(), processId, 1);
                    countNP = dao.getAnswerCount(Components.ac.toString(), processId, 2);
                    countN = dao.getAnswerCount(Components.ac.toString(), processId, 3);
                } else {
                    total = dao.getQuestionsCount(entityId, Components.ac.toString());
                    countA = dao.getAnswerCount(entityId, Components.ac.toString(), 1);
                    countNP = dao.getAnswerCount(entityId, Components.ac.toString(), 2);
                    countN = dao.getAnswerCount(entityId, Components.ac.toString(), 3);
                }
                break;
            case 5:
                if (processId != -1) {
                    total = dao.getQuestionsCount(Components.ic.toString(), processId);
                    countA = dao.getAnswerCount(Components.ic.toString(), processId, 1);
                    countNP = dao.getAnswerCount(Components.ic.toString(), processId, 2);
                    countN = dao.getAnswerCount(Components.ic.toString(), processId, 3);
                } else {
                    total = dao.getQuestionsCount(entityId, Components.ic.toString());
                    countA = dao.getAnswerCount(entityId, Components.ic.toString(), 1);
                    countNP = dao.getAnswerCount(entityId, Components.ic.toString(), 2);
                    countN = dao.getAnswerCount(entityId, Components.ic.toString(), 3);
                }
                break;
            default:
                if (processId != -1) {
                    total = dao.getQuestionsCount(Components.sm.toString(), processId);
                    countA = dao.getAnswerCount(Components.sm.toString(), processId, 1);
                    countNP = dao.getAnswerCount(Components.sm.toString(), processId, 2);
                    countN = dao.getAnswerCount(Components.sm.toString(), processId, 3);
                } else {
                    total = dao.getQuestionsCount(entityId, Components.sm.toString());
                    countA = dao.getAnswerCount(entityId, Components.sm.toString(), 1);
                    countNP = dao.getAnswerCount(entityId, Components.sm.toString(), 2);
                    countN = dao.getAnswerCount(entityId, Components.sm.toString(), 3);
                }
        }
        double totalR = total - countNP;

        percent = countA / totalR * 100;
        Nefficacy nefficacy = Context.getNomenclatureService().getNefficacyByPercent(percent);
        if (nefficacy != null) {
            result.put("efficacy", nefficacy.getName());
            result.put("percent", (double) Math.round(percent * 100) / 100);
        }
        result.put("countA", (int) countA + "");
        result.put("countNP", (int) countNP + "");
        result.put("countN", (int) countN + "");
        result.put("nAnswer", ((int) total - (int) countA - (int) countN - (int) countNP) + "");
        result.put("questionCount", (int) total + "");
        return result;
    }

    @Override
    public Map getAllSupervisoryGeneralDashboardData(Integer entityId) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        double percent;
        double countA = dao.getAnswerCountFromEntity(entityId, 1);
        double countNP = dao.getAnswerCountFromEntity(entityId, 2);
        double total = dao.getQuestionsCountFromEntity(entityId);
        double totalR = total - countNP;
        percent = countA / totalR * 100;
        Nefficacy nefficacy = Context.getNomenclatureService().getNefficacyByPercent(percent);
        if (nefficacy != null) {
            result.put("efficacy", nefficacy.getName());
            result.put("percent", (double) Math.round(percent * 100) / 100);
        }

        return result;
    }
}
