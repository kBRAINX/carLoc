package org.reseaux.carLoc.configs;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.internal.core.ssl.DefaultSslEngineFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${ASTRA_DB_ID}")
    private String astraDbId;

    @Value("${ASTRA_DB_REGION}")
    private String astraDbRegion;

    @Value("${ASTRA_DB_KEYSPACE}")
    private String astraDbKeyspace;

    @Value("${ASTRA_DB_APPLICATION_TOKEN}")
    private String astraDbApplicationToken;

    @Override
    protected String getKeyspaceName() {
        return astraDbKeyspace;
    }

    @Override
    protected String getContactPoints() {
        return String.format("%s-%s.apps.astra.datastax.com", astraDbId, astraDbRegion);
    }

    @Override
    protected int getPort() {
        return 443;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getLocalDataCenter() {
        return astraDbRegion;
    }

    @Bean(name = "session")
    public CqlSession session() {
        DriverConfigLoader loader = DriverConfigLoader.programmaticBuilder()
            .withStringList(DefaultDriverOption.CONTACT_POINTS,
                List.of(String.format("%s-%s.apps.astra.datastax.com", astraDbId, astraDbRegion)))
            .withInt(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, 10000) // 10 seconds timeout
            .withString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, "token")
            .withString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, astraDbApplicationToken)
            .withString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, astraDbRegion)
            .withClass(DefaultDriverOption.SSL_ENGINE_FACTORY_CLASS, DefaultSslEngineFactory.class)
            .build();

        return CqlSession.builder()
            .withConfigLoader(loader)
            .addContactPoint(new InetSocketAddress(getContactPoints(), getPort()))
            .withKeyspace(astraDbKeyspace)
            .build();
    }

    @Bean
    public CassandraTemplate cassandraTemplate(@Qualifier("session") CqlSession session) {
        return new CassandraTemplate(session);
    }
}
