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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Release_Types")
public class ReleaseType implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 4)
  @Column(name = "release_code")
  private String releaseCode;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  @Size(max = 500)
  @Column(name = "comment")
  private String comment;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "releaseTypeCode")
  private List<Product> productList;

  public ReleaseType() {
  }

  public ReleaseType(String releaseCode) {
    this.releaseCode = releaseCode;
  }

  public ReleaseType(String releaseCode, String name) {
    this.releaseCode = releaseCode;
    this.name = name;
  }

  public String getReleaseCode() {
    return releaseCode;
  }

  public void setReleaseCode(String releaseCode) {
    this.releaseCode = releaseCode;
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

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (releaseCode != null ? releaseCode.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof ReleaseType)) {
      return false;
    }
    ReleaseType other = (ReleaseType) object;
    if ((this.releaseCode == null && other.releaseCode != null) || (this.releaseCode != null && !this.releaseCode.equals(other.releaseCode))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.ReleaseType[ releaseCode=" + releaseCode + " ]";
  }
  
}
