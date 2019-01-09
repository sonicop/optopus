/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Brands")
@NamedQueries({
  @NamedQuery(name = "Brand.findAll", query = "SELECT b FROM Brand b")})
public class Brand implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "brand_id")
  private Integer brandId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  @Size(max = 500)
  @Column(name = "comment")
  private String comment;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId")
//  private List<Product> productList;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId")
//  private List<Category> categoryList;

  public Brand() {
  }

  public Brand(Integer brandId) {
    this.brandId = brandId;
  }

  public Brand(Integer brandId, String name) {
    this.brandId = brandId;
    this.name = name;
  }

  public Integer getBrandId() {
    return brandId;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

//  public List<Product> getProductList() {
//    return productList;
//  }
//
//  public void setProductList(List<Product> productList) {
//    this.productList = productList;
//  }
//
//  public List<Category> getCategoryList() {
//    return categoryList;
//  }
//
//  public void setCategoryList(List<Category> categoryList) {
//    this.categoryList = categoryList;
//  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (brandId != null ? brandId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Brand)) {
      return false;
    }
    Brand other = (Brand) object;
    if ((this.brandId == null && other.brandId != null) || (this.brandId != null && !this.brandId.equals(other.brandId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.Brand[ brandId=" + brandId + " ]";
  }
  
}
