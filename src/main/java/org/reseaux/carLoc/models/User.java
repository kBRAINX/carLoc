package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table(value = "proprietaires")
public class User {
    @PrimaryKey
    private long id;

    private String name;
    private String email;
    private String password;
    private String sexe;
}
