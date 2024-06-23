package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Poste;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PosteRepository extends CassandraRepository<Poste, Long> {
    @AllowFiltering
    List<Poste> findByCategoryId(long categoryId);
}
