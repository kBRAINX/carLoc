package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.PriceVehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PriceVehicule;
import org.reseaux.carLoc.repositories.PriceVehiculeRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceVehiculeService {

    @Autowired
    private PriceVehiculeRepository priceVehiculeRepository;

    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<PriceVehicule> findAll() {
        return priceVehiculeRepository.findAll();
    }

    public Optional<PriceVehicule> findOne(long id) {
        return priceVehiculeRepository.findById(id);
    }

    public List<PriceVehicule> findByVehiculeImmatriculation(String vehiculeImmatriculation) {
        return priceVehiculeRepository.findByVehiculeImmatriculation(vehiculeImmatriculation);
    }

    public PriceVehicule create(PriceVehiculeDTO priceVehiculeDTO) {
        PriceVehicule priceVehicule = new PriceVehicule();
        Long newId = cassandraIdGenerator.getNextId("prix_vehicule");
        priceVehicule.setId(newId);
        priceVehicule.setVehiculeImmatriculation(priceVehiculeDTO.getVehiculeImmatriculation());
        priceVehicule.setPriceH(priceVehiculeDTO.getPriceH());
        priceVehicule.setPriceJ(priceVehiculeDTO.getPriceJ());
        return priceVehiculeRepository.save(priceVehicule);
    }

    public PriceVehicule update(long id, PriceVehiculeDTO priceVehiculeDTO) {
        Optional<PriceVehicule> optionalPriceVehicule = priceVehiculeRepository.findById(id);
        if (optionalPriceVehicule.isPresent()) {
            PriceVehicule priceVehicule = optionalPriceVehicule.get();
            priceVehicule.setPriceH(priceVehiculeDTO.getPriceH());
            priceVehicule.setPriceJ(priceVehiculeDTO.getPriceJ());
            return priceVehiculeRepository.save(priceVehicule);
        } else {
            throw new ResourceNotFoundException("PriceVehicule not found with id " + id);
        }
    }

    public void delete(long id) {
        priceVehiculeRepository.deleteById(id);
    }
}
