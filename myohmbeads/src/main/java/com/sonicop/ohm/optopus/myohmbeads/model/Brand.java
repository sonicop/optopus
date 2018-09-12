/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "brands")
@NamedQueries({
  @NamedQuery(name = "Brand.findAll", query = "SELECT b FROM Brand b")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Brand implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "brand_id")
  private Integer brandId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
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

  public Brand() {
  }

  public Brand(Integer brandId) {
    this.brandId = brandId;
  }

  public Brand(Integer brandId, String name, Date createTime, byte[] createdBy) {
    this.brandId = brandId;
    this.name = name;
    this.createTime = createTime;
    this.createdBy = createdBy;
  }

  public Integer getBrandId() {
    return brandId;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    hash += (brandId != null ? brandId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Brand)) {
      return false;
    }
    Brand other = (Brand) object;
    if ((this.brandId == null && other.brandId != null) || (this.brandId != null && !this.brandId.equals(other.brandId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.myohmbeads.model.Brand[ brandId=" + brandId + " ]";
  }
  
}
