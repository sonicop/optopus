/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.admin.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "Users")
@NamedQueries({
  @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_id")
  private Integer userId;
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_type")
  private int userType;
  @Basic(optional = false)
  @NotNull
  @Column(name = "role_type")
  private int roleType;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 16)
  @Column(name = "username")
  private String username;
  // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "email")
  private String email;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 32)
  @Column(name = "password")
  private String password;
  @Basic(optional = false)
  @NotNull
  @Column(name = "create_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  @Column(name = "verified_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date verifiedTime;
  @Column(name = "eula_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date eulaTime;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<UserProduct> userProductList;
//  @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
//  private List<Image> imageList;

  public User() {
  }

  public User(Integer userId) {
    this.userId = userId;
  }

  public User(Integer userId, int userType, int roleType, String username, String email, String password, Date createTime) {
    this.userId = userId;
    this.userType = userType;
    this.roleType = roleType;
    this.username = username;
    this.email = email;
    this.password = password;
    this.createTime = createTime;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public int getUserType() {
    return userType;
  }

  public void setUserType(int userType) {
    this.userType = userType;
  }

  public int getRoleType() {
    return roleType;
  }

  public void setRoleType(int roleType) {
    this.roleType = roleType;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getVerifiedTime() {
    return verifiedTime;
  }

  public void setVerifiedTime(Date verifiedTime) {
    this.verifiedTime = verifiedTime;
  }

  public Date getEulaTime() {
    return eulaTime;
  }

  public void setEulaTime(Date eulaTime) {
    this.eulaTime = eulaTime;
  }

  public List<UserProduct> getUserProductList() {
    return userProductList;
  }

  public void setUserProductList(List<UserProduct> userProductList) {
    this.userProductList = userProductList;
  }

//  public List<Image> getImageList() {
//    return imageList;
//  }
//
//  public void setImageList(List<Image> imageList) {
//    this.imageList = imageList;
//  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (userId != null ? userId.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof User)) {
      return false;
    }
    User other = (User) object;
    if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.sonicop.ohm.optopus.admin.model.User[ userId=" + userId + " ]";
  }
  
}
