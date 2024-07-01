package org.reseaux.carLoc.configs;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.internal.core.ssl.DefaultSslEngineFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@Configuration
@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
public class CassandraConfig {

    @Value("${ASTRA_DB_ID}")
    private String astraDbId;

    @Value("${ASTRA_DB_REGION}")
    private String astraDbRegion;

    @Value("${ASTRA_DB_KEYSPACE}")
    private String astraDbKeyspace;

    @Value("${ASTRA_DB_APPLICATION_TOKEN}")
    private String astraDbApplicationToken;

    @Bean
    public CqlSession session() {
        String contactPoint = String.format("%s-%s.apps.astra.datastax.com", astraDbId, astraDbRegion);
        int port = 443;
        String localDatacenter = astraDbRegion;
        String username = "token";
        String password = astraDbApplicationToken;

        DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
            .withString(DefaultDriverOption.CONTACT_POINTS, contactPoint + ":" + port)
            .withString(DefaultDriverOption.SESSION_NAME, "SpringBootSession")
            .withString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter)
            .withString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, username)
            .withString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, password)
            .withString(DefaultDriverOption.SSL_ENGINE_FACTORY_CLASS, DefaultSslEngineFactory.class.getCanonicalName())
            .build();

        return CqlSession.builder()
            .withConfigLoader(loader)
            .withKeyspace(astraDbKeyspace)
            .build();
    }

    @Bean
    public CassandraTemplate cassandraTemplate() throws Exception {
        return new CassandraTemplate(session());
    }
}


