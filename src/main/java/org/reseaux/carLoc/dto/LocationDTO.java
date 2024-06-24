package org.reseaux.carLoc.dto;

import lombok.Data;
import java.util.Date;

@Data
public class LocationDTO {
    private long clientId;
    private String vehiculeId;
    private Long chauffeurId;
    private Date dateDebut;
    private Date dateFin;
    private double montant;
    private Date createdAt;
}
