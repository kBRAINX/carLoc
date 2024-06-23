package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Vehicule;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepository extends CassandraRepository<Vehicule, String> {
    @AllowFiltering
    List<Vehicule> findByPosteId(long posteId);

    @AllowFiltering
    List<Vehicule> findByCategoryId(long categoryId);
}
