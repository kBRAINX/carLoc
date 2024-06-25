package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.reseaux.carLoc.models.options.Type;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Data
@Entity
@Table(value = "reservations")
public class Reservation {
    @PrimaryKey
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
