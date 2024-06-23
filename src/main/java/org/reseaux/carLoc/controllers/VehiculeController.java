package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.VehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.ImageVehicule;
import org.reseaux.carLoc.models.PriceVehicule;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.services.ImageVehiculeService;
import org.reseaux.carLoc.services.PriceVehiculeService;
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
@RequestMapping("/vehicules")
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private ImageVehiculeService imageVehiculeService;
    @Autowired
    private PriceVehiculeService priceVehiculeService;

    @GetMapping
    public ResponseEntity<List<Vehicule>> findAll() {
        List<Vehicule> vehicules = vehiculeService.findAll();
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @GetMapping("/{immatriculation}/images")
    public ResponseEntity<List<ImageVehicule>> findByVehiculeImmatriculation(@PathVariable("immatriculation") String vehiculeImmatriculation) {
        List<ImageVehicule> imageVehicules = imageVehiculeService.getImages(vehiculeImmatriculation);
        return new ResponseEntity<>(imageVehicules, HttpStatus.OK);
    }

    @GetMapping("/{immatriculation}")
    public ResponseEntity<Vehicule> findOne(@PathVariable("immatriculation") String immatriculation) {
        Optional<Vehicule> vehicule = vehiculeService.findOne(immatriculation);
        return vehicule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{Immatriculation}/prices")
    public List<PriceVehicule> getPrices(@PathVariable String Immatriculation) {
        return priceVehiculeService.findByVehiculeImmatriculation(Immatriculation);
    }

    @PostMapping
    public ResponseEntity<Vehicule> create(@RequestBody VehiculeDTO vehiculeDTO) {
        Vehicule createdVehicule = vehiculeService.create(vehiculeDTO);
        return new ResponseEntity<>(createdVehicule, HttpStatus.CREATED);
    }

    @PostMapping("/{vehiculeImmatriculation}/images")
    public ResponseEntity<ImageVehicule> uploadImage(@PathVariable String vehiculeImmatriculation, @RequestParam("file") MultipartFile file) {
        try {
            ImageVehicule uploadedImage = imageVehiculeService.uploadImage(vehiculeImmatriculation, file);
            return ResponseEntity.ok(uploadedImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping("/{immatriculation}")
    public ResponseEntity<Vehicule> update(@PathVariable("immatriculation") String immatriculation, @RequestBody VehiculeDTO vehiculeDTO) {
        try {
            Vehicule updatedVehicule = vehiculeService.update(immatriculation, vehiculeDTO);
            return new ResponseEntity<>(updatedVehicule, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{immatriculation}")
    public ResponseEntity<Void> delete(@PathVariable("immatriculation") String immatriculation) {
        try {
            vehiculeService.delete(immatriculation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
