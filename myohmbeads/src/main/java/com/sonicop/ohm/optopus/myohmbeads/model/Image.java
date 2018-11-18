/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "image_id", columnDefinition = "BINARY(16)")
  private byte[] imageId;
  
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1024)
  @Column(name = "reference")
  private String reference;
  
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  
  @Size(max = 500)
  @Column(name = "caption")
  private String caption;
  
  @JoinColumn(name = "sku", referencedColumnName = "sku")
  @ManyToOne(optional = false)
  private Product product;
  
  @JoinColumn(name = "created_by", referencedColumnName = "user_id")
  @Column(name = "created_by", columnDefinition = "BINARY(16)", updatable = false)
  private UUID createdBy;
  
  @JoinColumn(name = "used_in_transaction_id", referencedColumnName = "transaction_id")
  @Column(name = "used_in_transaction_id", columnDefinition = "BINARY(16)", updatable = false)
  private UUID usedInTransactionId;

  @Column(name = "sort_number")
  private Integer sortNumber;
  
  @Size(max = 255)
  @Column(name = "original_file_name")
  private String originalFileName;
  
  @Size(max = 100)
  @Column(name = "taken_by_make")
  private String takenByMake;
  
  @Size(max = 100)
  @Column(name = "taken_by_model")
  private String takenByModel;
 
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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product sku) {
    this.product = sku;
  }

  public UUID getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(UUID createdBy) {
    this.createdBy = createdBy;
  }

  public UUID getUsedInTransactionId() {
    return usedInTransactionId;
  }

  public void setUsedInTransactionId(UUID usedInTransactionId) {
    this.usedInTransactionId = usedInTransactionId;
  }

  public Integer getSortNumber() {
    return sortNumber;
  }

  public void setSortNumber(Integer sortNumber) {
    this.sortNumber = sortNumber;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }

  public String getTakenByMake() {
    return takenByMake;
  }

  public void setTakenByMake(String takenByMake) {
    this.takenByMake = takenByMake;
  }

  public String getTakenByModel() {
    return takenByModel;
  }

  public void setTakenByModel(String takenByModel) {
    this.takenByModel = takenByModel;
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
