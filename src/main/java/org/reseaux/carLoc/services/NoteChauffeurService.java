package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.NoteChauffeurDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.NoteChauffeur;
import org.reseaux.carLoc.repositories.NoteChauffeurRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteChauffeurService {

    @Autowired
    private NoteChauffeurRepository noteChauffeurRepository;

    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<NoteChauffeur> findAll() {
        return noteChauffeurRepository.findAll();
    }

    public List<NoteChauffeur> findAllByClientId(long id) {
        return noteChauffeurRepository.findAllByClientId(id);
    }

    public List<NoteChauffeur> findAllByChauffeurId(long id) {
        return noteChauffeurRepository.findAllByChauffeurId(id);
    }

    public Optional<NoteChauffeur> findOne(long id) {
        return noteChauffeurRepository.findById(id);
    }

    public NoteChauffeur create(NoteChauffeurDTO noteChauffeurDTO) {
        NoteChauffeur noteChauffeur = new NoteChauffeur();
        Long newId = cassandraIdGenerator.getNextId("evaluation_chauffeur");
        noteChauffeur.setId(newId);
        noteChauffeur.setChauffeurId(noteChauffeurDTO.getChauffeurId());
        noteChauffeur.setClientId(noteChauffeurDTO.getClientId());
        noteChauffeur.setNote(noteChauffeurDTO.getNote());
        return noteChauffeurRepository.save(noteChauffeur);
    }

    public NoteChauffeur update(long id, NoteChauffeurDTO noteChauffeurDTO) {
        Optional<NoteChauffeur> optionalNoteChauffeur = noteChauffeurRepository.findById(id);
        if (optionalNoteChauffeur.isPresent()) {
            NoteChauffeur noteChauffeur = optionalNoteChauffeur.get();
            noteChauffeur.setNote(noteChauffeurDTO.getNote());
            return noteChauffeurRepository.save(noteChauffeur);
        } else {
            throw new ResourceNotFoundException("NoteChauffeur not found with id " + id);
        }
    }

    public void delete(long id) {
        noteChauffeurRepository.deleteById(id);
    }
}
