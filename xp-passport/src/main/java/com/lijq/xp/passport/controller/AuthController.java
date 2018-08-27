package com.lijq.xp.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lijq
 */
@Controller
public class AuthController {

    @GetMapping(value = {"/", "/login"})
    public String login(HttpServletRequest request, ModelMap modelMap) {



        return "login";
    }

}
