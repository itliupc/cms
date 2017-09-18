package com.wafer.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafer.domain.Car;
import com.wafer.service.CarService;
import com.wafer.utils.GridView;

@Controller
@RequestMapping("/car-manage")
@Transactional
public class CarController {

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(CarController.class);

  @RequestMapping(value = "/view/{page}")
  public String loginView(@PathVariable String page) {
    return "car/" + page;
  }

  /**
   * 查询Car信息
   * 
   * @return 封装的Car list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Car> carList(@RequestParam Map<String, String> param) {
    Page<Car> page = carService.getCarList(param);
    return new GridView<Car>(page.getContent(), page.getTotalElements());
  }
}
