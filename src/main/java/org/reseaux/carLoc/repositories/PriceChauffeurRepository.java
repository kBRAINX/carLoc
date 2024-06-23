package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.PriceChauffeur;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceChauffeurRepository extends CassandraRepository<PriceChauffeur, Long> {
    @AllowFiltering
    List<PriceChauffeur> findByChauffeurId(long id);
}
