package org.reseaux.carLoc.controllers;

import org.reseaux.carLoc.dto.NoteVehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.NoteVehicule;
import org.reseaux.carLoc.services.NoteVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/noteVehicules")
@CrossOrigin("*")
public class NoteVehiculeController {

    @Autowired
    private NoteVehiculeService noteVehiculeService;

    @GetMapping
    public List<NoteVehicule> findAll() {
        return noteVehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteVehicule> findOne(@PathVariable long id) {
        Optional<NoteVehicule> noteVehicule = noteVehiculeService.findOne(id);
        return noteVehicule.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NoteVehicule> create(@RequestBody NoteVehiculeDTO noteVehiculeDTO) {
        NoteVehicule createdNoteVehicule = noteVehiculeService.create(noteVehiculeDTO);
        return new ResponseEntity<>(createdNoteVehicule, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteVehicule> update(@PathVariable("id") long noteVehiculeId, @RequestBody NoteVehiculeDTO noteVehiculeDTO) {
        try {
            NoteVehicule updatedNoteVehicule = noteVehiculeService.update(noteVehiculeId, noteVehiculeDTO);
            return new ResponseEntity<>(updatedNoteVehicule, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        noteVehiculeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
