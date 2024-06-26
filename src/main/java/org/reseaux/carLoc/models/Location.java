package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Entity
@Table(value = "locations")
public class Location {
    @PrimaryKey
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
