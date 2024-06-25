package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.ImageVehicule;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageVehiculeRepository extends CassandraRepository<ImageVehicule, Long> {
    @AllowFiltering
    Optional<ImageVehicule> findByName(String name);

    @AllowFiltering
    List<ImageVehicule> findByVehiculeImmatriculation(String vehiculeImmatriculation);
}
