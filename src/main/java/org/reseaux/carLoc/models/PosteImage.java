package org.reseaux.carLoc.models;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("service_image")
public class PosteImage {
    @PrimaryKey
    private Long id;
    private long posteId;
    private String name;
    private String type;

    @Column("image_data")
    @CassandraType(type = CassandraType.Name.BLOB)
    private byte[] imageData;
}
