package com.licenta.rapoarteimobiliare.service;

import com.licenta.rapoarteimobiliare.entities.AreaEntity;
import com.licenta.rapoarteimobiliare.repositories.AreaRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @PostConstruct
    public void init() {
        String csvFilePath = "static/content/Cartiere_Bucuresti.csv"; // Path to your CSV file
        try {
            List<AreaEntity> areas = readCSV(csvFilePath);
            calculateLevelOfArea(areas);
            saveAreas(areas);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private List<AreaEntity> readCSV(String filePath) throws IOException, CsvValidationException {
        List<AreaEntity> areas = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {
            String[] values;
            csvReader.readNext(); // Skip header
            while ((values = csvReader.readNext()) != null) {
                AreaEntity area = new AreaEntity();
                area.setAreaName(values[0]);
                area.setPricePerSquareMeter(Float.parseFloat(values[1]));
                area.setHasWaterIssues(Boolean.parseBoolean(values[2]));
                areas.add(area);
            }
        }
        return areas;
    }

    private void calculateLevelOfArea(List<AreaEntity> areas) {
        float minPrice = areas.stream().map(AreaEntity::getPricePerSquareMeter).min(Float::compare).orElse(0f);
        float maxPrice = areas.stream().map(AreaEntity::getPricePerSquareMeter).max(Float::compare).orElse(0f);
        float range = maxPrice - minPrice;
        float step = range / 5;

        for (AreaEntity area : areas) {
            float price = area.getPricePerSquareMeter();
            int levelOfArea = 1;
            if (price > minPrice + step * 4) {
                levelOfArea = 5;
            } else if (price > minPrice + step * 3) {
                levelOfArea = 4;
            } else if (price > minPrice + step * 2) {
                levelOfArea = 3;
            } else if (price > minPrice + step) {
                levelOfArea = 2;
            }
            area.setLevelofArea(levelOfArea);
        }
    }

    private void saveAreas(List<AreaEntity> areas) {
        for (AreaEntity area : areas) {
            if (!areaRepository.existsByAreaName(area.getAreaName())) {
                areaRepository.save(area);
            }
        }
    }
}
