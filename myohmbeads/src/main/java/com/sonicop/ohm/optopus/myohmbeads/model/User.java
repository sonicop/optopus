/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author oproot
 */
@Entity
@Table(name = "users")
@NamedQueries({
  @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements UserDetails, Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @Basic(optional = false)
  @NotNull
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "BINARY(16)")
  private UUID userId;
  
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_type")
  private int userType;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "role_type")
  private String roleType;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
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
  @Size(min = 1, max = 255)
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
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
  private List<Image> imageList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
  private List<Comment> commentList;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<UserProduct> userProductList;

  public User() {
  }

  public User(UUID userId) {
    this.userId = userId;
  }

  public User(UUID userId, int userType, String roleType, String username, String email, String password, Date createTime) {
    this.userId = userId;
    this.userType = userType;
    this.roleType = roleType;
    this.username = username;
    this.email = email;
    this.password = password;
    this.createTime = createTime;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public int getUserType() {
    return userType;
  }

  public void setUserType(int userType) {
    this.userType = userType;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
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

  public List<Image> getImageList() {
    return imageList;
  }

  public void setImageList(List<Image> imageList) {
    this.imageList = imageList;
  }

  public List<Comment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<Comment> commentList) {
    this.commentList = commentList;
  }

  public List<UserProduct> getUserProductList() {
    return userProductList;
  }

  public void setUserProductList(List<UserProduct> userProductList) {
    this.userProductList = userProductList;
  }

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
    return "com.sonicop.ohm.optopus.myohmbeads.model.User[ userId=" + userId + " ]";
  }
  
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// we never lock accounts
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// credentials never expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
