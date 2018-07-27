/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Categories")
@NamedQueries({
  @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")})
public class Category implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "category_id")
  private Integer categoryId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  @Size(max = 500)
  @Column(name = "comment")
  private String comment;
  @JoinTable(name = "Product_Categories", joinColumns = {
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")}, inverseJoinColumns = {
    @JoinColumn(name = "sku", referencedColumnName = "sku")})
  @ManyToMany(fetch = FetchType.LAZY,
          cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
          })
  private List<Product> productList;
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  @ManyToOne(optional = false)
  private Brand brandId;

  public Category() {
  }

  public Category(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public Category(Integer categoryId, String name) {
    this.categoryId = categoryId;
    this.name = name;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
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

  public List<Product> getProductList() {
    return productList;
  }

  public void setProductList(List<Product> productList) {
    this.productList = productList;
  }

  public Brand getBrandId() {
    return brandId;
  }

  public void setBrandId(Brand brandId) {
    this.brandId = brandId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (categoryId != null ? categoryId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Category)) {
      return false;
    }
    Category other = (Category) object;
    if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.Category[ categoryId=" + categoryId + " ]";
  }
  
}
