package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineData", types=UserProduct.class)
public interface UserProductProjection {
  int getQuantity();
  BigDecimal unitPrice();
  String getPurchaseFrom();
  Date getPurchaseDate();
  String getComment();
  User getUser();
  Product getProduct();
}
