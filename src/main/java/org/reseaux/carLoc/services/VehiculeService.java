package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.VehiculeDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.repositories.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    public Optional<Vehicule> findOne(String immatriculation) {
        return vehiculeRepository.findById(immatriculation);
    }

    public List<Vehicule> findByPosteId(long posteId) {
        return vehiculeRepository.findByPosteId(posteId);
    }

    public List<Vehicule> findByCategoryId(long categoryId) {
        return vehiculeRepository.findByCategoryId(categoryId);
    }

    public Vehicule create(VehiculeDTO vehiculeDTO) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(vehiculeDTO.getImmatriculation());
        vehicule.setPosteId(vehiculeDTO.getPosteId());
        vehicule.setCategoryId(vehiculeDTO.getCategoryId());
        vehicule.setCreatedAt(new Date());
        return getVehicule(vehiculeDTO, vehicule);
    }

    public Vehicule update(String immatriculation, VehiculeDTO vehiculeDTO) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(immatriculation);
        if (optionalVehicule.isPresent()) {
            Vehicule vehicule = optionalVehicule.get();
            vehicule.setCategoryId(vehiculeDTO.getCategoryId());
            vehicule.setUpdatedAt(new Date());
            return getVehicule(vehiculeDTO, vehicule);
        } else {
            throw new ResourceNotFoundException("Vehicule not found with immatriculation " + immatriculation);
        }
    }

    private Vehicule getVehicule(VehiculeDTO vehiculeDTO, Vehicule vehicule) {
        vehicule.setMarque(vehiculeDTO.getMarque());
        vehicule.setKilometrage(vehiculeDTO.getKilometrage());
        vehicule.setTransmission(vehiculeDTO.getTransmission());
        vehicule.setCouleur(vehiculeDTO.getCouleur());
        vehicule.setDescription(vehiculeDTO.getDescription());
        vehicule.setCarburant(vehiculeDTO.getCarburant());
        vehicule.setPlaces(vehiculeDTO.getPlaces());
        vehicule.setBagage(vehiculeDTO.getBagage());
        vehicule.setPuissance(vehiculeDTO.getPuissance());
        vehicule.setAir_conditionner(vehiculeDTO.isAir_conditionner());
        vehicule.setChild_seat(vehiculeDTO.isChild_seat());
        vehicule.setClimatisation(vehiculeDTO.isClimatisation());
        vehicule.setWifi(vehiculeDTO.isWifi());
        vehicule.setTv(vehiculeDTO.isTv());
        vehicule.setGps(vehiculeDTO.isGps());
        vehicule.setBluetooth(vehiculeDTO.isBluetooth());
        vehicule.setSeat_belt(vehiculeDTO.isSeat_belt());
        vehicule.setStatut(vehiculeDTO.isStatut());
        return vehiculeRepository.save(vehicule);
    }

    public void delete(String immatriculation) {
        vehiculeRepository.deleteById(immatriculation);
    }
}
