package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.VehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.*;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.models.options.Transmission;
import org.reseaux.carLoc.services.*;
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
@CrossOrigin("*")
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private ImageVehiculeService imageVehiculeService;
    @Autowired
    private PriceVehiculeService priceVehiculeService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PosteService posteService;
    @Autowired
    private ChauffeurService chauffeurService;
    @Autowired
    private LocationService locationService;

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

    @GetMapping("/search")
    public ResponseEntity<List<Vehicule>> search(
        @RequestParam(name = "carburant", required = false) Carburant carburant,
        @RequestParam(name = "transmission", required = false) Transmission transmission){
        List<Vehicule> vehicules = vehiculeService.searchVehicles(carburant, transmission);
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Vehicule>> findAvailable() {
        List<Vehicule> vehicules = vehiculeService.findByStatut(true);
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    @GetMapping("/{immatriculation}/reservations")
    public ResponseEntity<List<Reservation>> getReservations(@PathVariable("immatriculation") String immatriculation) {
        List<Reservation> reservations = reservationService.findByVehiculeId(immatriculation);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{immatriculation}/chauffeurs")
    public ResponseEntity<List<Chauffeur>> getChauffeurs(@PathVariable("immatriculation") String immatriculation) {
        Optional<Vehicule> vehicule = vehiculeService.findOne(immatriculation);
        if (vehicule.isPresent()) {
            long posteId =  vehicule.get().getPosteId();
            Optional<Poste> poste = posteService.findOne(posteId);
            if (poste.isPresent()) {
                String city = poste.get().getLocalisation();
                long agenceId = poste.get().getAgenceId();
                List<Chauffeur> chauffeurs = chauffeurService.findByAgenceIdAndCity(agenceId, city);
                return new ResponseEntity<>(chauffeurs, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{immatriculation}/locations")
    public ResponseEntity<List<Location>> getLocations(@PathVariable("immatriculation") String immatriculation){
        List<Location> locations = locationService.findByVehiculeId(immatriculation);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

//    @GetMapping("/{immatriculation}/notes")


    @PostMapping
    public ResponseEntity<Vehicule> create(@RequestBody VehiculeDTO vehiculeDTO) {
        Vehicule createdVehicule = vehiculeService.create(vehiculeDTO);
        return new ResponseEntity<>(createdVehicule, HttpStatus.CREATED);
    }

    @PostMapping("/{vehiculeImmatriculation}/images")
    public ResponseEntity<List<ImageVehicule>> uploadImages(@PathVariable String vehiculeImmatriculation, @RequestPart("files") MultipartFile[] files) {
        try {
            List<ImageVehicule> uploadedImages = imageVehiculeService.uploadImage(vehiculeImmatriculation, files);
            return ResponseEntity.ok(uploadedImages);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
