package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.NoteVehicule;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteVehiculeRepository extends CassandraRepository<NoteVehicule, Long> {
    @AllowFiltering
    List<NoteVehicule> findAllByVehiculeId(long id);

    @AllowFiltering
    List<NoteVehicule> findAllByClientId(long id);
}
