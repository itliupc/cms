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

import com.wafer.domain.Manage;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.ManageService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/manage-manage")
@Transactional
public class ManageController {

  @Autowired
  ManageService manageService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(ManageController.class);

  @RequestMapping(value = "/view/{page}")
  public ModelAndView pageView(@PathVariable String page) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userRole = 1;
    if (principal instanceof SysUser) {
      userRole = principal.getUserAuthority();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("manage/" + page);
    view.addObject("userRole", userRole);
    return view;
  }

  /**
   * 查询Manage信息
   * 
   * @return 封装的Manage list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Manage> manageList(@RequestParam Map<String, String> param) {
    Page<Manage> page = manageService.getManageList(param);
    return new GridView<Manage>(page.getContent(), page.getTotalElements());
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addManage", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult manageCreate(Manage manage) {
    Manage manageForCompareNum = manageService.findByCarId(manage.getCarId());
    if (null != manageForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆管理费信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Manage manageInfo = new Manage();
      manageInfo.setCarId(manage.getCarId());
      manageInfo.setEndDate(manage.getEndDate());
      manageInfo.setUpdateUser(userId);
      manageService.manageSave(manageInfo);
      return ResponseResult.success(manageInfo);
    }
  }

  /**
   * 更新manage
   * 
   * @param manage基本信息
   * @return 封装的manage信息
   */
  @RequestMapping(value = "editManage", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult manageModify(Manage manage) {
    Manage manageForCompareNum = manageService.getOtherManageByCarId(manage.getCarId(), manage.getId());
    if (null != manageForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆管理费信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Manage manageInfo = manageService.getManageById(manage.getId());
      manageInfo.setCarId(manage.getCarId());
      manageInfo.setEndDate(manage.getEndDate());
      manageInfo.setUpdateUser(userId);
      manageService.manageSave(manageInfo);
      return ResponseResult.success(manageInfo);
    }
  }

  /**
   * 根据id删除manage信息
   * 
   * @param id
   * @return 封装的manage信息
   */
  @RequestMapping(value = "deleteManage", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult manageDelete(@RequestBody List<Long> ids) {
    manageService.deleteManageByIds(ids);
    return ResponseResult.success(true);
  }


}
