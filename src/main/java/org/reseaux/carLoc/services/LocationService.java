package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.LocationDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.Location;
import org.reseaux.carLoc.models.PrixCarburant;
import org.reseaux.carLoc.models.Reservation;
import org.reseaux.carLoc.repositories.LocationRepository;
import org.reseaux.carLoc.repositories.PrixCarburantRepository;
import org.reseaux.carLoc.repositories.ReservationRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CassandraIdGenerator cassandraIdGenerator;
    private final ReservationRepository reservationRepository;
    private final PrixCarburantRepository prixCarburantRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository, CassandraIdGenerator cassandraIdGenerator, ReservationRepository reservationRepository, PrixCarburantRepository prixCarburantRepository) {
        this.locationRepository = locationRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
        this.reservationRepository = reservationRepository;
        this.prixCarburantRepository = prixCarburantRepository;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Optional<Location> findOne(long id) {
        return locationRepository.findById(id);
    }

    public List<Location> findByVehiculeId(String vehiculeId){
        return locationRepository.findByVehiculeId(vehiculeId);
    }

    public List<Location> findByChauffeurId(long chauffeurId){
        return locationRepository.findByChauffeurId(chauffeurId);
    }

    public List<Location> findByClientId(long clientId){
        return locationRepository.findByClientId(clientId);
    }

    public Location create(LocationDTO locationDTO) {
        Location location = new Location();
        Long newId = cassandraIdGenerator.getNextId("locations");
        location.setId(newId);
        location.setReservationId(locationDTO.getReservationId());
        Optional<Reservation> reservationOpt = reservationRepository.findById(locationDTO.getReservationId());
        if (reservationOpt.isEmpty()) {
            throw new ResourceNotFoundException("Reservation not found with id " + locationDTO.getReservationId());
        }
        Reservation reservation = reservationOpt.get();
        location.setVehiculeId(reservation.getVehiculeId());
        if (reservation.getChauffeurId() == null){
            location.setChauffeurId(0);
        }else{
            location.setChauffeurId(reservation.getChauffeurId());
        }
        location.setClientId(reservation.getClientId());
        location.setMontant(reservation.getMontant());
        location.setDateDebut(reservation.getDateDebut());
        location.setDateFin(reservation.getDateFin());
        location.setKilometrageDebut(reservation.getStartKilometrage());
        location.setKilometrageFin(locationDTO.getKilometrageFin());
        double coutCarburant = calculateCoutCarburant(locationDTO.getKilometrageFin(), reservation.getStartKilometrage());
        location.setCout_carburant(coutCarburant);
        location.setCreatedAt(new Date());
        return locationRepository.save(location);
    }

    private double calculateCoutCarburant(double kilometrageFin, double kilometrageDebut) {
        double montantCarburant;
        if (kilometrageFin == 0) {
            montantCarburant = 0;
        } else {
            List<PrixCarburant> prixCarburants = prixCarburantRepository.findAll();
            if (prixCarburants.isEmpty()) {
                throw new ResourceNotFoundException("No fuel price found in the database");
            }

            PrixCarburant latestPrixCarburant = prixCarburants.stream()
                .max(Comparator.comparingLong(PrixCarburant::getId))
                .orElseThrow(() -> new ResourceNotFoundException("No fuel price found in the database"));

            double prixCarburant = latestPrixCarburant.getPrice();
            montantCarburant = (kilometrageFin - kilometrageDebut) * prixCarburant;
        }
        return montantCarburant;
    }

    public Location update(long id, LocationDTO locationDTO) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            double coutCarburant = calculateCoutCarburant(locationDTO.getKilometrageFin(), location.getKilometrageDebut());
            location.setCout_carburant(coutCarburant);
            location.setKilometrageFin(locationDTO.getKilometrageFin());
            location.setUpdatedAt(new Date());
            return locationRepository.save(location);
        } else {
            throw new ResourceNotFoundException("Location not found with id " + id);
        }
    }

    public void delete(long id) {
        locationRepository.deleteById(id);
    }
}
