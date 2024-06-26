package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.NoteChauffeur;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteChauffeurRepository extends CassandraRepository<NoteChauffeur, Long> {
    @AllowFiltering
    List<NoteChauffeur> findAllByChauffeurId(long id);

    @AllowFiltering
    List<NoteChauffeur> findAllByClientId(long id);
}
