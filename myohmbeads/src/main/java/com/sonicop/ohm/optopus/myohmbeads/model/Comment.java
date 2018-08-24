/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "comments")
@NamedQueries({
  @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c")})
public class Comment implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Lob
  @Column(name = "comment_id")
  private byte[] commentId;
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Basic(optional = false)
  @NotNull
  @Column(name = "rate")
  private int rate;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 1000)
  @Column(name = "comment")
  private String comment;
  @JoinColumn(name = "sku", referencedColumnName = "sku")
  @ManyToOne(optional = false)
  private Product sku;
  @JoinColumn(name = "created_by", referencedColumnName = "user_id")
  @ManyToOne(optional = false)
  private User createdBy;

  public Comment() {
  }

  public Comment(byte[] commentId) {
    this.commentId = commentId;
  }

  public Comment(byte[] commentId, Date createTime, int rate, String comment) {
    this.commentId = commentId;
    this.createTime = createTime;
    this.rate = rate;
    this.comment = comment;
  }

  public byte[] getCommentId() {
    return commentId;
  }

  public void setCommentId(byte[] commentId) {
    this.commentId = commentId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Product getSku() {
    return sku;
  }

  public void setSku(Product sku) {
    this.sku = sku;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (commentId != null ? commentId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Comment)) {
      return false;
    }
    Comment other = (Comment) object;
    if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.myohmbeads.model.Comment[ commentId=" + commentId + " ]";
  }
  
}
