/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Embeddable
public class ProductsHasCategoryPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 48)
  @Column(name = "sku")
  private String sku;
  @Basic(optional = false)
  @NotNull
  @Column(name = "category_id")
  private int categoryId;

  public ProductsHasCategoryPK() {
  }

  public ProductsHasCategoryPK(String sku, int categoryId) {
    this.sku = sku;
    this.categoryId = categoryId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sku != null ? sku.hashCode() : 0);
    hash += (int) categoryId;
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ProductsHasCategoryPK)) {
      return false;
    }
    ProductsHasCategoryPK other = (ProductsHasCategoryPK) object;
    if ((this.sku == null && other.sku != null) || (this.sku != null && !this.sku.equals(other.sku))) {
      return false;
    }
    if (this.categoryId != other.categoryId) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.ProductsHasCategoryPK[ sku=" + sku + ", categoryId=" + categoryId + " ]";
  }
  
}
