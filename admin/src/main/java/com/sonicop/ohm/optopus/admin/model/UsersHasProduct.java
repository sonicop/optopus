/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Users_has_Products")
public class UsersHasProduct implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected UsersHasProductPK usersHasProductPK;
  @Basic(optional = false)
  @NotNull
  @Column(name = "quantity")
  private int quantity;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "unit_price")
  private BigDecimal unitPrice;
  @Size(max = 100)
  @Column(name = "purchase_from")
  private String purchaseFrom;
  @Column(name = "purchase_date")
  @Temporal(TemporalType.DATE)
  private Date purchaseDate;
  @Size(max = 500)
  @Column(name = "notes")
  private String notes;
  @Size(max = 45)
  @Column(name = "image_id")
  private String imageId;
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private User user;
  @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Product product;

  public UsersHasProduct() {
  }

  public UsersHasProduct(UsersHasProductPK usersHasProductPK) {
    this.usersHasProductPK = usersHasProductPK;
  }

  public UsersHasProduct(UsersHasProductPK usersHasProductPK, int quantity) {
    this.usersHasProductPK = usersHasProductPK;
    this.quantity = quantity;
  }

  public UsersHasProduct(int userId, String sku) {
    this.usersHasProductPK = new UsersHasProductPK(userId, sku);
  }

  public UsersHasProductPK getUsersHasProductPK() {
    return usersHasProductPK;
  }

  public void setUsersHasProductPK(UsersHasProductPK usersHasProductPK) {
    this.usersHasProductPK = usersHasProductPK;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getPurchaseFrom() {
    return purchaseFrom;
  }

  public void setPurchaseFrom(String purchaseFrom) {
    this.purchaseFrom = purchaseFrom;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
    hash += (usersHasProductPK != null ? usersHasProductPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsersHasProduct)) {
      return false;
    }
    UsersHasProduct other = (UsersHasProduct) object;
    if ((this.usersHasProductPK == null && other.usersHasProductPK != null) || (this.usersHasProductPK != null && !this.usersHasProductPK.equals(other.usersHasProductPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.UsersHasProduct[ usersHasProductPK=" + usersHasProductPK + " ]";
  }
  
}
