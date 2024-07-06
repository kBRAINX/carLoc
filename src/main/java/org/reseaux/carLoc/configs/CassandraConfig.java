package org.reseaux.carLoc.configs;

import com.datastax.oss.driver.api.core.CqlSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.username}")
    private String username;

    @Value("${spring.cassandra.password}")
    private String password;

    @Value("${spring.cassandra.connection.bundle}")
    private Resource secureConnectBundle;

    @Override
    protected @NotNull String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    public @NotNull SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE;
    }

    @Bean
    public CqlSession session() {
        try {
            Path bundlePath = secureConnectBundle.getFile().toPath();
            if (Files.exists(bundlePath)) {
                return CqlSession.builder()
                    .withCloudSecureConnectBundle(bundlePath)
                    .withAuthCredentials(username, password)
                    .withKeyspace(keyspaceName)
                    .build();
            } else {
                throw new RuntimeException("Secure connect bundle file not found: " + bundlePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create CqlSession", e);
        }
    }
}
