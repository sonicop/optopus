package com.sonicop.ohm.optopus.myohmbeads.dto;

import com.sonicop.ohm.optopus.myohmbeads.model.Image;
import java.util.List;

public class ProductInfo {
  private String sku;
  private String name;
  private String imageReference;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageReference() {
    return imageReference;
  }

  public void setImageReference(String imageReference) {
    this.imageReference = imageReference;
  }
}
