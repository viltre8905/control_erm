package com.verynet.gcint.controllers.monitoring.committee.report;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.ActionControlInform;
import com.verynet.gcint.api.model.ControlAction;
import com.verynet.gcint.api.model.DocumentMetadata;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 01/10/2016.
 */
@Controller
@RequestMapping("/monitoring/committee/report")
public class CommitteeReportController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;

    @Autowired
    public CommitteeReportController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getActionControlInforms(ModelMap model, HttpSession session) {
        model.put("actionControlInformsMap", Context.getACInformService().getAllACIGroupByCAction(getEntityData(session).getId()));
        return "monitoring/committee/report/all";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getReportData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        ActionControlInform actionControlInform = Context.getACInformService().getActionControlInform(id);
        if (actionControlInform == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("title", actionControlInform.getTitle());
        result.put("ubication", actionControlInform.getUbication());
        result.put("conclution", actionControlInform.getConclution());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditReport(@RequestParam(value = "id", required = false) Integer id,
                           @RequestParam(value = "idControlAction", required = false) Integer idControlAction,
                           @RequestParam(value = "title") String title,
                           @RequestParam(value = "ubication") String ubication,
                           @RequestParam(value = "conclution") String conclution,
                           @RequestParam(value = "action") String action,
                           MultipartHttpServletRequest request, HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            ActionControlInform actionControlInform = null;
            if (action.equals("add")) {
                actionControlInform = new ActionControlInform();
            } else {
                actionControlInform = Context.getACInformService().getActionControlInform(id);
            }
            DocumentMetadata documentMetadata = controllerUtil.handleMultipartRequest(request);
            actionControlInform.setTitle(title);
            actionControlInform.setUbication(ubication);
            actionControlInform.setConclution(conclution);
            if (action.equals("edit")) {
                if (documentMetadata != null) {
                    if (actionControlInform.getDocumentMetadata() != null) {
                        String documentMetadataId = actionControlInform.getDocumentMetadata().getId();
                        actionControlInform.setDocumentMetadata(null);
                        actionControlInform = Context.getACInformService().saveActionControlInform(actionControlInform);
                        Context.getDocumentService().deleteDocumentMetadata(documentMetadataId);
                    }
                    actionControlInform.setDocumentMetadata(documentMetadata);
                }
            } else {
                actionControlInform.setDocumentMetadata(documentMetadata);
                actionControlInform.setEntity(Context.getEntityService().getEntityData(getEntityData(session).getId()));
            }
            actionControlInform = Context.getACInformService().saveActionControlInform(actionControlInform);
            if (action.equals("add")) {
                ControlAction controlAction = Context.getNomenclatureService().getControlAction(idControlAction);
                actionControlInform.setControlAction(controlAction);
                Context.getACInformService().saveActionControlInform(actionControlInform);
            }
            if (documentMetadata != null) {
                result.put("uuid", documentMetadata.getId());
            }
            result.put("id", actionControlInform.getId());
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing report of control committee: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteReport(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getACInformService().deleteActionControlInform(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting report of control committee(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download", produces = "multipart/form-data")
    public void getReportFile(@RequestParam(value = "id") String id, HttpServletResponse response) {
        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(id);
        try {
            controllerUtil.handleMultipartResponse(documentMetadata, response);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
