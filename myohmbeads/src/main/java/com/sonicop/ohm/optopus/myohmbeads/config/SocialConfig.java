package com.sonicop.ohm.optopus.myohmbeads.config;

import javax.inject.Inject;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
public class SocialConfig {
  
  @Autowired
  DataSource dataSource;
  
  @Autowired
  FacebookConnectionSignup facebookConnectionSignup;

  @Bean
  public ConnectionFactoryLocator connectionFactoryLocator() {
    ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
    registry.addConnectionFactory(new FacebookConnectionFactory(
            environment.getProperty("spring.social.facebook.clientId"),
            environment.getProperty("spring.social.facebook.clientSecret")));
    return registry;
  }

  @Bean
  public UsersConnectionRepository usersConnectionRepository() {
    JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
            connectionFactoryLocator(), Encryptors.noOpText());
    repository.setConnectionSignUp(facebookConnectionSignup);
    return repository;
  }

  @Inject
  private Environment environment;
}
