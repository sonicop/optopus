package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Brand;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineData", types=Brand.class)
public interface BrandProjection {
  Integer getBrandId();
  String getName();
}
