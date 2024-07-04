package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.PriceVehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PriceVehicule;
import org.reseaux.carLoc.services.PriceVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
@CrossOrigin("*")
public class PriceVehiculeController {

    private final PriceVehiculeService priceVehiculeService;

    @Autowired
    public PriceVehiculeController(PriceVehiculeService priceVehiculeService) {
        this.priceVehiculeService = priceVehiculeService;
    }

    @GetMapping
    public List<PriceVehicule> findAll() {
        return priceVehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceVehicule> findOne(@PathVariable long id) {
        Optional<PriceVehicule> priceVehicule = priceVehiculeService.findOne(id);
        return priceVehicule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PriceVehicule create(@RequestBody PriceVehiculeDTO priceVehiculeDTO) {
        return priceVehiculeService.create(priceVehiculeDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PriceVehicule> update(@PathVariable long id, @RequestBody PriceVehiculeDTO priceVehiculeDTO) {
        try {
            PriceVehicule updatedPriceVehicule = priceVehiculeService.update(id, priceVehiculeDTO);
            return ResponseEntity.ok(updatedPriceVehicule);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        priceVehiculeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
