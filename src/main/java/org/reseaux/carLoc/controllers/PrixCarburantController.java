package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.PrixCarburantDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PrixCarburant;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.services.PrixCarburantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prix_carburant")
@CrossOrigin("*")
public class PrixCarburantController {
    @Autowired
    private PrixCarburantService prixCarburantService;

    @GetMapping
    public List<PrixCarburant> findAll() {
        return prixCarburantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrixCarburant> findOne(@PathVariable long id) {
        Optional<PrixCarburant> prixCarburant = prixCarburantService.findOne(id);
        return prixCarburant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{carburant}")
    public ResponseEntity<PrixCarburant> findByCarburant(@PathVariable Carburant carburant) {
        Optional<PrixCarburant> prixCarburant = prixCarburantService.findByCarburant(carburant);
        return prixCarburant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrixCarburant> create(@RequestBody PrixCarburantDTO prixCarburantDTO) {
        PrixCarburant createdPrixCarburant = prixCarburantService.create(prixCarburantDTO);
        return new ResponseEntity<>(createdPrixCarburant, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrixCarburant> update(@PathVariable long id, @RequestBody PrixCarburantDTO prixCarburantDTO) {
        try {
            PrixCarburant updatedPrixCarburant = prixCarburantService.update(id, prixCarburantDTO);
            return new ResponseEntity<>(updatedPrixCarburant, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        prixCarburantService.delete(id);
        return ResponseEntity.ok().build();
    }
}
