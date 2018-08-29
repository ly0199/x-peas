package com.lijq.xp.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lijq
 */
@RestController
public class PortalController {

    @GetMapping(value = {"/", "/index", "/portal", "/home"})
    public String portal() {
        return "Welcome!";
    }


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/user/list")
    public Object list() {
        return restTemplate.getForObject("http://xp-user-server" + "/user/list", Object.class);
    }
}
