package org.reseaux.carLoc.dto;

import lombok.Data;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.models.options.Transmission;

import java.util.Date;

@Data
public class VehiculeDTO {
    private String immatriculation;
    private long posteId;
    private long categoryId;
    private String marque;
    private String modele;
    private String couleur;
    private double kilometrage_init;
    private double kilometrage_updated;
    private Transmission transmission;
    private String description;
    private Carburant carburant;
    private int places;
    private int bagage;
    private int puissance;
    private int capacity_reservoir;
    private float cons_moy_Km;
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
