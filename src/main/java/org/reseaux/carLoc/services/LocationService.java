package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.LocationDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.*;
import org.reseaux.carLoc.repositories.*;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private ChauffeurRepository chauffeurRepository;
    @Autowired
    private PriceVehiculeRepository priceVehiculeRepository;
    @Autowired
    private PriceChauffeurRepository priceChauffeurRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;
    @Autowired
    private ClientRepository clientRepository;


    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public List<Location> findByVehiculeId(String vehiculeId) {
        return locationRepository.findByVehiculeId(vehiculeId);
    }

    public List<Location> findByChauffeurId(long chauffeurId) {
        return locationRepository.findByChauffeurId(chauffeurId);
    }

    public List<Location> findByClientId(long clientId) {
        return locationRepository.findByClientId(clientId);
    }

    public Location create(LocationDTO locationDTO) {
        Location location = new Location();
        Long newId = cassandraIdGenerator.getNextId("locations");
        location.setId(newId);
        location.setClientId(locationDTO.getClientId());
        location.setVehiculeId(locationDTO.getVehiculeId());
        location.setChauffeurId(locationDTO.getChauffeurId());
        location.setDateDebut(locationDTO.getDateDebut());
        location.setDateFin(locationDTO.getDateFin());
        location.setCreatedAt(new Date());

        Optional<Client> clientOptional = clientRepository.findById(locationDTO.getClientId());
        if (clientOptional.isEmpty()) {
            throw new ResourceNotFoundException("Client not found with id " + locationDTO.getClientId());
        }

        Optional<Vehicule> vehiculeOptional = vehiculeRepository.findById(locationDTO.getVehiculeId());
        if (vehiculeOptional.isEmpty()) {
            throw new ResourceNotFoundException("Vehicule not found with id " + locationDTO.getVehiculeId());
        }

        Vehicule vehicule = vehiculeOptional.get();
        double montant = calculateMontant(locationDTO.getDateDebut(), locationDTO.getDateFin(), vehicule, locationDTO.getChauffeurId());

        if (locationDTO.getChauffeurId() != null) {
            Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(locationDTO.getChauffeurId());
            if (chauffeurOptional.isEmpty()) {
                throw new ResourceNotFoundException("Chauffeur not found with id " + locationDTO.getChauffeurId());
            }
            Chauffeur chauffeur = chauffeurOptional.get();
            chauffeur.setStatut(false);
            chauffeurRepository.save(chauffeur);
        }

        vehicule.setStatut(false);
        vehiculeRepository.save(vehicule);

        location.setMontant(montant);
        Location savedLocation = locationRepository.save(location);

        sendNotification(savedLocation, vehicule, locationDTO.getChauffeurId(), locationDTO.getClientId());

        return savedLocation;
    }

    private double calculateMontant(Date dateDebut, Date dateFin, Vehicule vehicule, Long chauffeurId) {
        Duration duration = Duration.between(dateDebut.toInstant(), dateFin.toInstant());
        long hours = duration.toHours();
        long days = duration.toDays();

        List<PriceVehicule> vehiculePrices = priceVehiculeRepository.findByVehiculeImmatriculation(vehicule.getImmatriculation());
        if (vehiculePrices.isEmpty()) {
            throw new ResourceNotFoundException("No pricing information found for vehicle with immatriculation " + vehicule.getImmatriculation());
        }
        PriceVehicule priceVehicule = vehiculePrices.get(0); // Assuming the first price entry is the relevant one

        double montant = priceVehicule.getPriceH() * hours;
        if (days > 0) {
            montant = priceVehicule.getPriceJ() * days;
        }

        if (chauffeurId != null) {
            List<PriceChauffeur> chauffeurPrices = priceChauffeurRepository.findByChauffeurId(chauffeurId);
            if (chauffeurPrices.isEmpty()) {
                throw new ResourceNotFoundException("No pricing information found for chauffeur with id " + chauffeurId);
            }
            PriceChauffeur priceChauffeur = chauffeurPrices.get(0); // Assuming the first price entry is the relevant one

            double chauffeurMontant = priceChauffeur.getPriceH() * hours;
            if (days > 0) {
                chauffeurMontant = priceChauffeur.getPriceJ() * days;
            }
            montant += chauffeurMontant;
        }

        return montant;
    }

    private void sendNotification(Location location, Vehicule vehicule, Long chauffeurId, Long clientId) {
        String message = "Location Details:\n"+
            "Vehicule: " + vehicule.getMarque() + " immatriculation: " + vehicule.getImmatriculation() + "\n" +
            "Date Debut: " + location.getDateDebut() + "\n" +
            "Date Fin: " + location.getDateFin() + "\n" +
            "Montant: " + location.getMontant() + "\n";

        if(clientId != null){
            Optional<Client> clientOptional = clientRepository.findById(clientId);
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                message += "Client: " + client.getName() + " Phone number: "+ client.getPhone() + "\n";
            }
        }

        if (chauffeurId != null) {
            Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(chauffeurId);
            if (chauffeurOptional.isPresent()) {
                Chauffeur chauffeur = chauffeurOptional.get();
                message += "Chauffeur: " + chauffeur.getName() + " Phone number: "+ chauffeur.getPhoneNumber() + "\n";
            }
        }

        // Here you can add logic to send the message via email, SMS, etc.
        System.out.println(message);
    }

    public Location findById(long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (locationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Location not found with id " + id);
        }
        return locationOptional.get();
    }
}
