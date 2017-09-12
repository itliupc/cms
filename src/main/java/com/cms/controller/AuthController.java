package com.cms.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cms.security.domain.SysUser;

@Controller
public class AuthController {

  @RequestMapping(value = "/")
  public ModelAndView indexView() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userName = "";
    if (principal instanceof SysUser) {
      userName = ((SysUser) principal).getName();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("index");
    view.addObject("userName", userName);
    return view;
  }

  @RequestMapping(value = "/login")
  public String loginView() {
    return "login";
  }
}
