package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.AgenceDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Agence;
import org.reseaux.carLoc.repositories.AgenceRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<Agence> findAll() {
        return (List<Agence>) agenceRepository.findAll();
    }

    public Optional<Agence> findOne(long id) {
        return agenceRepository.findById(id);
    }

    public List<Agence> findByUserId(long userId) {
        return agenceRepository.findByUserId(userId);
    }

    public Agence create(AgenceDTO agenceDTO) {
        Agence agence = new Agence();
        Long newId = cassandraIdGenerator.getNextId("agences");
        agence.setId(newId);
        agence.setUserId(agenceDTO.getUserId());
        agence.setName(agenceDTO.getName());
        return agenceRepository.save(agence);
    }

    public Agence update(long id, AgenceDTO agenceDTO) {
        Optional<Agence> optionalAgence = agenceRepository.findById(id);
        if (optionalAgence.isPresent()) {
            Agence agence = optionalAgence.get();
            agence.setName(agenceDTO.getName());
            return agenceRepository.save(agence);
        } else {
            throw new ResourceNotFoundException("Agence not found with id " + id);
        }
    }

    public void delete(long id) {
        agenceRepository.deleteById(id);
    }
}
