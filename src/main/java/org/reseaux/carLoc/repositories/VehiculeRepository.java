package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Vehicule;
import org.reseaux.carLoc.models.options.Carburant;
import org.reseaux.carLoc.models.options.Transmission;
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

    @AllowFiltering
    List<Vehicule> findByStatut(boolean statut);

    @AllowFiltering
    List<Vehicule> findByCarburantAndTransmissionAndStatut(Carburant carburant, Transmission transmission, boolean statut);

    @AllowFiltering
    List<Vehicule> findByCarburantAndTransmission(Carburant carburant, Transmission transmission);

    @AllowFiltering
    List<Vehicule> findByCarburantAndStatut(Carburant carburant, boolean statut);

    @AllowFiltering
    List<Vehicule> findByTransmissionAndStatut(Transmission transmission, boolean statut);
}
