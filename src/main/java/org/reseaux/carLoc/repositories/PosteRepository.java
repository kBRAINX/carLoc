package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Poste;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosteRepository extends CassandraRepository<Poste, Long> {
    @AllowFiltering
    List<Poste> findByAgenceId(long agenceId);
}
