package org.reseaux.carLoc.configs;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@Configuration
@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
public class CassandraConfig {

    @Bean
    public CqlSession session() {
        return CqlSession
                .builder()
                .withKeyspace("reseau")
                .withLocalDatacenter("datacenter1")
                .build();
    }

    @Bean
    public CassandraTemplate cassandraTemplate() throws Exception {
        return new CassandraTemplate(session());
    }
}

