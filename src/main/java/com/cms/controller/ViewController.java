package com.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  @RequestMapping(value = "/signin")
  public String loginView() {
    return "login.html";
  }
  @RequestMapping(value = "/index1")
  public String indexView() {
    return "index.html";
  }

}
