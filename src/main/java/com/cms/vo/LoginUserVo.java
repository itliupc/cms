package com.cms.vo;

import com.cms.domain.User;

public class LoginUserVo {
  private String token;
  private User user;
  
  public String getToken() {
    return token;
  }
  
  public void setToken(String token) {
    this.token = token;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public LoginUserVo() {
    super();
  } 
  
  public LoginUserVo(String token, User user) {
    super();
    this.token = token;
    this.user = user;
  }  
  
}
