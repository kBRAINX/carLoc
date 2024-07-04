package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.LocationDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
@CrossOrigin("*")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findOne(@PathVariable long id) {
        Optional<Location> location = locationService.findOne(id);
        return location.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> create(@RequestBody LocationDTO locationDTO) {
        Location createdLocation = locationService.create(locationDTO);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Location> update(@PathVariable("id") long locationId, @RequestBody LocationDTO locationDTO) {
        try {
            Location updatedLocation = locationService.update(locationId, locationDTO);
            return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
