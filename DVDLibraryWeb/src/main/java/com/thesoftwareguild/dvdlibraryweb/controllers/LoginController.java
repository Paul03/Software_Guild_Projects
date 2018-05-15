package com.thesoftwareguild.dvdlibraryweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }

}
