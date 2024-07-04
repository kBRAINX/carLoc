package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.PosteDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.models.PosteImage;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.services.PosteImageService;
import org.reseaux.carLoc.services.PosteService;
import org.reseaux.carLoc.services.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postes")
@CrossOrigin("*")
public class PosteController {

    private final PosteService posteService;

    private final VehiculeService vehiculeService;
    private final PosteImageService posteImageService;

    @Autowired
    public PosteController(PosteService posteService, VehiculeService vehiculeService, PosteImageService posteImageService) {
        this.posteService = posteService;
        this.vehiculeService = vehiculeService;
        this.posteImageService = posteImageService;
    }

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

    @GetMapping("/{id}/images")
    public ResponseEntity<List<PosteImage>> getImages(@PathVariable("id") long posteId){
        List<PosteImage> images = posteImageService.getImages(posteId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Poste> create(@RequestBody PosteDTO posteDTO) {
        Poste createdPoste = posteService.create(posteDTO);
        return new ResponseEntity<>(createdPoste, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<PosteImage> uploadImage(@PathVariable long id, @RequestParam("file") MultipartFile file) {
        try {
            PosteImage uploadedImage = posteImageService.uploadImage(id, file);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
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
