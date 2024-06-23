package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table(value = "chauffeurs")
public class Chauffeur {
    @PrimaryKey
    private long id;
    private long agenceId;
    private long syndicatId;
    private String name;
    private String email;
    private String city;
    private String phoneNumber;
}
