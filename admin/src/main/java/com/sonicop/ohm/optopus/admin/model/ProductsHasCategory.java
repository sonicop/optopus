/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Products_has_Category")
public class ProductsHasCategory implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected ProductsHasCategoryPK productsHasCategoryPK;
  @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Product product;

  public ProductsHasCategory() {
  }

  public ProductsHasCategory(ProductsHasCategoryPK productsHasCategoryPK) {
    this.productsHasCategoryPK = productsHasCategoryPK;
  }

  public ProductsHasCategory(String sku, int categoryId) {
    this.productsHasCategoryPK = new ProductsHasCategoryPK(sku, categoryId);
  }

  public ProductsHasCategoryPK getProductsHasCategoryPK() {
    return productsHasCategoryPK;
  }

  public void setProductsHasCategoryPK(ProductsHasCategoryPK productsHasCategoryPK) {
    this.productsHasCategoryPK = productsHasCategoryPK;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (productsHasCategoryPK != null ? productsHasCategoryPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ProductsHasCategory)) {
      return false;
    }
    ProductsHasCategory other = (ProductsHasCategory) object;
    if ((this.productsHasCategoryPK == null && other.productsHasCategoryPK != null) || (this.productsHasCategoryPK != null && !this.productsHasCategoryPK.equals(other.productsHasCategoryPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.ProductsHasCategory[ productsHasCategoryPK=" + productsHasCategoryPK + " ]";
  }
  
}
