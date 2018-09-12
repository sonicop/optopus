/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "images")
@NamedQueries({
  @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Lob
  @Column(name = "image_id")
  private byte[] imageId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1024)
  @Column(name = "reference")
  private String reference;
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Size(max = 500)
  @Column(name = "caption")
  private String caption;
  @JoinColumn(name = "sku", referencedColumnName = "sku")
  @ManyToOne(optional = false)
  private Product sku;
  @JoinColumn(name = "created_by", referencedColumnName = "user_id")
//  @ManyToOne(optional = false)
//  private User createdBy;
  @Column(name = "created_by")
  private byte[] createdBy;
  
  public Image() {
  }

  public Image(byte[] imageId) {
    this.imageId = imageId;
  }

  public Image(byte[] imageId, String reference, Date createTime) {
    this.imageId = imageId;
    this.reference = reference;
    this.createTime = createTime;
  }

  public byte[] getImageId() {
    return imageId;
  }

  public void setImageId(byte[] imageId) {
    this.imageId = imageId;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public Product getSku() {
    return sku;
  }

  public void setSku(Product sku) {
    this.sku = sku;
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
    return "com.sonicop.ohm.optopus.myohmbeads.model.Image[ imageId=" + imageId + " ]";
  }
  
}
