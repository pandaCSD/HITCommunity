package org.hit.hitcommunitybackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @RequestMapping(value = "ask", method = RequestMethod.POST)
    public String ask() {
        return "ASK";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String getUid(String uname, String password) {
        return "";
    }
}
