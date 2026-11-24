package br.edu.atitus.geopoint.controllers;

import br.edu.atitus.geopoint.dtos.PointRequest;
import br.edu.atitus.geopoint.entities.PointEntity;
import br.edu.atitus.geopoint.services.PointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/points")
@CrossOrigin(origins = "*")
@RestController

public class PointController {
    private final PointService service;

    public PointController(PointService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<PointEntity> insertPoint(
            @RequestBody PointRequest pointRequest) {
        PointEntity point = service.insert(pointRequest);
        return ResponseEntity.status(201).body(point);

    }
    @GetMapping
    public ResponseEntity<List<PointEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointEntity> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointEntity> update(
            @PathVariable UUID id,
            @RequestBody PointRequest pointRequest) {

        return ResponseEntity.ok(service.update(id, pointRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
