package com.sonicop.ohm.optopus.myohmbeads.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("!dev")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//  @Autowired
//  @Qualifier("userDetailsService")
//  private UserDetailsService userDetailsService;

//  @Override
//  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//  public AuthenticationManager authenticationManager() throws Exception {
//    return super.authenticationManagerBean();
//  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //http
    http.csrf().disable() //HTTP with Disable CSRF
                .authorizeRequests()
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/fonts/**").permitAll()
                    .antMatchers("/framework7/**").permitAll()
                    .antMatchers("/images/**").permitAll()
                    .antMatchers("/scripts/**").permitAll()
                    .antMatchers("/**").authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
//http.authorizeRequests().antMatchers("/**").permitAll();
  }
  

  @Order(Ordered.HIGHEST_PRECEDENCE + 10)
  protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

  }
}
