package com.licenta.rapoarteimobiliare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/preferinte")
    public String showPreferences() {
        return "preferinte";
    }



    @GetMapping("/map")
    public String mapPage() {
        return "map";
    }


    @GetMapping("/raport")
    public String raportPage() {
        return "raport";
    }

    @GetMapping("/view-reports")
    public String viewReportsPage() {
        // Logic to display the reports
        return "view-reports";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
