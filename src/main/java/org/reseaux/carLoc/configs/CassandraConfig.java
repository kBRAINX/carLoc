//package org.reseaux.carLoc.configs;
//
//import com.datastax.oss.driver.api.core.CqlSession;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.data.cassandra.config.SchemaAction;
//import org.springframework.data.cassandra.core.cql.session.init.KeyspacePopulator;
//import org.springframework.data.cassandra.core.cql.session.init.ResourceKeyspacePopulator;
//import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//
//@Configuration
//@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
//public class CassandraConfig {
//
//    @Value("${spring.cassandra.keyspace-name}")
//    private String keyspaceName;
//
//    @Value("${spring.cassandra.username}")
//    private String username;
//
//    @Value("${spring.cassandra.password}")
//    private String password;
//
//    @Value("${spring.cassandra.connection.bundle}")
//    private Resource secureConnectBundle;
//
//    @Bean
//    public CqlSession session() {
//        try {
//            Path bundlePath = secureConnectBundle.getFile().toPath();
//            if (Files.exists(bundlePath)) {
//                return CqlSession.builder()
//                    .withCloudSecureConnectBundle(bundlePath)
//                    .withAuthCredentials(username, password)
//                    .withKeyspace(keyspaceName)
//                    .build();
//            } else {
//                throw new RuntimeException("Secure connect bundle file not found: " + bundlePath);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create CqlSession", e);
//        }
//    }
//    @Bean
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.CREATE_IF_NOT_EXISTS;
//    }
//
//    @Bean
//    public KeyspacePopulator keyspacePopulator() {
//        return new ResourceKeyspacePopulator(
//            secureConnectBundle);
//    }
//}

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


