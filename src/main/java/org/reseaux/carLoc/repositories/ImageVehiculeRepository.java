package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.ImageVehicule;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageVehiculeRepository extends CassandraRepository<ImageVehicule, Long> {
    @AllowFiltering
    List<ImageVehicule> findByVehiculeImmatriculation(String vehiculeImmatriculation);
}
