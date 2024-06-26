package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.PriceChauffeurDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PriceChauffeur;
import org.reseaux.carLoc.services.PriceChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prices_Chauffeur")
@CrossOrigin("*")
public class PriceChauffeurController {
    @Autowired
    private PriceChauffeurService priceChauffeurService;

    @GetMapping
    public List<PriceChauffeur> findAll() {
        return priceChauffeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceChauffeur> findOne(@PathVariable long id) {
        Optional<PriceChauffeur> priceChauffeur = priceChauffeurService.findOne(id);
        return priceChauffeur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PriceChauffeur> create(@RequestBody PriceChauffeurDTO priceChauffeurDTO) {
        PriceChauffeur createdPriceChauffeur = priceChauffeurService.create(priceChauffeurDTO);
        return new ResponseEntity<>(createdPriceChauffeur, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PriceChauffeur> update(@PathVariable("id") long priceChauffeurId, @RequestBody PriceChauffeurDTO priceChauffeurDTO) {
        try {
            PriceChauffeur updatedPriceChauffeur = priceChauffeurService.update(priceChauffeurId, priceChauffeurDTO);
            return new ResponseEntity<>(updatedPriceChauffeur, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PriceChauffeur> delete(@PathVariable("id") long id) {
        try {
             priceChauffeurService.delete(id);
            return new ResponseEntity<>( HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
