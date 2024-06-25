package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.ChauffeurDTO;
import org.reseaux.carLoc.dto.SyndicatDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Chauffeur;
import org.reseaux.carLoc.models.Syndicat;
import org.reseaux.carLoc.repositories.ChauffeurRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChauffeurService {
    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<Chauffeur> findAll() {
        return chauffeurRepository.findAll();
    }

    public Optional<Chauffeur> findOne(long id){
        return chauffeurRepository.findById(id);
    }

    public List<Chauffeur> findBySyndicatId(long id){
        return chauffeurRepository.findBySyndicatId(id);
    }

    public List<Chauffeur> findByAgenceId(long id){
        return chauffeurRepository.findByAgenceId(id);
    }

    public List<Chauffeur> findByAgenceIdAndCity(long agenceId, String city){
        return chauffeurRepository.findByAgenceIdAndCity(agenceId, city);
    }

    public List<Chauffeur> findByStatut(boolean statut) {
        return chauffeurRepository.findByStatut(statut);
    }

    public Chauffeur create(ChauffeurDTO chauffeurDTO){
        Chauffeur chauffeur = new Chauffeur();
        Long newId = cassandraIdGenerator.getNextId("chauffeurs");
        chauffeur.setId(newId);
        chauffeur.setSyndicatId(chauffeurDTO.getSyndicatId());
        chauffeur.setAgenceId(chauffeurDTO.getAgenceId());
        chauffeur.setName(chauffeurDTO.getName());
        chauffeur.setEmail(chauffeurDTO.getEmail());
        chauffeur.setCity(chauffeurDTO.getCity());
        chauffeur.setPhoneNumber(chauffeurDTO.getPhoneNumber());
        return chauffeurRepository.save(chauffeur);
    }

    public Chauffeur update(long id, ChauffeurDTO chauffeurDTO) {
        Optional<Chauffeur> optionalChauffeur = chauffeurRepository.findById(id);
        if (optionalChauffeur.isPresent()) {
            Chauffeur chauffeur = optionalChauffeur.get();
            chauffeur.setName(chauffeurDTO.getName());
            chauffeur.setEmail(chauffeurDTO.getEmail());
            chauffeur.setCity(chauffeurDTO.getCity());
            chauffeur.setPhoneNumber(chauffeurDTO.getPhoneNumber());
            chauffeur.setStatut(chauffeurDTO.getStatut());
            return chauffeurRepository.save(chauffeur);
        } else {
            throw new ResourceNotFoundException("Syndicat not found with id " + id);
        }
    }

    public void delete(long id){
        chauffeurRepository.deleteById(id);
    }
}
