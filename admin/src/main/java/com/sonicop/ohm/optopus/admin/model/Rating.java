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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "Ratings")
@NamedQueries({
  @NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r")})
public class Rating implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "rating_id")
  private Integer ratingId;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 100)
  @Column(name = "name")
  private String name;
  @Size(max = 500)
  @Column(name = "comment")
  private String comment;
//  @OneToMany(mappedBy = "ratingId")
//  private List<Product> productList;

  public Rating() {
  }

  public Rating(Integer ratingId) {
    this.ratingId = ratingId;
  }

  public Rating(Integer ratingId, String name) {
    this.ratingId = ratingId;
    this.name = name;
  }

  public Integer getRatingId() {
    return ratingId;
  }

  public void setRatingId(Integer ratingId) {
    this.ratingId = ratingId;
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

//  public List<Product> getProductList() {
//    return productList;
//  }
//
//  public void setProductList(List<Product> productList) {
//    this.productList = productList;
//  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (ratingId != null ? ratingId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Rating)) {
      return false;
    }
    Rating other = (Rating) object;
    if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.Rating[ ratingId=" + ratingId + " ]";
  }
  
}
