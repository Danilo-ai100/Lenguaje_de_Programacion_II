package com.escuela.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    
    @Value("${spring.datasource.password}")
    private String databasePassword;
    
    @Value("${spring.datasource.driver-class-name}")
    private String databaseDriver;
    
    @Bean
    @Profile("mysql")
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);
        
        System.out.println("🗄️  Configurando MySQL DataSource");
        System.out.println("📍 URL: " + databaseUrl);
        System.out.println("👤 Usuario: " + databaseUsername);
        
        return dataSource;
    }
    
    @Bean
    @Profile("h2")
    public DataSource h2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:escuela_api;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("admin");
        dataSource.setPassword("escuela123");
        
        System.out.println("🗄️  Configurando H2 DataSource (Desarrollo)");
        
        return dataSource;
    }
}
