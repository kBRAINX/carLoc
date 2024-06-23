package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.PriceVehicule;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceVehiculeRepository extends CassandraRepository<PriceVehicule, Long> {
    @AllowFiltering
    List<PriceVehicule> findByVehiculeImmatriculation(String vehiculeImmatriculation);
}
