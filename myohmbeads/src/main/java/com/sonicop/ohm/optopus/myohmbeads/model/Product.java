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
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "products")
@NamedQueries({
  @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Basic(optional = false)
  @NotNull
  @Lob
  @Column(name = "created_by")
  private byte[] createdBy;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "map")
  private BigDecimal map;
  @Column(name = "release_date")
  @Temporal(TemporalType.DATE)
  private Date releaseDate;
  @Column(name = "discontinued_date")
  @Temporal(TemporalType.DATE)
  private Date discontinuedDate;
  @Column(name = "soldout_date")
  @Temporal(TemporalType.DATE)
  private Date soldoutDate;
  @Size(max = 500)
  @Column(name = "tags")
  private String tags;
  @Size(max = 500)
  @Column(name = "note")
  private String note;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private List<Image> imageList;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "sku")
//  private List<Comment> commentList;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
//  private List<UserProduct> userProductList;
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  @ManyToOne(optional = false)
  private Brand brandId;
  @JoinColumn(name = "release_type_code", referencedColumnName = "release_code")
  @ManyToOne(optional = false)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private ReleaseType releaseTypeCode;

  public Product() {
  }

  public Product(String sku) {
    this.sku = sku;
  }

  public Product(String sku, Date createTime, byte[] createdBy, String name) {
    this.sku = sku;
    this.createTime = createTime;
    this.createdBy = createdBy;
    this.name = name;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getMap() {
    return map;
  }

  public void setMap(BigDecimal map) {
    this.map = map;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
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

  public String getTags() {
    return tags;
  }

  public void setTags(String tags) {
    this.tags = tags;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public List<Image> getImageList() {
    return imageList;
  }

  public void setImageList(List<Image> imageList) {
    this.imageList = imageList;
  }

//  public List<Comment> getCommentList() {
//    return commentList;
//  }

//  public void setCommentList(List<Comment> commentList) {
//    this.commentList = commentList;
//  }

//  public List<UserProduct> getUserProductList() {
//    return userProductList;
//  }

//  public void setUserProductList(List<UserProduct> userProductList) {
//    this.userProductList = userProductList;
//  }

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
    return "com.sonicop.ohm.optopus.myohmbeads.model.Product[ sku=" + sku + " ]";
  }
}
