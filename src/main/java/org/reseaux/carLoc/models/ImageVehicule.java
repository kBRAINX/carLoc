package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Table(value = "image_vehicule")
public class ImageVehicule {
    @PrimaryKey
    private Long id;
    private String vehiculeImmatriculation;

    @Column("image_data")
    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] imageData;
}
