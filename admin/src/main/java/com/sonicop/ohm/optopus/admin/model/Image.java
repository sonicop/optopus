/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Images")
public class Image implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "image_id")
  private Integer imageId;
  @Size(max = 500)
  @Column(name = "description")
  private String description;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 200)
  @Column(name = "object_key")
  private String objectKey;
  @JoinTable(name = "Products_has_Images", joinColumns = {
    @JoinColumn(name = "Images_image_id", referencedColumnName = "image_id")}, inverseJoinColumns = {
    @JoinColumn(name = "Products_sku", referencedColumnName = "sku")})
  @ManyToMany
  private List<Product> productList;

  public Image() {
  }

  public Image(Integer imageId) {
    this.imageId = imageId;
  }

  public Image(Integer imageId, String objectKey) {
    this.imageId = imageId;
    this.objectKey = objectKey;
  }

  public Integer getImageId() {
    return imageId;
  }

  public void setImageId(Integer imageId) {
    this.imageId = imageId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getObjectKey() {
    return objectKey;
  }

  public void setObjectKey(String objectKey) {
    this.objectKey = objectKey;
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
    hash += (imageId != null ? imageId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Image)) {
      return false;
    }
    Image other = (Image) object;
    if ((this.imageId == null && other.imageId != null) || (this.imageId != null && !this.imageId.equals(other.imageId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.Image[ imageId=" + imageId + " ]";
  }
  
}
