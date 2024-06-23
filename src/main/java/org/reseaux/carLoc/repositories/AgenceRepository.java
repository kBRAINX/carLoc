package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Agence;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenceRepository extends CassandraRepository<Agence, Long> {
    @AllowFiltering
    List<Agence> findByUserId(long userId);
}
