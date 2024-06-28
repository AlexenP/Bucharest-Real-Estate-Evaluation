package com.licenta.rapoarteimobiliare.controllers;

import com.licenta.rapoarteimobiliare.DTO.*;
import com.licenta.rapoarteimobiliare.entities.AreaEntity;
import com.licenta.rapoarteimobiliare.entities.EvaluationEntity;
import com.licenta.rapoarteimobiliare.entities.UserEntity;
import com.licenta.rapoarteimobiliare.repositories.AreaRepository;
import com.licenta.rapoarteimobiliare.repositories.EvaluationRepository;
import com.licenta.rapoarteimobiliare.repositories.UserRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ReportController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-evaluation")
    public String saveEvaluation(
            @RequestParam String areaName,
            Authentication authentication,
            HttpSession session) {

        UserEntity user = userRepository.findByUsername(authentication.getName());
        AreaEntity area = areaRepository.findByAreaName(areaName);

        // Retrieve preferences from the session
        PreferenceDTO preferenceDTO = (PreferenceDTO) session.getAttribute("preferenceDTO");
        if (preferenceDTO == null) {
            throw new IllegalStateException("Preferences not found in session.");
        }

        // Create and save the evaluation
        EvaluationEntity evaluation = new EvaluationEntity();
        String reportName = user.getUsername() + "_" + preferenceDTO.getTipImobil() + "_" + area.getAreaName() + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        evaluation.setName(reportName);
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
    public int getSessionEvaluationId(HttpSession session) {
        Integer evaluationId = (Integer) session.getAttribute("currentEvaluationId");
        if (evaluationId == null) {
            throw new IllegalStateException("No evaluation ID found in session.");
        }
        return evaluationId;
    }

    @GetMapping("/get-report")
    @ResponseBody
    public EvaluationReportDTO getReport(@RequestParam int reportId) {
        Optional<EvaluationEntity> evaluation = evaluationRepository.findById(reportId);
        if (!evaluation.isPresent()) {
            throw new IllegalStateException("No report found for the given ID.");
        }

        EvaluationEntity evaluationEntity = evaluation.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(evaluationEntity.getUser().getUserId());
        userDTO.setUsername(evaluationEntity.getUser().getUsername());

        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setAreaId(evaluationEntity.getArea().getAreaId());
        areaDTO.setAreaName(evaluationEntity.getArea().getAreaName());

        EvaluationReportDTO evaluationReportDTO = new EvaluationReportDTO();
        evaluationReportDTO.setName(evaluationEntity.getName());
        evaluationReportDTO.setDate(evaluationEntity.getDate());
        evaluationReportDTO.setUser(userDTO);
        evaluationReportDTO.setArea(areaDTO);
        evaluationReportDTO.setTipImobil(evaluationEntity.getTipImobil());
        evaluationReportDTO.setNumarCamere(evaluationEntity.getNumarCamere() != null ? evaluationEntity.getNumarCamere() : 2);
        evaluationReportDTO.setSuprafataMinima(evaluationEntity.getSuprafataMinima());
        evaluationReportDTO.setAnConstructie(evaluationEntity.getAnConstructie());
        evaluationReportDTO.setFacilitati(evaluationEntity.getFacilitati());

        // Calculating adjusted price per square meter
        double pretMediuPerMp = evaluationEntity.getArea().getPricePerSquareMeter();
        int numarFacilitati = evaluationEntity.getFacilitati().size();
        double ajustarePret = 0;

        if (numarFacilitati > 2) {
            ajustarePret = (numarFacilitati - 2) * 0.015 * pretMediuPerMp;
        } else if (numarFacilitati < 2) {
            ajustarePret = (2 - numarFacilitati) * -0.015 * pretMediuPerMp;
        }

        double pretAjustatPerMp = pretMediuPerMp + ajustarePret;
        evaluationReportDTO.setPretAjustatPerMp(pretAjustatPerMp);
        evaluationReportDTO.setPretMinimPerMp(pretAjustatPerMp * 0.92);
        evaluationReportDTO.setPretMaximPerMp(pretAjustatPerMp * 1.08);

        // Calculating property prices
        int suprafataEstimativa = 0;

        if ("Apartament".equalsIgnoreCase(evaluationReportDTO.getTipImobil())) {
            suprafataEstimativa = evaluationReportDTO.getNumarCamere() * 23; // Example: 23 mp per room
        } else if ("Casa".equalsIgnoreCase(evaluationReportDTO.getTipImobil())) {
            suprafataEstimativa = evaluationReportDTO.getNumarCamere() * 45; // Example: 45 mp per room
        }

        // Update suprafataEstimativa based on suprafataMinima
        if (evaluationReportDTO.getSuprafataMinima() != null && evaluationReportDTO.getSuprafataMinima() > suprafataEstimativa) {
            suprafataEstimativa = evaluationReportDTO.getSuprafataMinima();
        }

        double pretMinimProprietate = suprafataEstimativa * evaluationReportDTO.getPretMinimPerMp();
        double pretMediuProprietate = suprafataEstimativa * evaluationReportDTO.getPretAjustatPerMp();
        double pretMaximProprietate = suprafataEstimativa * evaluationReportDTO.getPretMaximPerMp();

        // Adjust property prices based on the age of the construction
        if (evaluationEntity.getAnConstructie() != null) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int age = currentYear - evaluationEntity.getAnConstructie();
            double ageAdjustment = 0;

            if (age < 10) {
                ageAdjustment = 0.20;
            } else if (age < 15) {
                ageAdjustment = 0.15;
            } else if (age < 20) {
                ageAdjustment = 0.10;
            }

            pretMinimProprietate *= (1 + ageAdjustment);
            pretMediuProprietate *= (1 + ageAdjustment);
            pretMaximProprietate *= (1 + ageAdjustment);
        }

        evaluationReportDTO.setPretMinimProprietate(pretMinimProprietate);
        evaluationReportDTO.setPretMediuProprietate(pretMediuProprietate);
        evaluationReportDTO.setPretMaximProprietate(pretMaximProprietate);

        // Getting property listings
        List<PropertyListingDTO> propertyListings = fetchPropertyListings(evaluationEntity.getArea().getAreaName());
        evaluationReportDTO.setPropertyListings(propertyListings);

        return evaluationReportDTO;
    }

    private List<PropertyListingDTO> fetchPropertyListings(String areaName) {
        List<PropertyListingDTO> listings = new ArrayList<>();
        String url = "https://www.olx.ro/imobiliare/apartamente-garsoniere-de-vanzare/q-" + areaName + "-Bucuresti/?currency=EUR";

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div[data-cy='l-card']"); // Updated selector
            System.out.println("Fetched elements: " + elements.size()); // Debugging statement

            for (Element element : elements) {
                String title = element.select("h6").text(); // Updated selector for title
                String price = element.select("p[data-testid='ad-price']").text(); // Updated selector for price
                String link = element.select("a").attr("href"); // Updated selector for link
                if (!link.startsWith("https")) {
                    link = "https://www.olx.ro" + link;
                }
                listings.add(new PropertyListingDTO(title, price, link));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listings;
    }



}
