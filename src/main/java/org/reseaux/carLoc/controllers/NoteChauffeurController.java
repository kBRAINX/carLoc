package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.NoteChauffeurDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.NoteChauffeur;
import org.reseaux.carLoc.services.NoteChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noteChauffeurs")
@CrossOrigin("*")
public class NoteChauffeurController {

    @Autowired
    private NoteChauffeurService noteChauffeurService;

    @GetMapping
    public List<NoteChauffeur> findAll() {
        return noteChauffeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteChauffeur> findOne(@PathVariable long id) {
        Optional<NoteChauffeur> noteChauffeur = noteChauffeurService.findOne(id);
        return noteChauffeur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteChauffeur> create(@RequestBody NoteChauffeurDTO noteChauffeurDTO) {
        NoteChauffeur createdNoteChauffeur = noteChauffeurService.create(noteChauffeurDTO);
        return new ResponseEntity<>(createdNoteChauffeur, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteChauffeur> update(@PathVariable("id") long noteChauffeurId, @RequestBody NoteChauffeurDTO noteChauffeurDTO) {
        try {
            NoteChauffeur updatedNoteChauffeur = noteChauffeurService.update(noteChauffeurId, noteChauffeurDTO);
            return new ResponseEntity<>(updatedNoteChauffeur, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        noteChauffeurService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
