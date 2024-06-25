package org.reseaux.carLoc.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LocationDTO {
    private long id;
    private long reservationId;
    private String vehiculeId;
    private long chauffeurId;
    private long clientId;
    private double montant;
    private Date dateDebut;
    private Date dateFin;
    private double kilometrageDebut;
    private double kilometrageFin;
    private double cout_carburant;
    private Date createdAt;
    private Date updatedAt;
}
