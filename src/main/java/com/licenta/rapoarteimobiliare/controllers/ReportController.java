package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.DTO.EvaluationDTO;
import com.licenta.rapoarteimobiliare.DTO.EvaluationReportDTO;
import com.licenta.rapoarteimobiliare.DTO.PreferenceDTO;
import com.licenta.rapoarteimobiliare.entities.AreaEntity;
import com.licenta.rapoarteimobiliare.entities.EvaluationEntity;
import com.licenta.rapoarteimobiliare.entities.UserEntity;
import com.licenta.rapoarteimobiliare.repositories.AreaRepository;
import com.licenta.rapoarteimobiliare.repositories.EvaluationRepository;
import com.licenta.rapoarteimobiliare.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-evaluation")
    public String saveEvaluation(EvaluationDTO evaluationDTO,
                                 Authentication authentication,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        UserEntity user = userRepository.findByUsername(authentication.getName());
        AreaEntity area = areaRepository.findByAreaName(evaluationDTO.getAreaName());

        // Retrieve preferences from the session
        PreferenceDTO preferenceDTO = (PreferenceDTO) session.getAttribute("preferenceDTO");

        // Create and save the evaluation
        EvaluationEntity evaluation = new EvaluationEntity();
        evaluation.setName("New Report");
        evaluation.setDate(new Date());
        evaluation.setUser(user);
        evaluation.setArea(area);
        evaluation.setTipImobil(preferenceDTO.getTipImobil());
        evaluation.setNumarCamere(preferenceDTO.getNumarCamere());
        evaluation.setSuprafataMinima(preferenceDTO.getSuprafataMinima());
        evaluation.setAnConstructie(preferenceDTO.getAnConstructie());
        evaluation.setFacilitati(preferenceDTO.getFacilitati());

        evaluationRepository.save(evaluation);

        // Store the evaluation ID in session
        session.setAttribute("currentEvaluationId", evaluation.getEvaluationId());

        return "redirect:/raport";
    }

    @GetMapping("/get-session-evaluation-id")
    @ResponseBody
    public Map<String, Integer> getSessionEvaluationId(HttpSession session) {
        Integer evaluationId = (Integer) session.getAttribute("currentEvaluationId");
        Map<String, Integer> response = new HashMap<>();
        response.put("evaluationId", evaluationId);
        return response;
    }

    @GetMapping("/get-report")
    @ResponseBody
    public EvaluationReportDTO getReport(@RequestParam Integer reportId) {
        EvaluationEntity evaluation = evaluationRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Convert EvaluationEntity to EvaluationReportDTO
        EvaluationReportDTO reportDTO = new EvaluationReportDTO();
        reportDTO.setName(evaluation.getName());
        reportDTO.setDate(evaluation.getDate());
        reportDTO.setUser(evaluation.getUser());
        reportDTO.setArea(evaluation.getArea());
        reportDTO.setTipImobil(evaluation.getTipImobil());
        reportDTO.setNumarCamere(evaluation.getNumarCamere());
        reportDTO.setSuprafataMinima(evaluation.getSuprafataMinima());
        reportDTO.setAnConstructie(evaluation.getAnConstructie());
        reportDTO.setFacilitati(evaluation.getFacilitati());

        // Debugging information
        System.out.println("Retrieved EvaluationReportDTO: " + reportDTO);

        return reportDTO;
    }




}
