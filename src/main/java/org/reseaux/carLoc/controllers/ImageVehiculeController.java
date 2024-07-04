package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.ImageVehicule;
import org.reseaux.carLoc.services.ImageVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image_vehicules")
@CrossOrigin("*")
public class ImageVehiculeController {

    private final ImageVehiculeService imageVehiculeService;

    @Autowired
    public ImageVehiculeController(ImageVehiculeService imageVehiculeService) {
        this.imageVehiculeService = imageVehiculeService;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ImageVehicule> update(@PathVariable("id") Long imageId, @RequestParam("file") MultipartFile file) {
        try {
            ImageVehicule updatedImageVehicule = imageVehiculeService.update(imageId, file);
            return new ResponseEntity<>(updatedImageVehicule, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageVehiculeService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
            .body(imageData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long imageId) {
        try {
            imageVehiculeService.delete(imageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
