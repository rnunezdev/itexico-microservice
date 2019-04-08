package com.enterprise.usermgmt.users;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EntityScan("com.enterprise.usermgmt")
@EnableJpaRepositories("com.enterprise.usermgmt.users")
@PropertySource("classpath:db-config.properties")
public class UsersConfiguration {

    private static final String INITIAL_SCHEMA_PATH = "classpath:database/schema.sql";
    private static final String INITIAL_DATA_PATH = "classpath:database/data.sql";

    public UsersConfiguration() {
    }

    @Bean
    public DataSource dataSource() {
        return (new EmbeddedDatabaseBuilder()).addScript(INITIAL_SCHEMA_PATH).addScript(INITIAL_DATA_PATH).build();
    }
}