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

import com.wafer.domain.Insure;
import com.wafer.security.domain.SysUser;
import com.wafer.service.InsureService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/insure-manage")
@Transactional
public class InsureController {

  @Autowired
  InsureService insureService;

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
  public ResponseResult insureCreate(Insure insure) {
    Insure userForCompareNum = insureService.findByOperateNum(insure.getOperateNum());
    if (null != userForCompareNum) {
      return ResponseResult.failure("保存失败,营运号已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String userName = "";
      if (principal instanceof SysUser) {
        userName = principal.getUsername();
      }
      insure.setUpdateUser(userName);
      insureService.insureSave(insure);
      return ResponseResult.success(insure);
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
    Insure userForCompareNum =
        insureService.getOtherInsureByOperateNum(insure.getOperateNum(), insure.getId());
    if (null != userForCompareNum) {
      return ResponseResult.failure("保存失败,营运号已存在!");
    } else {
      Insure insureInfo = insureService.getInsureById(insure.getId());
      if (null != insure.getCarNum()) {
        insureInfo.setCarNum(insure.getCarNum());
      }
      if (null != insure.getOperateNum()) {
        insureInfo.setOperateNum(insure.getOperateNum());
      }
      if (null != insure.getForceInsure()) {
        insureInfo.setForceInsure(insure.getForceInsure());
      }
      if (null != insure.getBusInsure()) {
        insureInfo.setBusInsure(insure.getBusInsure());
      }
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      String userName = "";
      if (principal instanceof SysUser) {
        userName = principal.getUsername();
      }
      insureInfo.setUpdateUser(userName);
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
