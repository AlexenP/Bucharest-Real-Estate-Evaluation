package com.licenta.rapoarteimobiliare.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.Set;

@Controller
public class PreferencesController {

    @PostMapping("/save-preferences")
    public String savePreferences(@RequestParam String tipImobil,
                                  @RequestParam(required = false) Integer numarCamere,
                                  @RequestParam(required = false) Integer suprafataMinima,
                                  @RequestParam(required = false) Integer anConstructie,
                                  @RequestParam(required = false) Set<String> facilitati,
                                  Authentication authentication,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        // Save preferences to session
        session.setAttribute("tipImobil", tipImobil);
        session.setAttribute("numarCamere", numarCamere);
        session.setAttribute("suprafataMinima", suprafataMinima);
        session.setAttribute("anConstructie", anConstructie);
        session.setAttribute("facilitati", facilitati);

        // Redirect to map.html
        return "redirect:/map";
    }
}
