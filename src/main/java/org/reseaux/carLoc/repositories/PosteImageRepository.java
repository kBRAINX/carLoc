package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.PosteImage;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface PosteImageRepository extends CassandraRepository<PosteImage, Long> {
    @AllowFiltering
    Optional<PosteImage> findByName(String name);

    @AllowFiltering
    List<PosteImage> findByPosteId(long posteId);
}
