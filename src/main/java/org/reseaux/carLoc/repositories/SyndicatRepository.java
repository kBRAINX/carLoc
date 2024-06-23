package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Syndicat;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyndicatRepository extends CassandraRepository<Syndicat, Long> {
}
