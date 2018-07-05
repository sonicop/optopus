/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "Products")
public class Product implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 48)
  @Column(name = "sku")
  private String sku;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 500)
  @Column(name = "description")
  private String description;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "map")
  private BigDecimal map;
  @Column(name = "manufactured_date")
  @Temporal(TemporalType.DATE)
  private Date manufacturedDate;
  @Column(name = "discontinued_date")
  @Temporal(TemporalType.DATE)
  private Date discontinuedDate;
  @Column(name = "soldout_date")
  @Temporal(TemporalType.DATE)
  private Date soldoutDate;
  @ManyToMany(mappedBy = "productList")
  private List<Image> imageList;
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  @ManyToOne(optional = false)
  private Brand brandId;
  @JoinColumn(name = "release_type_code", referencedColumnName = "release_code")
  @ManyToOne(optional = false)
  private ReleaseType releaseTypeCode;
  @JoinColumn(name = "rating_id", referencedColumnName = "rating_id")
  @ManyToOne
  private Rating ratingId;

  public Product() {
  }

  public Product(String sku) {
    this.sku = sku;
  }

  public Product(String sku, String name, String description) {
    this.sku = sku;
    this.name = name;
    this.description = description;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getMap() {
    return map;
  }

  public void setMap(BigDecimal map) {
    this.map = map;
  }

  public Date getManufacturedDate() {
    return manufacturedDate;
  }

  public void setManufacturedDate(Date manufacturedDate) {
    this.manufacturedDate = manufacturedDate;
  }

  public Date getDiscontinuedDate() {
    return discontinuedDate;
  }

  public void setDiscontinuedDate(Date discontinuedDate) {
    this.discontinuedDate = discontinuedDate;
  }

  public Date getSoldoutDate() {
    return soldoutDate;
  }

  public void setSoldoutDate(Date soldoutDate) {
    this.soldoutDate = soldoutDate;
  }

  public List<Image> getImageList() {
    return imageList;
  }

  public void setImageList(List<Image> imageList) {
    this.imageList = imageList;
  }

  public Brand getBrandId() {
    return brandId;
  }

  public void setBrandId(Brand brandId) {
    this.brandId = brandId;
  }

  public ReleaseType getReleaseTypeCode() {
    return releaseTypeCode;
  }

  public void setReleaseTypeCode(ReleaseType releaseTypeCode) {
    this.releaseTypeCode = releaseTypeCode;
  }

  public Rating getRatingId() {
    return ratingId;
  }

  public void setRatingId(Rating ratingId) {
    this.ratingId = ratingId;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (sku != null ? sku.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Product)) {
      return false;
    }
    Product other = (Product) object;
    if ((this.sku == null && other.sku != null) || (this.sku != null && !this.sku.equals(other.sku))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.Product[ sku=" + sku + " ]";
  }
  
}
