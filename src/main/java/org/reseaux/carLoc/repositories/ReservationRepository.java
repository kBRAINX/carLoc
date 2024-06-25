package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Reservation;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CassandraRepository<Reservation, Long> {
    @AllowFiltering
    List<Reservation> findByVehiculeId(String vehiculeId);

    @AllowFiltering
    List<Reservation> findByChauffeurId(long chauffeurId);

    @AllowFiltering
    List<Reservation> findByClientId(long clientId);
}
