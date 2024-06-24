package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Client;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CassandraRepository<Client, Long> {
    @AllowFiltering
    Optional<Client> findByEmail(String email);
}
