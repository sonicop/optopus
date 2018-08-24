package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineData", types=UserProduct.class)
public interface UserProductProjection {
  UUID getTransactionId();
  Product getProduct();
  User getUser();
  Timestamp getCreateTime();
  String getCurrency();
  BigDecimal getPurchasePrice();
  String getPurchaseFrom();
  Date getPurchaseDate();
  String getNote();
  UUID getCreatedBy();
}
