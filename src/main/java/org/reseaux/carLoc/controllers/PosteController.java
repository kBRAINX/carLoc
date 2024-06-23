package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.PosteDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.services.PosteService;
import org.reseaux.carLoc.services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postes")
public class PosteController {

    @Autowired
    private PosteService posteService;

    @Autowired
    private VehiculeService vehiculeService;

    @GetMapping
    public ResponseEntity<List<Poste>> findAll() {
        List<Poste> postes = posteService.findAll();
        return new ResponseEntity<>(postes, HttpStatus.OK);
    }

    @GetMapping("/{posteId}/vehicules")
    public ResponseEntity<List<Vehicule>> findByPosteId(@PathVariable("posteId") long posteId) {
        List<Vehicule> vehicules = vehiculeService.findByPosteId(posteId);
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poste> findOne(@PathVariable("id") long posteId) {
        Optional<Poste> poste = posteService.findOne(posteId);
        return poste.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Poste> create(@RequestBody PosteDTO posteDTO) {
        Poste createdPoste = posteService.create(posteDTO);
        return new ResponseEntity<>(createdPoste, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Poste> update(@PathVariable("id") long posteId, @RequestBody PosteDTO posteDTO) {
        try {
            Poste updatedPoste = posteService.update(posteId, posteDTO);
            return new ResponseEntity<>(updatedPoste, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long posteId) {
        try {
            posteService.delete(posteId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
