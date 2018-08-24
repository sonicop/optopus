/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "currencies")
@NamedQueries({
  @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c")})
public class Currency implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 3)
  @Column(name = "currency_code")
  private String currencyCode;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  //@OneToMany(cascade = CascadeType.ALL, mappedBy = "currency")
  //private List<UserProduct> userProductList;

  public Currency() {
  }

  public Currency(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public Currency(String currencyCode, String name) {
    this.currencyCode = currencyCode;
    this.name = name;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public List<UserProduct> getUserProductList() {
//    return userProductList;
//  }

//  public void setUserProductList(List<UserProduct> userProductList) {
//    this.userProductList = userProductList;
//  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (currencyCode != null ? currencyCode.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Currency)) {
      return false;
    }
    Currency other = (Currency) object;
    if ((this.currencyCode == null && other.currencyCode != null) || (this.currencyCode != null && !this.currencyCode.equals(other.currencyCode))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.myohmbeads.model.Currency[ currencyCode=" + currencyCode + " ]";
  }
  
  public String getDropDownText() {
    return currencyCode + " - " + name;
  }
}
