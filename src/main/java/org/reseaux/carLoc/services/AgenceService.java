package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.AgenceDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Agence;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.models.Poste;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.repositories.AgenceRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    private final AgenceRepository agenceRepository;
    private final CassandraIdGenerator cassandraIdGenerator;
    private final PosteService posteService;
    private final VehiculeService vehiculeService;
    private final LocationService locationService;

    @Autowired
    public AgenceService(AgenceRepository agenceRepository,
                         CassandraIdGenerator cassandraIdGenerator,
                         PosteService posteService,
                         VehiculeService vehiculeService,
                         LocationService locationService) {
        this.agenceRepository = agenceRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
        this.posteService = posteService;
        this.vehiculeService = vehiculeService;
        this.locationService = locationService;
    }

    public List<Agence> findAll() {
        return agenceRepository.findAll();
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

    public List<Vehicule> getAllVehiclesByAgenceId(long agenceId) {
        List<Vehicule> allVehicles = new ArrayList<>();
        List<Poste> postes = posteService.findByAgenceId(agenceId);
        for (Poste poste : postes) {
            List<Vehicule> vehicules = vehiculeService.findByPosteId(poste.getId());
            allVehicles.addAll(vehicules);
        }
        return allVehicles;
    }

    public List<Location> getAllLocationsByAgenceId(long agenceId) {
        List<Location> allLocations = new ArrayList<>();
        List<Vehicule> vehicules = getAllVehiclesByAgenceId(agenceId);
        for (Vehicule vehicule : vehicules) {
            List<Location> locations = locationService.findByVehiculeId(vehicule.getImmatriculation());
            allLocations.addAll(locations);
        }
        return allLocations;
    }

    public void delete(long id) {
        agenceRepository.deleteById(id);
    }
}
