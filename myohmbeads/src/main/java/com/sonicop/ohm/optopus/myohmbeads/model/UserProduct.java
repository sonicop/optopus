/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "User_Products")
@NamedQueries({
  @NamedQuery(name = "UserProduct.findAll", query = "SELECT u FROM UserProduct u")})
public class UserProduct implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected UserProductPK userProductPK;
  //@Basic(optional = false)
  //@NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Basic(optional = false)
  @NotNull
  @Column(name = "created_by")
  private int createdBy;
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
//  @Size(max = 45)
//  @Column(name = "image_id")
//  private String imageId;
  @Size(max = 500)
  @Column(name = "comment")
  private String comment;
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private User user;
  @JoinColumn(name = "sku", referencedColumnName = "sku", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Product product;

  public UserProduct() {
  }

  public UserProduct(UserProductPK userProductPK) {
    this.userProductPK = userProductPK;
  }

  public UserProduct(UserProductPK userProductPK, Date createTime, int createdBy, int quantity) {
    this.userProductPK = userProductPK;
    this.createTime = createTime;
    this.createdBy = createdBy;
    this.quantity = quantity;
  }

  public UserProduct(int userId, String sku) {
    this.userProductPK = new UserProductPK(userId, sku);
  }

  public UserProductPK getUserProductPK() {
    return userProductPK;
  }

  public void setUserProductPK(UserProductPK userProductPK) {
    this.userProductPK = userProductPK;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
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

//  public String getImageId() {
//    return imageId;
//  }
//
//  public void setImageId(String imageId) {
//    this.imageId = imageId;
//  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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
    hash += (userProductPK != null ? userProductPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UserProduct)) {
      return false;
    }
    UserProduct other = (UserProduct) object;
    if ((this.userProductPK == null && other.userProductPK != null) || (this.userProductPK != null && !this.userProductPK.equals(other.userProductPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.UserProduct[ userProductPK=" + userProductPK + " ]";
  }
  
}
