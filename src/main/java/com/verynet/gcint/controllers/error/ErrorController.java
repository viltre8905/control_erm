package com.verynet.gcint.controllers.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by day on 05/10/2016.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public String getProcessError() {
        return "error/process";
    }
}
