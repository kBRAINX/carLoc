package org.reseaux.carLoc.utils;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CassandraIdGenerator {

    private final CqlSession cqlSession;

    @Autowired
    public CassandraIdGenerator(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }

    public Long getNextId(String tableName) {
        String query = "SELECT max(id) FROM " + tableName;
        Long currentMaxId = Objects.requireNonNull(cqlSession.execute(query)
                .one())
            .getLong(0);

        return currentMaxId != null ? currentMaxId + 1 : 1L;
    }
}

