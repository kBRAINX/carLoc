package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.models.options.Transmission;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
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
    private String modele;
    private String couleur;

    @Column("kilometrage initial")
    private double kilometrage_init;

    @Column("kilometrage courant")
    private double kilometrage_updated;

    private Transmission transmission;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;

    private Carburant carburant;
    private int places;
    private int bagage;
    private int puissance;
    private int capacity_reservoir;

    @Column("consommation moyenne au Km")
    private float cons_moy_Km;

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
