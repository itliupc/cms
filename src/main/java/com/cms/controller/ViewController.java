package com.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  @RequestMapping(value = "/")
  public String indexView() {
    return "index";
  }
  
  @RequestMapping(value = "/login")
  public String loginView() {
    return "login";
  }
}
