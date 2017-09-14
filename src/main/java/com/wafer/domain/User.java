package com.wafer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * Created by liupc
 */

@Entity
@Table(name = "ps_user")
public class User {

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "native")
  @Column(name = "user_id", unique = true, nullable = false)
  private long userId;

  @Column(name = "name")
  private String name;
  
  @Column(name = "user_name")
  private String userName;

  @Column(name = "password")
  private String password;
  
  @Column(name = "email")
  private String email;
  
  @Column(name = "phone")
  private String phone;

  @Column(name = "status")
  private int status;  
  
  @Column(name = "user_authority")
  private int userAuthority;

  @Column(name = "latest_login_time")
  private Date latestLoginTime;

  @Column(name = "create_time")
  private Date createTime;

  @Column(name = "update_time")
  private Date updateTime;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }
  
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getLatestLoginTime() {
    return latestLoginTime;
  }

  public void setLatestLoginTime(Date latestLoginTime) {
    this.latestLoginTime = latestLoginTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  public int getUserAuthority() {
	return userAuthority;
  }

  public void setUserAuthority(int userAuthority) {
	this.userAuthority = userAuthority;
  }

  public User(){
    super();
  }

  public User(long userId, String name, String userName, String password, String email, String phone,
      int status, int userAuthority, Date latestLoginTime, Date createTime, Date updateTime) {
	super();
	this.userId = userId;
	this.name = name;
	this.userName = userName;
	this.password = password;
	this.email = email;
	this.phone = phone;
	this.status = status;
	this.userAuthority = userAuthority;
	this.latestLoginTime = latestLoginTime;
	this.createTime = createTime;
	this.updateTime = updateTime;
	}
  
}
