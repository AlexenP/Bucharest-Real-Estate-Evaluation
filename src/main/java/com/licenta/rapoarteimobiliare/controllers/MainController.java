package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.entities.EvaluationEntity;
import com.licenta.rapoarteimobiliare.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @GetMapping("/view-reports")
    public String viewReportsPage(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<EvaluationEntity> evaluations = evaluationRepository.findByUserUsername(username);

        if (evaluations.isEmpty()) {
            model.addAttribute("noReports", true);
        } else {
            model.addAttribute("evaluations", evaluations);
            model.addAttribute("noReports", false);
        }

        return "view-reports";
    }

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

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
