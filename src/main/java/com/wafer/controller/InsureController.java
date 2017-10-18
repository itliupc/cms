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

import com.wafer.domain.Insure;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.InsureService;
import com.wafer.utils.GridView;
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
  public ModelAndView pageView(@PathVariable String page) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userRole = 1;
    if (principal instanceof SysUser) {
      userRole = principal.getUserAuthority();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("insure/" + page);
    view.addObject("userRole", userRole);
    return view;
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
  public ResponseResult insureCreate(Insure insure) {
    Insure insureForCompareNum = insureService.findByCarId(insure.getCarId());
    if (null != insureForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆保险信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Insure insureInfo = new Insure();
      insureInfo.setCarId(insure.getCarId());
      insureInfo.setForceInsure(insure.getForceInsure());
      insureInfo.setBusInsure(insure.getBusInsure());
      insureInfo.setOutBuy(insure.getOutBuy());
      insureInfo.setHasReceive(insure.getHasReceive());
      insureInfo.setHasPay(insure.getHasPay());
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
  public ResponseResult insureModify(Insure insure) {
    Insure insureForCompareNum =
        insureService.getOtherInsureByCarId(insure.getCarId(), insure.getId());
    if (null != insureForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆保险信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Insure insureInfo = insureService.getInsureById(insure.getId());
      insureInfo.setCarId(insure.getCarId());
      insureInfo.setForceInsure(insure.getForceInsure());
      insureInfo.setBusInsure(insure.getBusInsure());
      insureInfo.setOutBuy(insure.getOutBuy());
      insureInfo.setHasReceive(insure.getHasReceive());
      insureInfo.setHasPay(insure.getHasPay());
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
