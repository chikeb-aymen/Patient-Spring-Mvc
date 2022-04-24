package com.achcode.patientmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/403")
    public String pageNotAuthorized(){
        return "error/403";
    }
}
