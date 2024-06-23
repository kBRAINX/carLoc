package org.reseaux.carLoc.dto;

import lombok.Data;
import java.util.Date;

@Data
public class VehiculeDTO {
    private String immatriculation;
    private long posteId;
    private long categoryId;
    private String marque;
    private String kilometrage;
    private String transmission;
    private String couleur;
    private String description;
    private String carburant;
    private int places;
    private int bagage;
    private int puissance;
    private boolean air_conditionner;
    private boolean child_seat;
    private boolean climatisation;
    private boolean wifi;
    private boolean tv;
    private boolean gps;
    private boolean bluetooth;
    private boolean seat_belt;
    private boolean statut;
    private Date createdAt;
    private Date updatedAt;
}
