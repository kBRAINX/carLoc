package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.PrixCarburant;
import org.reseaux.carLoc.models.options.Carburant;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrixCarburantRepository extends CassandraRepository<PrixCarburant, Long> {
    @AllowFiltering
    Optional<PrixCarburant> findByCarburant(Carburant carburant);

    @AllowFiltering
    Optional<PrixCarburant> findTopByOrderByIdDesc();
}
