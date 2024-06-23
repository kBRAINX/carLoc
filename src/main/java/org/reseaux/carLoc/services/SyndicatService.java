package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.SyndicatDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Syndicat;
import org.reseaux.carLoc.repositories.SyndicatRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SyndicatService {

    @Autowired
    private SyndicatRepository syndicatRepository;

    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<Syndicat> findAll() {
        return syndicatRepository.findAll();
    }

    public Optional<Syndicat> findOne(long id) {
        return syndicatRepository.findById(id);
    }

    public Syndicat create(SyndicatDTO syndicatDTO) {
        Syndicat syndicat = new Syndicat();
        Long newId = cassandraIdGenerator.getNextId("syndicat");
        syndicat.setId(newId);
        syndicat.setName(syndicatDTO.getName());
        syndicat.setCity(syndicatDTO.getCity());
        return syndicatRepository.save(syndicat);
    }

    public Syndicat update(long id, SyndicatDTO syndicatDTO) {
        Optional<Syndicat> optionalSyndicat = syndicatRepository.findById(id);
        if (optionalSyndicat.isPresent()) {
            Syndicat syndicat = optionalSyndicat.get();
            syndicat.setName(syndicatDTO.getName());
            syndicat.setCity(syndicatDTO.getCity());
            return syndicatRepository.save(syndicat);
        } else {
            throw new ResourceNotFoundException("Syndicat not found with id " + id);
        }
    }

    public void delete(long id) {
        syndicatRepository.deleteById(id);
    }
}
