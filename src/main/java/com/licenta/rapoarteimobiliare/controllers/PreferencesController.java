package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.DTO.PreferenceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                                  HttpSession session) {
        PreferenceDTO preferenceDTO = new PreferenceDTO();
        preferenceDTO.setTipImobil(tipImobil);
        preferenceDTO.setNumarCamere(numarCamere);
        preferenceDTO.setSuprafataMinima(suprafataMinima);
        preferenceDTO.setAnConstructie(anConstructie);
        preferenceDTO.setFacilitati(facilitati);

        session.setAttribute("preferenceDTO", preferenceDTO);

        return "redirect:/map";
    }
}
