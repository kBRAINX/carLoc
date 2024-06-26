package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<User, Long> {
    @AllowFiltering
    Optional<User> findByEmail(String email);
}

