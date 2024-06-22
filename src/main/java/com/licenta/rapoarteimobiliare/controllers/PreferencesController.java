package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.DTO.PreferenceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class PreferencesController {

    @PostMapping("/save-preferences")
    public String savePreferences(PreferenceDTO preferenceDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        session.setAttribute("preferenceDTO", preferenceDTO);
        return "redirect:/map";
    }
}
