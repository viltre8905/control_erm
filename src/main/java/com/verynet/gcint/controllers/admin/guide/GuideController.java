package com.verynet.gcint.controllers.admin.guide;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Component;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Guide;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 09/09/2016.
 */
@Controller
@RequestMapping(value = "/admin/guide")
public class GuideController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/guides")
    public String getAllGuides() {
        return "admin/guide/guides";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getGuideData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Guide guide = Context.getGuideService().getGuide(id);
        if (guide == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", guide.getName());
        result.put("component", guide.getComponent().getId());
        result.put("process", guide.getProcess().getId());
        result.put("description", guide.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditGuide(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "component") Integer componentId,
                          @RequestParam(value = "process") Integer processId,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Guide guide = null;
        if (action.equals("add")) {
            guide = new Guide();
        } else {
            guide = Context.getGuideService().getGuide(id);
        }
        guide.setName(name);
        guide.setComponent(Context.getNomenclatureService().getComponent(componentId));
        GeneralProcess generalProcess = Context.getProcessService().getGeneralProcess(processId);
        guide.setProcess(generalProcess);
        guide.setDescription(description);
        guide = Context.getGuideService().saveGuide(guide);
        result.put("id", guide.getId());
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteGuide(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getGuideService().deleteGuide(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting guide: %s", e.getMessage()));
            return false;
        }
    }

    @ModelAttribute(value = "guides")
    public List<Guide> getGuides() {
        User loggedUser = getUserLogged();
        if (loggedUser != null) {
            return Context.getGuideService().getAllGuidesFromEntity(loggedUser.getEntity().getId());
        }
        return null;
    }

    @ModelAttribute(value = "components")
    public List<Component> getComponents() {
        return Context.getNomenclatureService().getAllComponents();
    }

    @ModelAttribute(value = "processes")
    public List<GeneralProcess> getProcesses() {
        User loggedUser = getUserLogged();
        List<GeneralProcess> result = new ArrayList<>();
        if (loggedUser != null) {
            result = Context.getProcessService().getAllGeneralProcess(loggedUser.getEntity().getId());
        }
        return result;
    }

}
