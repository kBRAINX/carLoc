package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table(value = "prix_chauffeur")
public class PriceChauffeur {
    @PrimaryKey
    private long id;
    private long chauffeurId;
    private double priceH;
    private double priceJ;
}
