package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.reseaux.carLoc.models.options.Carburant;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table("prix_carburant")
public class PrixCarburant {
    @PrimaryKey
    private long id;
    private Carburant carburant;
    private double price;
}
