package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PosteImage;
import org.reseaux.carLoc.services.PosteImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/imagePoste")
@CrossOrigin("*")
public class PosteImageController {
    @Autowired
    private PosteImageService posteImageService;

    @PatchMapping("/{id}")
    public ResponseEntity<PosteImage> update(@PathVariable("id") Long imageId, @RequestParam("file") MultipartFile file) {
        try {
            PosteImage updatedImageVehicule = posteImageService.update(imageId, file);
            return new ResponseEntity<>(updatedImageVehicule, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = posteImageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
            .body(imageData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long imageId) {
        try {
            posteImageService.delete(imageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
