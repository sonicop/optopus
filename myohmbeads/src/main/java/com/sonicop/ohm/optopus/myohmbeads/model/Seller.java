/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "sellers")
@NamedQueries({
  @NamedQuery(name = "Seller.findAll", query = "SELECT s FROM Seller s")})
public class Seller implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "seller")
  private String seller;
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Basic(optional = false)
  @NotNull
  @Lob
  @Column(name = "created_by")
  private byte[] createdBy;

  public Seller() {
  }

  public Seller(String seller) {
    this.seller = seller;
  }

  public Seller(String seller, Date createTime, byte[] createdBy) {
    this.seller = seller;
    this.createTime = createTime;
    this.createdBy = createdBy;
  }

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public byte[] getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(byte[] createdBy) {
    this.createdBy = createdBy;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (seller != null ? seller.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Seller)) {
      return false;
    }
    Seller other = (Seller) object;
    if ((this.seller == null && other.seller != null) || (this.seller != null && !this.seller.equals(other.seller))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.myohmbeads.model.Seller[ seller=" + seller + " ]";
  }
  
}
