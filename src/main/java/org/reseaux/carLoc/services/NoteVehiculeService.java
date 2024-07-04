package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.NoteVehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.NoteVehicule;
import org.reseaux.carLoc.repositories.NoteVehiculeRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteVehiculeService {

    private final NoteVehiculeRepository noteVehiculeRepository;

    private final CassandraIdGenerator cassandraIdGenerator;

    @Autowired
    public NoteVehiculeService(NoteVehiculeRepository noteVehiculeRepository, CassandraIdGenerator cassandraIdGenerator) {
        this.noteVehiculeRepository = noteVehiculeRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
    }

    public List<NoteVehicule> findAll() {
        return noteVehiculeRepository.findAll();
    }

    public List<NoteVehicule> findByVehiculeId(long id) {
        return noteVehiculeRepository.findAllByVehiculeId(id);
    }

    public List<NoteVehicule> findByClientId(long id) {
        return noteVehiculeRepository.findAllByClientId(id);
    }

    public Optional<NoteVehicule> findOne(long id) {
        return noteVehiculeRepository.findById(id);
    }

    public NoteVehicule create(NoteVehiculeDTO noteVehiculeDTO) {
        NoteVehicule noteVehicule = new NoteVehicule();
        Long newId = cassandraIdGenerator.getNextId("evaluation_vehicule");
        noteVehicule.setId(newId);
        noteVehicule.setVehiculeId(noteVehiculeDTO.getVehiculeId());
        noteVehicule.setClientId(noteVehiculeDTO.getClientId());
        noteVehicule.setNote(noteVehiculeDTO.getNote());
        return noteVehiculeRepository.save(noteVehicule);
    }

    public NoteVehicule update(long id, NoteVehiculeDTO noteVehiculeDTO) {
        Optional<NoteVehicule> optionalNoteVehicule = noteVehiculeRepository.findById(id);
        if (optionalNoteVehicule.isPresent()) {
            NoteVehicule noteVehicule = optionalNoteVehicule.get();
            noteVehicule.setNote(noteVehiculeDTO.getNote());
            return noteVehiculeRepository.save(noteVehicule);
        } else {
            throw new ResourceNotFoundException("NoteVehicule not found with id " + id);
        }
    }

    public void delete(long id) {
        noteVehiculeRepository.deleteById(id);
    }
}
