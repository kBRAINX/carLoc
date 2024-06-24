package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.LocationDTO;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return ResponseEntity.ok(locationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findOne(@PathVariable long id) {
        return ResponseEntity.ok(locationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationDTO locationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.create(locationDTO));
    }
}
