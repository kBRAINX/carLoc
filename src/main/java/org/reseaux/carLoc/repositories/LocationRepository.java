package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Location;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CassandraRepository<Location, Long> {
    @AllowFiltering
    List<Location> findByVehiculeId(String vehiculeId);

    @AllowFiltering
    List<Location> findByChauffeurId(long chauffeurId);
}
