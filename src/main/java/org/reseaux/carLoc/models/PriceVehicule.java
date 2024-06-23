package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table(value = "prix_vehicule")
public class PriceVehicule {
    @PrimaryKey
    private long id;
    private String vehiculeImmatriculation;
    private double priceH;
    private double priceJ;
}
