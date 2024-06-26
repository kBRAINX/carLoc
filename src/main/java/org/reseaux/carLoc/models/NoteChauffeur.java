package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table("evaluation_chauffeur")
public class NoteChauffeur {
    @PrimaryKey
    private long id;
    private long chauffeurId;
    private long clientId;
    private int note;
}
