package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Entity
@Table(value = "vehicules")
public class Vehicule {
    @PrimaryKey
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
    private boolean statut = true;
    private Date createdAt;
    private Date updatedAt;
}
