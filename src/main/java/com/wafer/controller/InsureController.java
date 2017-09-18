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

import com.wafer.domain.Car;
import com.wafer.domain.Insure;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.InsureService;
import com.wafer.utils.GridView;
import com.wafer.vo.InsureVo;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/insure-manage")
@Transactional
public class InsureController {

  @Autowired
  InsureService insureService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(InsureController.class);

  @RequestMapping(value = "/view/{page}")
  public String loginView(@PathVariable String page) {
    return "insure/" + page;
  }

  /**
   * 查询Insure信息
   * 
   * @return 封装的Insure list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Insure> insureList(@RequestParam Map<String, String> param) {
    Page<Insure> page = insureService.getInsureList(param);
    return new GridView<Insure>(page.getContent(), page.getTotalElements());
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addInsure", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult insureCreate(InsureVo insure) {
    Insure userForCompareNum = null;
    Car car = carService.findByOperateNum(insure.getOperateNum());
    if (null != car) {
      userForCompareNum = insureService.findByCarId(car.getId());
    } else {
      car = new Car();
    }
    if (null != userForCompareNum) {
      return ResponseResult.failure("保存失败,该建运号车辆信息已存在!");
    } else {
      car.setCarNum(insure.getCarNum());
      car.setOperateNum(insure.getOperateNum());
      car.setOwnerName(insure.getOwnerName());
      car.setOwnerPhone(insure.getOwnerPhone());
      carService.carSave(car);

      Insure insureInfo = new Insure();
      insureInfo.setCarId(car.getId());
      insureInfo.setForceInsure(insure.getForceInsure());
      insureInfo.setBusInsure(insure.getBusInsure());
      insureInfo.setOutBuy(insure.getOutBuy());
      insureInfo.setHasReceive(insure.getHasReceive());
      insureInfo.setHasPay(insure.getHasPay());
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      insureInfo.setUpdateUser(userId);
      insureService.insureSave(insureInfo);
      return ResponseResult.success(insureInfo);
    }
  }

  /**
   * 更新Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "editInsure", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult insureModify(InsureVo insure) {
    Insure userForCompareNum = null;
    Car car = carService.findByOperateNum(insure.getOperateNum());
    if (null != car) {
      userForCompareNum = insureService.getOtherInsureByCarId(car.getId(), insure.getId());
    } else {
      car = new Car();
    }
    if (null != userForCompareNum) {
      return ResponseResult.failure("保存失败,该建运号车辆信息已存在!");
    } else {
      if (null != insure.getCarNum()) {
        car.setCarNum(insure.getCarNum());
      }
      if (null != insure.getOperateNum()) {
        car.setOperateNum(insure.getOperateNum());
      }
      if (null != insure.getOwnerName()) {
        car.setOwnerName(insure.getOwnerName());
      }
      if (null != insure.getOwnerPhone()) {
        car.setOwnerPhone(insure.getOwnerPhone());
      }
      carService.carSave(car);
      
      Insure insureInfo = insureService.findByCarId(insure.getId());
      insureInfo.setCarId(car.getId());
      insureInfo.setForceInsure(insure.getForceInsure());
      insureInfo.setBusInsure(insure.getBusInsure());
      insureInfo.setOutBuy(insure.getOutBuy());
      insureInfo.setHasReceive(insure.getHasReceive());
      insureInfo.setHasPay(insure.getHasPay());
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      insureInfo.setUpdateUser(userId);
      insureService.insureSave(insureInfo);
      return ResponseResult.success(insureInfo);
    }
  }

  /**
   * 根据id删除Insure信息
   * 
   * @param id
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "deleteInsure", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult insureDelete(@RequestBody List<Long> ids) {
    insureService.deleteInsureByIds(ids);
    return ResponseResult.success(true);
  }


}
