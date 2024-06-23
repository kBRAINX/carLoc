package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.PriceChauffeurDTO;
import org.reseaux.carLoc.dto.PriceVehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.PriceChauffeur;
import org.reseaux.carLoc.repositories.PriceChauffeurRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceChauffeurService {
    @Autowired
    private PriceChauffeurRepository priceChauffeurRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;


    public List<PriceChauffeur> findAll() {
        return  priceChauffeurRepository.findAll();
    }

    public Optional<PriceChauffeur> findOne(long id) {
        return priceChauffeurRepository.findById(id);
    }

    public List<PriceChauffeur> findByChauffeurId(long chauffeurId) {
        return priceChauffeurRepository.findByChauffeurId(chauffeurId);
    }

    public PriceChauffeur create(PriceChauffeurDTO priceChauffeurDTO) {
        PriceChauffeur priceChauffeur = new PriceChauffeur();
        Long newId = cassandraIdGenerator.getNextId("prix_chauffeur");
        priceChauffeur.setId(newId);
        priceChauffeur.setChauffeurId(priceChauffeurDTO.getChauffeurId());
        priceChauffeur.setPriceH(priceChauffeurDTO.getPriceH());
        priceChauffeur.setPriceJ(priceChauffeurDTO.getPriceJ());
        return priceChauffeurRepository.save(priceChauffeur);
    }

    public PriceChauffeur update(long id, PriceChauffeurDTO priceChauffeurDTO) {
        Optional<PriceChauffeur> optionalPriceChauffeur = priceChauffeurRepository.findById(id);
        if (optionalPriceChauffeur.isPresent()) {
            PriceChauffeur priceChauffeur = optionalPriceChauffeur.get();
            priceChauffeur.setPriceH(priceChauffeurDTO.getPriceH());
            priceChauffeur.setPriceJ(priceChauffeurDTO.getPriceJ());
            return priceChauffeurRepository.save(priceChauffeur);
        } else {
            throw new ResourceNotFoundException("PriceVehicule not found with id " + id);
        }
    }

    public void delete(long id) {
        priceChauffeurRepository.deleteById(id);
    }
}
