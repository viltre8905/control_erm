package com.verynet.gcint.controllers.monitoring.committee.thematicplan;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.DiscussionDate;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 18/07/2017.
 */
@Controller
@RequestMapping(value = "/monitoring/committee/thematic-plan/themes")
public class DiscussionDateController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/discussionDate")
    public String getQuestionFromGuide(@RequestParam(value = "id") Integer id, ModelMap model, HttpSession session) {
        model.put("themeId", id);
        model.put("discussionDates", Context.getThemeService().getAllDiscussionDate(id));
        model.put("responsibles", Context.getUserService().getUsersWithRoleValues(getEntityData(session).getId(), Roles.ROLE_COMMITTEE_MEMBER.toString(), Roles.ROLE_SECRETARY_COMMITTEE.toString()));
        return "monitoring/committee/thematicplan/discussionDate";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getDiscussionDateData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        DiscussionDate discussionDate = Context.getThemeService().getDiscussionDate(id);
        if (discussionDate == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        result.put("responsible", discussionDate.getResponsible().getId());
        result.put("date", discussionDate.toShortDiscussionDate());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditDiscussionDate(@RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam(value = "themeId", required = false) Integer themeId,
                                   @RequestParam(value = "responsible") Integer responsible,
                                   @RequestParam(value = "date") String date,
                                   @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        DiscussionDate discussionDate = null;
        if (action.equals("add")) {
            discussionDate = new DiscussionDate();
            discussionDate.setTheme(Context.getThemeService().getTheme(themeId));
        } else {
            discussionDate = Context.getThemeService().getDiscussionDate(id);
        }
        discussionDate.setResponsible(Context.getUserService().getUser(responsible));
        discussionDate.setDiscussionDate(TimestampUtil.getDayFromSimpleText(date));
        discussionDate = Context.getThemeService().saveDiscussionDate(discussionDate);
        result.put("id", discussionDate.getId());
        result.put("responsible", String.format("%s %s", discussionDate.getResponsible().getFirstName(), discussionDate.getResponsible().getLastName()));
        result.put("date", discussionDate.toShortDiscussionDate());
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteDiscussionDate(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getThemeService().deleteDiscussionDate(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting theme(%s): %s", id, e.getMessage()));
            return false;
        }
    }
}
