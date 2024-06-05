package com.licenta.rapoarteimobiliare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // return the name of the HTML template
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // return the name of the login HTML template
    }
}
