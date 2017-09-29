package com.wafer.security.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.wafer.domain.User;
import com.wafer.security.domain.SysUser;
import com.wafer.service.UserService;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
  
  @Autowired
  private UserService userService;
  
  @Override    
  public void onAuthenticationSuccess(HttpServletRequest request,    
          HttpServletResponse response, Authentication authentication) throws IOException,    
          ServletException {    
      //获得授权后可得到用户信息，更新用户上次登录时间  
      SysUser userDetails = (SysUser)authentication.getPrincipal();    
      User user = userService.getUserbyUserId(userDetails.getUserId());
      user.setLatestLoginTime(new Date());
      userService.userSave(user);
      super.onAuthenticationSuccess(request, response, authentication);   
      request.getSession().setMaxInactiveInterval(-1);
  }    
}
