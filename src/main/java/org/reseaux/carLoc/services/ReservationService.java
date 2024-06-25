package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.ReservationDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.*;
import org.reseaux.carLoc.models.options.Type;
import org.reseaux.carLoc.repositories.*;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
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


    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findByVehiculeId(String vehiculeId) {
        return reservationRepository.findByVehiculeId(vehiculeId);
    }

    public List<Reservation> findByChauffeurId(long chauffeurId) {
        return reservationRepository.findByChauffeurId(chauffeurId);
    }

    public List<Reservation> findByClientId(long clientId) {
        return reservationRepository.findByClientId(clientId);
    }

    public Reservation create(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        Long newId = cassandraIdGenerator.getNextId("reservations");
        reservation.setId(newId);
        reservation.setClientId(reservationDTO.getClientId());
        reservation.setVehiculeId(reservationDTO.getVehiculeId());
        reservation.setChauffeurId(reservationDTO.getChauffeurId());
        reservation.setDateDebut(reservationDTO.getDateDebut());
        reservation.setType(reservationDTO.getType());
        reservation.setDateFin(reservationDTO.getDateFin());
        reservation.setCreatedAt(new Date());

        Optional<Vehicule> vehiculeOpt = vehiculeRepository.findById(reservationDTO.getVehiculeId());
        if (vehiculeOpt.isEmpty()) {
            throw new ResourceNotFoundException("Vehicule not found with immatriculation " + reservationDTO.getVehiculeId());
        }

        Vehicule vehicule = vehiculeOpt.get();
        reservation.setStartKilometrage(vehicule.getKilometrage_updated());
        double montant = calculateMontant(reservationDTO.getDateDebut(), reservationDTO.getDateFin(), vehicule, reservationDTO.getChauffeurId(), reservationDTO.getType());

        if (reservationDTO.getChauffeurId() != null) {
            Optional<Chauffeur> chauffeurOptional = chauffeurRepository.findById(reservationDTO.getChauffeurId());
            if (chauffeurOptional.isEmpty()) {
                throw new ResourceNotFoundException("Chauffeur not found with id " + reservationDTO.getChauffeurId());
            }
            Chauffeur chauffeur = chauffeurOptional.get();
            chauffeur.setStatut(false);
            chauffeurRepository.save(chauffeur);
        }

        vehicule.setStatut(false);
        vehiculeRepository.save(vehicule);

        reservation.setMontant(montant);
        Reservation savedReservation = reservationRepository.save(reservation);

        sendNotification(savedReservation, vehicule, reservationDTO.getChauffeurId(), reservationDTO.getClientId());

        return savedReservation;
    }

    private double calculateMontant(Date dateDebut, Date dateFin, Vehicule vehicule, Long chauffeurId, Type type) {
        long durationHours = Duration.between(dateDebut.toInstant(), dateFin.toInstant()).toHours();
        long durationDays = durationHours / 24 + (durationHours % 24 > 0 ? 1 : 0);

        List<PriceVehicule> vehiculePrices = priceVehiculeRepository.findByVehiculeImmatriculation(vehicule.getImmatriculation());
        if (vehiculePrices.isEmpty()) {
            throw new ResourceNotFoundException("No pricing information found for vehicle with immatriculation " + vehicule.getImmatriculation());
        }
        PriceVehicule priceVehicule = vehiculePrices.stream()
            .max(Comparator.comparingLong(PriceVehicule::getId))
            .orElseThrow(() -> new ResourceNotFoundException("No pricing information found for vehicle with immatriculation " + vehicule.getImmatriculation()));

        double montant = 0;
        if ("HEURE".equalsIgnoreCase(String.valueOf(type))) {
            montant = priceVehicule.getPriceH() * durationHours;
        } else if ("JOUR".equalsIgnoreCase(String.valueOf(type))) {
            montant = priceVehicule.getPriceJ() * durationDays;
        }

        if (chauffeurId != null) {
            List<PriceChauffeur> chauffeurPrices = priceChauffeurRepository.findByChauffeurId(chauffeurId);
            if (chauffeurPrices.isEmpty()) {
                throw new ResourceNotFoundException("No pricing information found for chauffeur with id " + chauffeurId);
            }
            PriceChauffeur priceChauffeur = chauffeurPrices.get(0); // Assuming the first price entry is the relevant one

            if ("HEURE".equalsIgnoreCase(String.valueOf(type))) {
                montant += priceChauffeur.getPriceH() * durationHours;
            } else if ("JOUR".equalsIgnoreCase(String.valueOf(type))) {
                montant += priceChauffeur.getPriceJ() * durationDays;
            }
        }

        return montant;
    }

    private void sendNotification(Reservation reservation, Vehicule vehicule, Long chauffeurId, Long clientId) {
        StringBuilder message = new StringBuilder("Reservation Details:\n")
            .append("Vehicule: ").append(vehicule.getMarque()).append(" immatriculation: ").append(vehicule.getImmatriculation()).append("\n")
            .append("Date Debut: ").append(reservation.getDateDebut()).append("\n")
            .append("Date Fin: ").append(reservation.getDateFin()).append("\n")
            .append("Montant: ").append(reservation.getMontant()).append("\n");

        clientRepository.findById(clientId).ifPresent(client -> message.append("Client: ").append(client.getName()).append(" Phone number: ").append(client.getPhone()).append("\n"));

        if (chauffeurId != null) {
            chauffeurRepository.findById(chauffeurId).ifPresent(chauffeur -> message.append("Chauffeur: ").append(chauffeur.getName()).append(" Phone number: ").append(chauffeur.getPhoneNumber()).append("\n"));
        }

        // Here you can add logic to send the message via email, SMS, etc.
        System.out.println(message);
    }

    public Reservation findById(long id) {
        Optional<Reservation> locationOptional = reservationRepository.findById(id);
        if (locationOptional.isEmpty()) {
            throw new ResourceNotFoundException("Location not found with id " + id);
        }
        return locationOptional.get();
    }

    public void delete(long id) {
        reservationRepository.deleteById(id);
    }
}
