package org.reseaux.carLoc.utils;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CassandraIdGenerator {

    private final CqlSession cqlSession;

    @Autowired
    public CassandraIdGenerator(CqlSessionFactoryBean cqlSession) {
        this.cqlSession = (CqlSession) cqlSession;
    }

    public Long getNextId(String tableName) {
        String query = "SELECT max(id) FROM " + tableName;
        Long currentMaxId = cqlSession.execute(query)
            .one()
            .getLong(0);

        return currentMaxId != null ? currentMaxId + 1 : 1L;
    }
}

