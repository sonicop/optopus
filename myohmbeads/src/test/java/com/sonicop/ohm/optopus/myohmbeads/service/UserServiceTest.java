package com.sonicop.ohm.optopus.myohmbeads.service;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
  
  @Test
  public void testLoadUserByUsername() {
    System.out.println("loadUserByUsername");
    String username = "ohm-admin";
    UserDetails result = userDetailsService.loadUserByUsername(username);
    assertNotNull(result);
    assertEquals(username, result.getUsername());
  }
  
}
