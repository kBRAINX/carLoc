package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.ChauffeurDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Chauffeur;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.models.Reservation;
import org.reseaux.carLoc.models.PriceChauffeur;
import org.reseaux.carLoc.services.ChauffeurService;
import org.reseaux.carLoc.services.LocationService;
import org.reseaux.carLoc.services.ReservationService;
import org.reseaux.carLoc.services.PriceChauffeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chauffeurs")
public class ChauffeurController {
    @Autowired
    private ChauffeurService chauffeurService;
    @Autowired
    private PriceChauffeurService priceChauffeurService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Chauffeur> findAll(){
        return chauffeurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chauffeur> findOne(@PathVariable long id){
        Optional<Chauffeur> chauffeur = chauffeurService.findOne(id);
        return chauffeur.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/prices")
    public ResponseEntity<List<PriceChauffeur>> getPrices(@PathVariable long id){
         List<PriceChauffeur> prices = priceChauffeurService.findByChauffeurId(id);
         return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Chauffeur>> findAvailable() {
        List<Chauffeur> chauffeurs = chauffeurService.findByStatut(true);
        return new ResponseEntity<>(chauffeurs, HttpStatus.OK);
    }

    @GetMapping("/{id}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("id") long chauffeurId) {
        List<Reservation> reservations = reservationService.findByChauffeurId(chauffeurId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<Location>> getLocations(@PathVariable("id") long chauffeurId){
        List<Location> locations = locationService.findByChauffeurId(chauffeurId);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Chauffeur> create(@RequestBody ChauffeurDTO chauffeurDTO){
        Chauffeur createdChauffeur = chauffeurService.create(chauffeurDTO);
        return new ResponseEntity<>(createdChauffeur, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Chauffeur> update(@PathVariable("id") long chauffeurId, @RequestBody ChauffeurDTO chauffeurDTO) {
        try {
            Chauffeur updatedChauffeur = chauffeurService.update(chauffeurId, chauffeurDTO);
            return new ResponseEntity<>(updatedChauffeur, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long chauffeurId) {
        try {
            chauffeurService.delete(chauffeurId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
