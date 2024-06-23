package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.PosteDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.repositories.PosteRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PosteService {

    @Autowired
    private PosteRepository posteRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<Poste> findAll() {
        return posteRepository.findAll();
    }

    public List<Poste> findByCategoryId(long categoryId) {
        return posteRepository.findByCategoryId(categoryId);
    }

    public Optional<Poste> findOne(long posteId) {
        return posteRepository.findById(posteId);
    }

    public Poste create(PosteDTO posteDTO) {
        Poste poste = new Poste();
        Long newId = cassandraIdGenerator.getNextId("points_service");
        poste.setId(newId);
        poste.setCategoryId(posteDTO.getCategoryId());
        poste.setName(posteDTO.getName());
        poste.setLocalisation(posteDTO.getLocalisation());
        poste.setSeige(posteDTO.getSiege());
        return posteRepository.save(poste);
    }

    public Poste update(long posteId, PosteDTO posteDTO) {
        Optional<Poste> optionalPoste = posteRepository.findById(posteId);
        if (optionalPoste.isPresent()) {
            Poste poste = optionalPoste.get();
            poste.setName(posteDTO.getName());
            poste.setLocalisation(posteDTO.getLocalisation());
            poste.setSeige(posteDTO.getSiege());
            return posteRepository.save(poste);
        } else {
            throw new ResourceNotFoundException("Poste not found with id " + posteId);
        }
    }

    public void delete(long posteId) {
        posteRepository.deleteById(posteId);
    }
}
