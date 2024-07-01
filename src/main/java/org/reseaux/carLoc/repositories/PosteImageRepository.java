package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.PosteImage;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PosteImageRepository extends CassandraRepository<PosteImage, Long> {
    @AllowFiltering
    Optional<PosteImage> findByName(String name);

    @AllowFiltering
    List<PosteImage> findByPosteId(long posteId);
}
