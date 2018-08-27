package com.lijq.xp.portal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lijq
 */
@RestController
public class PortalController {

    @GetMapping(value = {"/", "/index", "/portal", "/home"})
    public String portal() {
        return "Welcome!";
    }
}
