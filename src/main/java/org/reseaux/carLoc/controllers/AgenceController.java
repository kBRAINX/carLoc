package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.AgenceDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Agence;
import org.reseaux.carLoc.models.Category;
import org.reseaux.carLoc.models.Chauffeur;
import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.services.AgenceService;
import org.reseaux.carLoc.services.CategoryService;
import org.reseaux.carLoc.services.ChauffeurService;
import org.reseaux.carLoc.services.PosteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agences")
public class AgenceController {

    @Autowired
    private AgenceService agenceService;
    @Autowired
    private PosteService posteService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ChauffeurService chauffeurService;

    @GetMapping
    public List<Agence> getAllAgences() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable long id) {
        Optional<Agence> agence = agenceService.findOne(id);
        return agence.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/services")
    public ResponseEntity<List<Poste>> findByCategoryId(@PathVariable("id") long categoryId) {
        List<Poste> postes = posteService.findByCategoryId(categoryId);
        return new ResponseEntity<>(postes, HttpStatus.OK);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<Category>> getCategoryByCategoryId(@PathVariable("id") long categoryId) {
        List<Category> categories = categoryService.findByAgenceId(categoryId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}/chauffeurs")
    public ResponseEntity<List<Chauffeur>> getChauffeur(@PathVariable("id") long agenceId) {
        List<Chauffeur> chauffeurs = chauffeurService.findByAgenceId(agenceId);
        return new ResponseEntity<>(chauffeurs, HttpStatus.OK);
    }

    @PostMapping
    public Agence createAgence(@RequestBody AgenceDTO agenceDTO) {
        return agenceService.create(agenceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> updateAgence(@PathVariable long id, @RequestBody AgenceDTO agenceDTO) {
        try {
            Agence updatedAgence = agenceService.update(id, agenceDTO);
            return ResponseEntity.ok(updatedAgence);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable long id) {
        agenceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
