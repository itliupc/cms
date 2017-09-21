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

import com.wafer.domain.Gps;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.GpsService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/gps-manage")
@Transactional
public class GpsController {

  @Autowired
  GpsService gpsService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(GpsController.class);

  @RequestMapping(value = "/view/{page}")
  public String loginView(@PathVariable String page) {
    return "gps/" + page;
  }

  /**
   * 查询Gps信息
   * 
   * @return 封装的Gps list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Gps> gpsList(@RequestParam Map<String, String> param) {
    Page<Gps> page = gpsService.getGpsList(param);
    return new GridView<Gps>(page.getContent(), page.getTotalElements());
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addGps", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult gpsCreate(Gps gps) {
    Gps gpsForCompareNum = gpsService.findByCarId(gps.getCarId());
    if (null != gpsForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆GPS信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Gps gpsInfo = new Gps();
      gpsInfo.setCarId(gps.getCarId());
      gpsInfo.setEndDate(gps.getEndDate());
      gpsInfo.setUpdateUser(userId);
      gpsService.gpsSave(gpsInfo);
      return ResponseResult.success(gpsInfo);
    }
  }

  /**
   * 更新gps
   * 
   * @param gps基本信息
   * @return 封装的gps信息
   */
  @RequestMapping(value = "editGps", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult gpsModify(Gps gps) {
    Gps gpsForCompareNum = gpsService.getOtherGpsByCarId(gps.getCarId(), gps.getId());
    if (null != gpsForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆GPS信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Gps gpsInfo = gpsService.getGpsById(gps.getId());
      gpsInfo.setCarId(gps.getCarId());
      gpsInfo.setEndDate(gps.getEndDate());
      gpsInfo.setUpdateUser(userId);
      gpsService.gpsSave(gpsInfo);
      return ResponseResult.success(gpsInfo);
    }
  }

  /**
   * 根据id删除gps信息
   * 
   * @param id
   * @return 封装的gps信息
   */
  @RequestMapping(value = "deleteGps", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult gpsDelete(@RequestBody List<Long> ids) {
    gpsService.deleteGpsByIds(ids);
    return ResponseResult.success(true);
  }


}
