/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verynet.gcint.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author day
 */
@Controller
public class LoginController {
    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String getLogin() {
        return "login";
    }
}
