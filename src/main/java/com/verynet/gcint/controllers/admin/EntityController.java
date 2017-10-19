package com.verynet.gcint.controllers.admin;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.controllers.GeneralController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 31/08/2016.
 */
@Controller
@RequestMapping(value = "/admin/entity")
public class EntityController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/entities")
    public String getEntity(ModelMap map) {
        map.put("entities", Context.getEntityService().getAllEntitiesData());
        return "admin/entity/entities";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getEntityData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        EntityData entityData = Context.getEntityService().getLightWeightEntityData(id);
        if (entityData == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", entityData.getName());
        result.put("mission", entityData.getMission());
        result.put("vision", entityData.getVision());
        result.put("address", entityData.getAddress());
        result.put("webAddress", entityData.getWebAddress());
        result.put("pathPhoto", entityData.getPathLogo());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map saveEntity(@RequestParam(value = "id", required = false) Integer id,
                   @RequestParam(value = "entityName") String entityName,
                   @RequestParam(value = "address", required = false) String address,
                   @RequestParam(value = "webAddress", required = false) String webAddress,
                   @RequestParam(value = "entityLogo", required = false) String entityLogo,
                   @RequestParam(value = "vision", required = false) String vision,
                   @RequestParam(value = "mission", required = false) String mission,
                   @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        EntityData entityData;
        if (action.equals("add")) {
            entityData = new EntityData();
        } else {
            entityData = Context.getEntityService().getEntityData(id);
        }
        entityData.setName(entityName);
        entityData.setAddress(address);
        entityData.setWebAddress(webAddress);
        if (StringUtils.isNotBlank(entityLogo)) {
            entityData.setPathLogo(entityLogo);
        } else {
            entityData.setPathLogo(null);
        }
        entityData.setVision(vision);
        entityData.setMission(mission);
        try {
            entityData = Context.getEntityService().saveEntity(entityData);
            if (entityData == null) {
                result.put("success", false);
                result.put("message", "La Entidad ya existe");
                return result;
            }

        } catch (Exception e) {
            logger.error(String.format("Error creating or editing entity: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("id", entityData.getId());
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteEntityData(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getEntityService().deleteEntityData(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deletingentity: %s", e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/structure")
    public String getEntityStructure(ModelMap map) {
        map.put("entitiesWithOutParent", Context.getEntityService().getAllEntitiesDataWithOutParent());
        return "admin/entity/structure";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/setParent")
    public
    @ResponseBody
    Map setParent(@RequestParam(value = "id") Integer id,
                  @RequestParam(value = "parentId", required = false) Integer parentId) {
        Map<String, Object> result = new HashMap<>();
        EntityData entityData = Context.getEntityService().getEntityData(id);
        EntityData parent = null;
        if (parentId != null) {
            parent = Context.getEntityService().getEntityData(parentId);
        }
        entityData.setParent(parent);
        entityData = Context.getEntityService().saveEntity(entityData);
        if (entityData == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

}
