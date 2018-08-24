package com.sonicop.ohm.optopus.myohmbeads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
//@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyohmbeadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyohmbeadsApplication.class, args);
	}
}
