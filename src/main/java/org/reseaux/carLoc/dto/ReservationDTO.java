package org.reseaux.carLoc.dto;

import lombok.Data;
import org.reseaux.carLoc.models.options.Type;

import java.util.Date;

@Data
public class ReservationDTO {
    private long id;
    private long clientId;
    private String vehiculeId;
    private Long chauffeurId;
    private Date dateDebut;
    private Date dateFin;
    private double montant;
    private Type type;
    private double startKilometrage;
    private Date createdAt;
}
