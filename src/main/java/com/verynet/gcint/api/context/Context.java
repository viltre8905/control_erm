package com.verynet.gcint.api.context;

import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.services.*;
import com.verynet.gcint.api.thread.TaskThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by day on 28/07/2016.
 */
public class Context {
    private static final Logger logger = LoggerFactory.getLogger(Context.class);
    private static ServiceContext serviceContext;
    private static UserContext userContext;

    public Context() {
    }

    public static ServiceContext getServiceContext() {
        if (serviceContext == null) {
            logger.error("serviceContext is null.  Creating new ServiceContext()");
            serviceContext = ServiceContext.getInstance();
        }

        if (logger.isTraceEnabled()) {
            logger.trace("serviceContext: " + serviceContext);
        }
        return ServiceContext.getInstance();
    }

    public void setServiceContext(ServiceContext ctx) {
        setContext(ctx);
    }

    public static UserContext getUserContext() {
        if (userContext == null) {
            logger.error("userContext is null.  Creating new UserContext()");
            userContext = UserContext.getInstance();
        }
        if (logger.isTraceEnabled()) {
            logger.trace("userContext: " + userContext);
        }
        return UserContext.getInstance();
    }

    public void setUserContext(UserContext userContext) {
        setContext(userContext);
    }

    public static void setContext(UserContext ctx) {
        userContext = ctx;
    }

    public static void setContext(ServiceContext ctx) {
        serviceContext = ctx;
    }

    public static User getAuthenticatedUser() {
        return getUserContext().getAuthenticatedUser();
    }

    public static UserService getUserService() {
        return getServiceContext().getUserService();
    }

    public static RoleService getRoleService() {
        return getServiceContext().getRoleService();
    }

    public static EntityService getEntityService() {
        return getServiceContext().getEntityService();
    }

    public static StrategicObjectiveService getStrategicObjectiveService() {
        return getServiceContext().getStrategicObjectiveService();
    }

    public static NomenclatureService getNomenclatureService() {
        return getServiceContext().getNomenclatureService();
    }

    public static ProcessService getProcessService() {
        return getServiceContext().getProcessService();
    }

    public static ActivityProcessService getActivityProcessService() {
        return getServiceContext().getActivityProcessService();
    }

    public static GuideService getGuideService() {
        return getServiceContext().getGuideService();
    }

    public static QuestionService getQuestionService() {
        return getServiceContext().getQuestionService();
    }

    public static DocumentService getDocumentService() {
        return getServiceContext().getDocumentService();
    }

    public static ActivityService getActivityService() {
        return getServiceContext().getActivityService();
    }

    public static AnswerService getAnswerService() {
        return getServiceContext().getAnswerService();
    }

    public static RiskService getRiskService() {
        return getServiceContext().getRiskService();
    }

    public static ACInformService getACInformService() {
        return getServiceContext().getACInformService();
    }

    public static DeficiencyService getDeficiencyService() {
        return getServiceContext().getDeficiencyService();
    }

    public static ReunionService getReunionService() {
        return getServiceContext().getReunionService();
    }

    public static NotificationService getNotificationService() {
        return getServiceContext().getNotificationService();
    }

    public static TaskService getTaskService() {
        return getServiceContext().getTaskService();
    }

    public static ThemeService getThemeService() {
        return getServiceContext().getThemeService();
    }

    public static TaskThread getTaskThread() {
        return getServiceContext().getTaskThread();
    }

    public static UserAttemptsService getUserAttemptsService() {
        return getServiceContext().getUserAttemptsService();
    }


}
