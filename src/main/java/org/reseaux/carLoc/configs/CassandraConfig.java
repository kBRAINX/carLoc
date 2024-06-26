package org.reseaux.carLoc.configs;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Objects;

@Configuration
@EnableCassandraRepositories(basePackages = "org.reseaux.carLoc.repositories")
public class CassandraConfig{

//    @Value("${spring.cassandra.contact-points}")
//    private String contactPoints;
//
//    @Value("${spring.cassandra.port}")
//    private int port;

    @Value("${spring.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.cassandra.local-datacenter}")
    private String localDatacenter;

//    @Bean
//    @Primary
//    public CqlSessionFactoryBean session() {
//        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
//        session.setContactPoints(contactPoints);
//        session.setPort(port);
//        session.setKeyspaceName(keyspaceName);
//        return session;
//    }
    @Bean
    @Primary
    public CqlSession session() {
        return CqlSession
            .builder()
            .withKeyspace(keyspaceName)
            .withLocalDatacenter(localDatacenter)
            .build();
    }

//    @Bean
//    public SessionFactoryFactoryBean sessionFactory(CqlSessionFactoryBean session, CassandraMappingContext context) {
//        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
//        sessionFactory.setSession(Objects.requireNonNull(session.getObject()));
//        sessionFactory.setConverter(new MappingCassandraConverter(context));
//        sessionFactory.setSchemaAction(getSchemaAction());
//        return sessionFactory;
//    }

    @Bean
    public CassandraTemplate cassandraTemplate() throws Exception {
        return new CassandraTemplate((CqlSession) session());
    }
}
