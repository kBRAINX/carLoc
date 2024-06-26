package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.SyndicatDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Chauffeur;
import org.reseaux.carLoc.models.Syndicat;
import org.reseaux.carLoc.services.ChauffeurService;
import org.reseaux.carLoc.services.SyndicatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/syndicats")
@CrossOrigin("*")
public class SyndicatController {

    @Autowired
    private SyndicatService syndicatService;
    @Autowired
    private ChauffeurService chauffeurService;

    @GetMapping
    public List<Syndicat> findAll() {
        return syndicatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Syndicat> findOne(@PathVariable long id) {
        Optional<Syndicat> syndicat = syndicatService.findOne(id);
        return syndicat.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/chauffeurs")
    public ResponseEntity<List<Chauffeur>> getChauffeur(@PathVariable("id") long syndicatId){
        List<Chauffeur> chauffeurs = chauffeurService.findBySyndicatId(syndicatId);
        return new ResponseEntity<>(chauffeurs, HttpStatus.OK);
    }

    @PostMapping
    public Syndicat create(@RequestBody SyndicatDTO syndicatDTO) {
        return syndicatService.create(syndicatDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Syndicat> update(@PathVariable long id, @RequestBody SyndicatDTO syndicatDTO) {
        try {
            Syndicat updatedSyndicat = syndicatService.update(id, syndicatDTO);
            return ResponseEntity.ok(updatedSyndicat);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        syndicatService.delete(id);
        return ResponseEntity.ok().build();
    }
}
