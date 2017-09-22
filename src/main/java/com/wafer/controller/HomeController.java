package com.wafer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wafer.domain.Car;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.utils.GridView;

@Controller
@RequestMapping("/home-manage")
@Transactional
public class HomeController {

  @Autowired
  CarService carService; 

  @RequestMapping(value = "/view/index")
  public ModelAndView homeView() {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userRole = 1;
    if (principal instanceof SysUser) {
      userRole = principal.getUserAuthority();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("home");
    view.addObject("userRole", userRole);
    return view;
  }
  
  /**
   * 查询Car汇总信息
   * 
   * @return 封装的Car 汇总信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Car> carList(@RequestParam Map<String, String> param) {
    Page<Car> page = carService.getCarList(param);
    return new GridView<Car>(page.getContent(), page.getTotalElements());
  }
}
