package com.wafer.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wafer.domain.Car;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/car-manage")
@Transactional
public class CarController {

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(CarController.class);

  @RequestMapping(value = "/view/{page}")
  public ModelAndView pageView(@PathVariable String page) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userRole = 1;
    if (principal instanceof SysUser) {
      userRole = principal.getUserAuthority();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("car/" + page);
    view.addObject("userRole", userRole);
    return view;
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
  
  /**
   * 新建Car
   * 
   * @param Car基本信息
   * @return 封装的Car信息
   */
  @RequestMapping(value = "addCar", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult carCreate(Car carInfo) {
      Car carForCompare = carService.findByOperateNum(carInfo.getOperateNum());
      if (null != carForCompare) {
          return ResponseResult.failure("保存失败,该建运号车辆信息已存在!");
      } else {
          SysUser principal =
              (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          long userId = 0L;
          if (principal instanceof SysUser) {
            userId = principal.getUserId();
          }
          Car car = new Car();
          car.setCarNum(carInfo.getCarNum());
          car.setOperateNum(carInfo.getOperateNum());
          car.setOwnerName(carInfo.getOwnerName());
          car.setOwnerPhone(carInfo.getOwnerPhone());
          car.setUpdateUser(userId);
          carService.carSave(car);
          return ResponseResult.success(car);
      }
  }

  /**
   * 更新Car
   * 
   * @param Car基本信息
   * @return 封装的Car信息
   */
  @RequestMapping(value = "editCar", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult carModify(Car car) {
      Car carForCompare = carService.getOtherCarByOperateNum(car.getCarNum(), car.getId());
      if (null != carForCompare) {
          return ResponseResult.failure("保存失败,该建运号车辆信息已存在!");
      } else {
          SysUser principal =
              (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
          long userId = 0L;
          if (principal instanceof SysUser) {
            userId = principal.getUserId();
          }
          Car carInfo = carService.getCarById(car.getId());
          carInfo.setCarNum(car.getCarNum());
          carInfo.setOperateNum(car.getOperateNum());
          carInfo.setOwnerName(car.getOwnerName());
          carInfo.setOwnerPhone(car.getOwnerPhone());
          carInfo.setUpdateUser(userId);
          carService.carSave(carInfo);
          return ResponseResult.success(carInfo);
      }
  }

  /**
   * 根据carId删除Car信息
   * 
   * @param carId
   * @return 封装的Car信息
   */
  @RequestMapping(value = "deleteCar", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult carDelete(@RequestBody List<Long> ids) {
    carService.deleteCarByIds(ids);
      return ResponseResult.success(true);
  }
}
