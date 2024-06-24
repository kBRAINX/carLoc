package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.ClientDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Client;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.services.ClientService;
import org.reseaux.carLoc.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findOne(@PathVariable long id) {
        Optional<Client> client = clientService.findOne(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<Location>> getLocations(@PathVariable("id") long clientId) {
        List<Location> locations = locationService.findByClientId(clientId);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientDTO clientDTO) {
        Client createdClient = clientService.create(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") long clientId, @RequestBody ClientDTO clientDTO) {
        try {
            Client updatedClient = clientService.update(clientId, clientDTO);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody ClientDTO clientDTO) {
            Optional<Client> client = clientService.login(clientDTO);
            return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
