package com.sonicop.ohm.optopus.myohmbeads.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
  }

//  @Override
//  public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    registry.addResourceHandler("/fonts/**","/css/**")
//            .addResourceLocations("/resources/")
//            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
//  }

}
