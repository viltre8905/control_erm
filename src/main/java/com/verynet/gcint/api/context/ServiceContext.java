package com.verynet.gcint.api.context;

import com.verynet.gcint.api.aop.LoggingAdvice;
import com.verynet.gcint.api.services.*;
import com.verynet.gcint.api.thread.SecundaryFunctions;
import com.verynet.gcint.api.thread.TaskThread;
import com.verynet.gcint.api.util.GeneralUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 28/07/2016.
 */
public class ServiceContext {
    private static final Logger logger = LoggerFactory.getLogger(ServiceContext.class);
    private static ServiceContext instance;
    private Map<Class, Object> services = new HashMap<Class, Object>();
    private SecundaryFunctions secundaryFunctions;
    private TaskThread taskThread;

    private ServiceContext() {
        logger.debug("Instantiating service context");
    }


    public static ServiceContext getInstance() {
        if (instance == null) {
            instance = new ServiceContext();
        }
        return instance;
    }

    public void setService(Class cls, Object classInstance) {
        //wrapping services with AOP proxy
        Advised advisedService;
        Class[] interfaces = {cls};
        ProxyFactory factory = new ProxyFactory(interfaces);
        factory.setTarget(classInstance);
        factory.addAdvice(new LoggingAdvice());
        advisedService = (Advised) factory.getProxy();
        //adding service
        services.put(cls, advisedService);
    }

    public <T extends Object> T getService(Class<? extends T> cls) {
        Object service = services.get(cls);
        return (T) service;
    }

    public static void destroyInstance() {
        if (instance != null && instance.services != null) {
            if (logger.isDebugEnabled()) {
                for (Map.Entry<Class, Object> entry : instance.services.entrySet()) {
                    logger.debug("Service - " + entry.getKey().getName() + ":" + entry.getValue());
                }
            }
            if (instance.services != null) {
                instance.services.clear();
                instance.services = null;
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Destroying ServiceContext instance: " + instance);
        }
        instance = null;
    }

    public UserService getUserService() {
        return getService(UserService.class);
    }

    public void setUserService(UserService userService) {
        setService(UserService.class, userService);
    }

    public void setSecundaryFunctions(SecundaryFunctions secundaryFunctions) {
        if (this.secundaryFunctions == null) {
            this.secundaryFunctions = secundaryFunctions;
            this.secundaryFunctions.start();
        }
    }

    public TaskThread getTaskThread() {
        return taskThread;
    }

    public void setTaskThread(TaskThread taskThread) {
        this.taskThread = taskThread;
    }

    public RoleService getRoleService() {
        return getService(RoleService.class);
    }

    public void setRoleService(RoleService roleService) {
        setService(RoleService.class, roleService);
    }

    public EntityService getEntityService() {
        return getService(EntityService.class);
    }

    public void setEntityService(EntityService entityService) {
        setService(EntityService.class, entityService);
    }

    public StrategicObjectiveService getStrategicObjectiveService() {
        return getService(StrategicObjectiveService.class);
    }

    public void setStrategicObjectiveService(StrategicObjectiveService strategicObjectiveService) {
        setService(StrategicObjectiveService.class, strategicObjectiveService);
    }

    public NomenclatureService getNomenclatureService() {
        return getService(NomenclatureService.class);
    }

    public void setNomenclatureService(NomenclatureService nomenclatureService) {
        setService(NomenclatureService.class, nomenclatureService);
    }

    public ProcessService getProcessService() {
        return getService(ProcessService.class);
    }

    public void setProcessService(ProcessService processService) {
        setService(ProcessService.class, processService);
    }

    public ActivityProcessService getActivityProcessService() {
        return getService(ActivityProcessService.class);
    }

    public void setActivityProcessService(ActivityProcessService activityProcessService) {
        setService(ActivityProcessService.class, activityProcessService);
    }

    public GuideService getGuideService() {
        return getService(GuideService.class);
    }

    public void setGuideService(GuideService guideService) {
        setService(GuideService.class, guideService);
    }

    public QuestionService getQuestionService() {
        return getService(QuestionService.class);
    }

    public void setQuestionService(QuestionService questionService) {
        setService(QuestionService.class, questionService);
    }

    public DocumentService getDocumentService() {
        return getService(DocumentService.class);
    }

    public void setDocumentService(DocumentService documentService) {
        setService(DocumentService.class, documentService);
    }

    public ActivityService getActivityService() {
        return getService(ActivityService.class);
    }

    public void setActivityService(ActivityService activityService) {
        setService(ActivityService.class, activityService);
    }

    public AnswerService getAnswerService() {
        return getService(AnswerService.class);
    }

    public void setAnswerService(AnswerService answerService) {
        setService(AnswerService.class, answerService);
    }

    public RiskService getRiskService() {
        return getService(RiskService.class);
    }

    public void setRiskService(RiskService riskService) {
        setService(RiskService.class, riskService);
    }

    public ACInformService getACInformService() {
        return getService(ACInformService.class);
    }

    public void setACInformService(ACInformService acInformService) {
        setService(ACInformService.class, acInformService);
    }

    public DeficiencyService getDeficiencyService() {
        return getService(DeficiencyService.class);
    }

    public void setDeficiencyService(DeficiencyService deficiencyService) {
        setService(DeficiencyService.class, deficiencyService);
    }

    public ReunionService getReunionService() {
        return getService(ReunionService.class);
    }

    public void setReunionService(ReunionService reunionService) {
        setService(ReunionService.class, reunionService);
    }

    public NotificationService getNotificationService() {
        return getService(NotificationService.class);
    }

    public void setNotificationService(NotificationService notificationService) {
        setService(NotificationService.class, notificationService);
    }

    public TaskService getTaskService() {
        return getService(TaskService.class);
    }

    public void setTaskService(TaskService taskService) {
        setService(TaskService.class, taskService);
    }

    public ThemeService getThemeService() {
        return getService(ThemeService.class);
    }

    public void setThemeService(ThemeService themeService) {
        setService(ThemeService.class, themeService);
    }

    public UserAttemptsService getUserAttemptsService() {
        return getService(UserAttemptsService.class);
    }

    public void setUserAttemptsService(UserAttemptsService userAttemptsService) {
        setService(UserAttemptsService.class, userAttemptsService);
    }


}
