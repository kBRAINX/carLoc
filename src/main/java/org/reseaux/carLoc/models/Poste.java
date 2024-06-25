package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import org.reseaux.carLoc.models.options.*;

@Data
@Entity
@Table(value = "points_service")
public class Poste {
    @PrimaryKey
    private long id;
    private long agenceId;
    private String name;
    private String localisation;
    private Siege seige;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String description;
}
