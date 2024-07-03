package org.reseaux.carLoc.repositories;

import org.reseaux.carLoc.models.Category;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CassandraRepository<Category, Long> {
    @AllowFiltering
    List<Category> findByAgenceId(long agenceId);
}
