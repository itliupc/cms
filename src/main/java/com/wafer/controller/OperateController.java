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

import com.wafer.domain.Operate;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.OperateService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/operate-manage")
@Transactional
public class OperateController {

  @Autowired
  OperateService operateService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(OperateController.class);

  @RequestMapping(value = "/view/{page}")
  public String loginView(@PathVariable String page) {
    return "operate/" + page;
  }

  /**
   * 查询Operate信息
   * 
   * @return 封装的Operate list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Operate> operateList(@RequestParam Map<String, String> param) {
    Page<Operate> page = operateService.getOperateList(param);
    return new GridView<Operate>(page.getContent(), page.getTotalElements());
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addOperate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult operateCreate(Operate operate) {
    Operate operateForCompareNum = operateService.findByCarId(operate.getCarId());
    if (null != operateForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆营运证信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Operate operateInfo = new Operate();
      operateInfo.setCarId(operate.getCarId());
      operateInfo.setEndDate(operate.getEndDate());
      operateInfo.setUpdateUser(userId);
      operateService.operateSave(operateInfo);
      return ResponseResult.success(operateInfo);
    }
  }

  /**
   * 更新operate
   * 
   * @param operate基本信息
   * @return 封装的operate信息
   */
  @RequestMapping(value = "editOperate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult operateModify(Operate operate) {
    Operate operateForCompareNum = operateService.getOtherOperateByCarId(operate.getCarId(), operate.getId());
    if (null != operateForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆营运证信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Operate operateInfo = operateService.getOperateById(operate.getId());
      operateInfo.setCarId(operate.getCarId());
      operateInfo.setEndDate(operate.getEndDate());
      operateInfo.setUpdateUser(userId);
      operateService.operateSave(operateInfo);
      return ResponseResult.success(operateInfo);
    }
  }

  /**
   * 根据id删除operate信息
   * 
   * @param id
   * @return 封装的operate信息
   */
  @RequestMapping(value = "deleteOperate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult operateDelete(@RequestBody List<Long> ids) {
    operateService.deleteOperateByIds(ids);
    return ResponseResult.success(true);
  }


}
