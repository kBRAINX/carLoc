package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Chauffeur;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChauffeurRepository extends CassandraRepository<Chauffeur, Long> {
    @AllowFiltering
    List<Chauffeur> findBySyndicatId(long syndicatId);

    @AllowFiltering
    List<Chauffeur> findByAgenceId(long AgenceId);
}
