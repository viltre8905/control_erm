package com.verynet.gcint.controllers.monitoring.committee.reunion;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.DocumentMetadata;
import com.verynet.gcint.api.model.Reunion;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 03/10/2016.
 */
@Controller
@RequestMapping("/monitoring/committee/reunion")
public class CommitteeReunionController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;

    @Autowired
    public CommitteeReunionController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/calendar")
    public String getCalendar() {
        return "monitoring/committee/reunion/calendar";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getAllReunionData(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        List<Reunion> reunion = Context.getReunionService().getAllReunions(getEntityData(session).getId());
        if (reunion == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("reunions", reunion.toArray());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditReunion(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "place") String place,
                            @RequestParam(value = "start") String start,
                            @RequestParam(value = "end") String end,
                            @RequestParam(value = "ubication", required = false) String ubication,
                            HttpSession session,
                            MultipartHttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Reunion reunion;
            if (id == null) {
                reunion = new Reunion();
            } else {
                reunion = Context.getReunionService().getReunion(id);
            }
            reunion.setTitle(title);
            reunion.setPlace(place);
            reunion.setStart(TimestampUtil.getDateFromTimeStamp(start));
            reunion.setEnd(TimestampUtil.getDateFromTimeStamp(end));
            reunion.setMinuteUbication(ubication);
            DocumentMetadata documentMetadata = controllerUtil.handleMultipartRequest(request);
            if (id != null) {
                if (documentMetadata != null) {
                    if (reunion.getDocumentMetadata() != null) {
                        String documentMetadataId = reunion.getDocumentMetadata().getId();
                        reunion.setDocumentMetadata(null);
                        reunion = Context.getReunionService().saveReunion(reunion);
                        Context.getDocumentService().deleteDocumentMetadata(documentMetadataId);
                    }
                    reunion.setDocumentMetadata(documentMetadata);
                }
            } else {
                reunion.setEntity(Context.getEntityService().getEntityData(getEntityData(session).getId()));
                reunion.setDocumentMetadata(documentMetadata);
            }
            Context.getReunionService().saveReunion(reunion);
            result.put("id", reunion.getId());
            if (documentMetadata != null) {
                result.put("uuid", documentMetadata.getId());
            }
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing control committee reunion: %s", e.getMessage()));
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
    boolean deleteReunion(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getReunionService().deleteReunion(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting control committee reunion(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download", produces = "multipart/form-data")
    public void getReunionMinute(@RequestParam(value = "id") String id, HttpServletResponse response) {
        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(id);
        try {
            controllerUtil.handleMultipartResponse(documentMetadata, response);
        } catch (IOException io) {
            logger.warn(String.format("Error downloading control committee reunion minute(%s): %s", id, io.getMessage()));
            io.printStackTrace();
        }
    }


}
