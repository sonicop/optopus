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
public class UsersHasProductPK implements Serializable {

  @Basic(optional = false)
  @NotNull
  @Column(name = "user_id")
  private int userId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 48)
  @Column(name = "sku")
  private String sku;

  public UsersHasProductPK() {
  }

  public UsersHasProductPK(int userId, String sku) {
    this.userId = userId;
    this.sku = sku;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (int) userId;
    hash += (sku != null ? sku.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UsersHasProductPK)) {
      return false;
    }
    UsersHasProductPK other = (UsersHasProductPK) object;
    if (this.userId != other.userId) {
      return false;
    }
    if ((this.sku == null && other.sku != null) || (this.sku != null && !this.sku.equals(other.sku))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.UsersHasProductPK[ userId=" + userId + ", sku=" + sku + " ]";
  }
  
}
