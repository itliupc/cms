package com.wafer.vo;

import java.util.Date;

public class UserVo {

  private long userId;
  
  private String name;

  private String userName;
  
  private int status;
  
  private String password;

  private Date latestLoginTime;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public UserVo() {
    super();
  }

  public UserVo(long userId,  String name, String userName, int status, Date latestLoginTime) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.name = name;
    this.status = status;
    this.latestLoginTime = latestLoginTime;
  }

  public UserVo(long userId, String name, String userName, int status, String password, Date latestLoginTime) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.password = password;
    this.name = name;
    this.status = status;
    this.latestLoginTime = latestLoginTime;
  }
}
