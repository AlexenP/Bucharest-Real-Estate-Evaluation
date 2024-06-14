package com.licenta.rapoarteimobiliare.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {
    @GetMapping("/content/map.geojson")
    public Resource getMapGeoJson() {
        return new ClassPathResource("static/content/map.geojson");
    }
}
