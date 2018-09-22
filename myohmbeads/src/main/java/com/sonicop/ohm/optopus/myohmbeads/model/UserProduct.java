/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "user_products")
@NamedQueries({
  @NamedQuery(name = "UserProduct.findAll", query = "SELECT u FROM UserProduct u")})
public class UserProduct implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @Basic(optional = false)
  @NotNull
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "transaction_id", columnDefinition = "BINARY(16)")
  private UUID transactionId;
 
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

  // TODO: cannot be mapped with String
  //@Basic(optional = false)
  //@NotNull
  @Column(name = "created_by", columnDefinition = "BINARY(16)", updatable = false)
  private UUID createdBy;

  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Size(min = 1, max = 100)
  @Column(name = "purchase_from")
  private String purchaseFrom;

  @Column(name = "purchase_date")
  @Temporal(TemporalType.DATE)
  private Date purchaseDate;

  @Size(max = 500)
  @Column(name = "note")
  private String note;

  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @ManyToOne(optional = false)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private User user;

  @JoinColumn(name = "sku", referencedColumnName = "sku")
  @ManyToOne(optional = false)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Product product;
  
  @Column(name = "serial_number")
  private String serialNumber;
  
  @JoinColumn(name = "currency_code", referencedColumnName = "currency_code")
  @ManyToOne
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Currency currency;
  
  @Column(name = "updated_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedTime;

  @Column(name = "updated_by", columnDefinition = "BINARY(16)")
  private UUID updatedBy;

  @Column(name = "deleted_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedTime;

  @Column(name = "deleted_by", columnDefinition = "BINARY(16)")
  private UUID deletedBy;
  

  public UserProduct() {
  }

  public UserProduct(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public UserProduct(UUID transactionId, Date createTime, UUID createdBy, BigDecimal purchasePrice, String purchaseFrom) {
    this.transactionId = transactionId;
    this.createTime = createTime;
    this.createdBy = createdBy;
    this.purchasePrice = purchasePrice;
    this.purchaseFrom = purchaseFrom;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    if (createdBy != null) {
      this.createdBy = createdBy;
    } else {
      this.createdBy = this.user.getUserId();
    }
  }

  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
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

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
    if (user !=null && this.createdBy == null) {
      this.createdBy = user.getUserId();
    }    
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Date getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(Date updatedTime) {
    this.updatedTime = updatedTime;
  }

  public UUID getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(UUID updatedBy) {
    this.updatedBy = updatedBy;
  }
  
  public Date getDeletedTime() {
    return deletedTime;
  }

  public void setDeletedTime(Date deletedTime) {
    this.deletedTime = deletedTime;
  }

  public UUID getDeletedBy() {
    return deletedBy;
  }

  public void setDeletedBy(UUID deletedBy) {
    this.deletedBy = deletedBy;
  } 

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (transactionId != null ? transactionId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UserProduct)) {
      return false;
    }
    UserProduct other = (UserProduct) object;
    if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.myohmbeads.model.UserProduct[ transactionId=" + transactionId + " ]";
  }
  
}
