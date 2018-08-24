package com.sonicop.ohm.optopus.myohmbeads.config;

import com.sonicop.ohm.optopus.myohmbeads.model.Brand;
import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.Seller;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(User.class);
    config.exposeIdsFor(Brand.class);
    config.exposeIdsFor(Product.class);
    config.exposeIdsFor(Currency.class);
    config.exposeIdsFor(Seller.class);
    config.exposeIdsFor(UserProduct.class);
  }
}
