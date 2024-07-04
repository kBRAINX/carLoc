package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.PrixCarburantDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PrixCarburant;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.repositories.PrixCarburantRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrixCarburantService {
    private final PrixCarburantRepository prixCarburantRepository;
    private final CassandraIdGenerator cassandraIdGenerator;

    @Autowired
    public PrixCarburantService(PrixCarburantRepository prixCarburantRepository, CassandraIdGenerator cassandraIdGenerator) {
        this.prixCarburantRepository = prixCarburantRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
    }

    public List<PrixCarburant> findAll() {
        return prixCarburantRepository.findAll();
    }

    public Optional<PrixCarburant> findOne(long id) {
        return prixCarburantRepository.findById(id);
    }

    public Optional<PrixCarburant> findByCarburant(Carburant carburant) {
        return prixCarburantRepository.findByCarburant(carburant);
    }

    public PrixCarburant create(PrixCarburantDTO prixCarburantDTO) {
        PrixCarburant prixCarburant = new PrixCarburant();
        Long newId = cassandraIdGenerator.getNextId("prix_carburant");
        prixCarburant.setId(newId);
        prixCarburant.setCarburant(prixCarburantDTO.getCarburant());
        prixCarburant.setPrice(prixCarburantDTO.getPrice());
        return prixCarburantRepository.save(prixCarburant);
    }

    public PrixCarburant update(long id, PrixCarburantDTO prixCarburantDTO) {
        Optional<PrixCarburant> optionalPrixCarburant = prixCarburantRepository.findById(id);
        if (optionalPrixCarburant.isPresent()) {
            PrixCarburant prixCarburant = optionalPrixCarburant.get();
            prixCarburant.setCarburant(prixCarburantDTO.getCarburant());
            prixCarburant.setPrice(prixCarburantDTO.getPrice());
            return prixCarburantRepository.save(prixCarburant);
        } else {
            throw new ResourceNotFoundException("PrixCarburant not found with id " + id);
        }
    }

    public void delete(long id) {
        prixCarburantRepository.deleteById(id);
    }
}
