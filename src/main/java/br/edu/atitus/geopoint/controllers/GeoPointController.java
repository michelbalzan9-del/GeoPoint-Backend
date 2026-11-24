package br.edu.atitus.geopoint.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GeoPointController {

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok(
                "GeoPoint - API de pontos turísticos");
    }
}