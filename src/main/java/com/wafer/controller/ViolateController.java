package com.wafer.controller;

import java.util.ArrayList;
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

import com.wafer.domain.Violate;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.ViolateService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/violate-manage")
@Transactional
public class ViolateController {

  @Autowired
  ViolateService violateService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(ViolateController.class);

  @RequestMapping(value = "/view/{page}")
  public String loginView(@PathVariable String page) {
    return "violate/" + page;
  }

  /**
   * 查询Violate信息
   * 
   * @return 封装的Violate list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Violate> violateList(@RequestParam Map<String, String> param) {
    Page<Violate> page = violateService.getViolateList(param);
    return new GridView<Violate>(page.getContent(), page.getTotalElements());
  }
  
  /**
   * 根据CarId查询Violate信息
   * 
   * @return 封装的Violate list信息
   */
  @RequestMapping(value = "listByCarId", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public List<Violate> listViolateByCarId(@RequestParam Map<String, String> param) {
    List<Violate> list = new ArrayList<Violate>();
    if(param.containsKey("carId") && null != param.get("carId")){
      Long carId = Long.parseLong(param.get("carId"));
      list = violateService.getViolateListByCarId(carId);
    }
    return list;
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addViolate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult violateCreate(Violate violate) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    long userId = 0L;
    if (principal instanceof SysUser) {
      userId = principal.getUserId();
    }
    Violate violateInfo = new Violate();
    violateInfo.setCarId(violate.getCarId());
    violateInfo.setRecordDate(violate.getRecordDate());
    violateInfo.setHasDeal(violate.getHasDeal());
    violateInfo.setUpdateUser(userId);
    violateService.violateSave(violateInfo);
    return ResponseResult.success(violateInfo);
  }

  /**
   * 更新violate
   * 
   * @param violate基本信息
   * @return 封装的violate信息
   */
  @RequestMapping(value = "editViolate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult violateModify(Violate violate) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    long userId = 0L;
    if (principal instanceof SysUser) {
      userId = principal.getUserId();
    }
    Violate violateInfo = violateService.getViolateById(violate.getId());
    violateInfo.setCarId(violate.getCarId());
    violateInfo.setRecordDate(violate.getRecordDate());
    violateInfo.setHasDeal(violate.getHasDeal());
    violateInfo.setUpdateUser(userId);
    violateService.violateSave(violateInfo);
    return ResponseResult.success(violateInfo);
  }

  /**
   * 根据id删除violate信息
   * 
   * @param id
   * @return 封装的violate信息
   */
  @RequestMapping(value = "deleteViolate", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult violateDelete(@RequestBody List<Long> ids) {
    violateService.deleteViolateByIds(ids);
    return ResponseResult.success(true);
  }


}
