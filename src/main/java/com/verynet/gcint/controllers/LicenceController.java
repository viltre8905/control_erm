package com.verynet.gcint.controllers;

import com.verynet.gcint.api.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 09/10/2016.
 */
@Controller
@RequestMapping(value = "/licence")
public class LicenceController {
    private ControllerUtil controllerUtil;

    @Autowired
    public LicenceController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getLicence() {
        return "licence";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public
    @ResponseBody
    Map uploadLicence(MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String message = controllerUtil.activate(request);
        result.put("success", true);
        if (message != null) {
            result.put("success", false);
            result.put("message", message);
        }
        return result;
    }
}
