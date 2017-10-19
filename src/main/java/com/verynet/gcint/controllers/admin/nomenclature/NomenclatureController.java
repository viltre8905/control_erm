package com.verynet.gcint.controllers.admin.nomenclature;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 22/08/2016.
 */
@Controller
@RequestMapping(value = "/admin/nomenclature")
public class NomenclatureController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getAllNomenclatures() {
        return "/admin/nomenclature/all";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditNomenclature(@RequestParam(value = "id", required = false) Integer id,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "dayCount", required = false) Integer dayCount,
                                 @RequestParam(value = "type") Integer type,
                                 @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        switch (type) {
            case 1:
                ControlAction ca;
                if (action.equals("add")) {
                    ca = new ControlAction();
                } else {
                    ca = Context.getNomenclatureService().getControlAction(id);
                }
                ca.setName(name);
                ca = Context.getNomenclatureService().saveControlAction(ca);
                if (ca == null) {
                    result.put("success", false);
                    result.put("message", "La acción de control ya existe");
                    return result;
                }
                result.put("id", ca.getId());
                break;
            case 2:
                DocumentProcedence dp;
                if (action.equals("add")) {
                    dp = new DocumentProcedence();
                } else {
                    dp = Context.getNomenclatureService().getDocumentProcedence(id);
                }
                dp.setName(name);
                dp = Context.getNomenclatureService().saveDocumentProcedence(dp);
                if (dp == null) {
                    result.put("success", false);
                    result.put("message", "La procedencia de documento ya existe");
                    return result;
                }
                result.put("id", dp.getId());
                break;
            case 3:
                DocumentType dt;
                if (action.equals("add")) {
                    dt = new DocumentType();
                } else {
                    dt = Context.getNomenclatureService().getDocumentType(id);
                }
                dt.setName(name);
                dt = Context.getNomenclatureService().saveDocumentType(dt);
                if (dt == null) {
                    result.put("success", false);
                    result.put("message", "El tipo de documento ya existe");
                    return result;
                }
                result.put("id", dt.getId());
                break;
            case 4:
                Ocupation ocupation;
                if (action.equals("add")) {
                    ocupation = new Ocupation();
                } else {
                    ocupation = Context.getNomenclatureService().getOcupation(id);
                }
                ocupation.setName(name);
                ocupation = Context.getNomenclatureService().saveOcupation(ocupation);
                if (ocupation == null) {
                    result.put("success", false);
                    result.put("message", "El cargo ya existe");
                    return result;
                }
                result.put("id", ocupation.getId());
                break;

        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteNomenclature(@RequestParam(value = "idNomenclature") Integer idNomenclature,
                               @RequestParam(value = "type") Integer type) {
        try {
            switch (type) {
                case 1:
                    return Context.getNomenclatureService().deleteControlAction(idNomenclature);
                case 2:
                    return Context.getNomenclatureService().deleteDocumentProcedence(idNomenclature);
                case 3:
                    return Context.getNomenclatureService().deleteDocumentType(idNomenclature);
                case 4:
                    return Context.getNomenclatureService().deleteOcupation(idNomenclature);
                default:
                    return Context.getNomenclatureService().deleteNefficacy(idNomenclature);
            }
        } catch (Exception e) {
            logger.warn(String.format("Error deleting nomenclature: %s", e.getMessage()));
            return false;
        }

    }


    @RequestMapping(method = RequestMethod.GET, value = "/efficacy/data")
    @ResponseBody
    public Map getEfficacy(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> result = new HashMap<>();
        Nefficacy nefficacy = Context.getNomenclatureService().getNefficacy(id);
        if (nefficacy == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("percent", nefficacy.getPercent());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/efficacy/edit")
    @ResponseBody
    public Map editEfficacy(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "percent", required = false) Double percent) {
        Map<String, Object> result = new HashMap<>();
        try {
            Nefficacy nefficacy = Context.getNomenclatureService().getNefficacy(id);
            nefficacy.setPercent(percent);
            Context.getNomenclatureService().saveNefficacy(nefficacy);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error editing efficacy nomenclature: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @ModelAttribute("controlActions")
    public List<ControlAction> getControlActions() {
        return Context.getNomenclatureService().getAllControlActions();
    }

    @ModelAttribute("documentProcedences")
    public List<DocumentProcedence> getDocumentProcedences() {
        return Context.getNomenclatureService().getAllDocumentProcedences();
    }

    @ModelAttribute("documentTypes")
    public List<DocumentType> getDocumentTypes() {
        return Context.getNomenclatureService().getAllDocumentTypes();
    }

    @ModelAttribute("ocupations")
    public List<Ocupation> getOcupations() {
        return Context.getNomenclatureService().getAllOcupations();
    }

    @ModelAttribute("nefficacies")
    public List<Nefficacy> getEfficacies() {
        return Context.getNomenclatureService().getAllNefficacies();
    }
}
