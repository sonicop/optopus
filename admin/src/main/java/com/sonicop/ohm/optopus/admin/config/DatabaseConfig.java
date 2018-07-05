package com.sonicop.ohm.optopus.admin.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EntityScan(basePackages = "com.sonicop.ohm.optopus.admin.model")
@EnableJpaRepositories(transactionManagerRef = "transactionManager",
                       entityManagerFactoryRef = "entityManagerFactory",
                       basePackages = "com.sonicop.ohm.optopus.admin.repository")
public class DatabaseConfig {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @PersistenceContext
  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setPersistenceUnitName("pu");
    emf.setPersistenceProvider(new HibernatePersistenceProvider());
    emf.setPackagesToScan("com.sonicop.ohm.optopus.admin.model");
    emf.setJpaProperties(additionalProperties());
    return emf;
  }

  @Bean
  @Primary
  public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.jpa")
  public Properties additionalProperties() {
    return new Properties();
  }
}
