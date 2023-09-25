package de.phoenix.wgtest;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    public MySQLContainer mySQLContainer() {
        final MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.27");
        mySQLContainer.start();
        return mySQLContainer;
    }

    @Bean
    public DataSource testContainerDataSource(final MySQLContainer container) {
        return DataSourceBuilder.create()
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .driverClassName(container.getDriverClassName())
                .build();
    }
}
