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

import com.wafer.domain.Exam;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.ExamService;
import com.wafer.utils.GridView;
import com.wafer.vo.ResponseResult;

@Controller
@RequestMapping("/exam-manage")
@Transactional
public class ExamController {

  @Autowired
  ExamService examService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(ExamController.class);

  @RequestMapping(value = "/view/{page}")
  public ModelAndView pageView(@PathVariable String page) {
    SysUser principal =
        (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    int userRole = 1;
    if (principal instanceof SysUser) {
      userRole = principal.getUserAuthority();
    }
    ModelAndView view = new ModelAndView();
    view.setViewName("exam/" + page);
    view.addObject("userRole", userRole);
    return view;
  }

  /**
   * 查询Exam信息
   * 
   * @return 封装的Exam list信息
   */
  @RequestMapping(value = "list", method = RequestMethod.POST)
  @Transactional(readOnly = true)
  @ResponseBody
  public GridView<Exam> examList(@RequestParam Map<String, String> param) {
    Page<Exam> page = examService.getExamList(param);
    return new GridView<Exam>(page.getContent(), page.getTotalElements());
  }

  /**
   * 新建Insure
   * 
   * @param Insure基本信息
   * @return 封装的Insure信息
   */
  @RequestMapping(value = "addExam", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult examCreate(Exam exam) {
    Exam examForCompareNum = examService.findByCarId(exam.getCarId());
    if (null != examForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆年审信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Exam examInfo = new Exam();
      examInfo.setCarId(exam.getCarId());
      examInfo.setEndDate(exam.getEndDate());
      examInfo.setUpdateUser(userId);
      examService.examSave(examInfo);
      return ResponseResult.success(examInfo);
    }
  }

  /**
   * 更新exam
   * 
   * @param exam基本信息
   * @return 封装的exam信息
   */
  @RequestMapping(value = "editExam", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult examModify(Exam exam) {
    Exam examForCompareNum = examService.getOtherExamByCarId(exam.getCarId(), exam.getId());
    if (null != examForCompareNum) {
      return ResponseResult.failure("保存失败,该车辆年审信息已存在!");
    } else {
      SysUser principal =
          (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      long userId = 0L;
      if (principal instanceof SysUser) {
        userId = principal.getUserId();
      }
      Exam examInfo = examService.getExamById(exam.getId());
      examInfo.setCarId(exam.getCarId());
      examInfo.setEndDate(exam.getEndDate());
      examInfo.setUpdateUser(userId);
      examService.examSave(examInfo);
      return ResponseResult.success(examInfo);
    }
  }

  /**
   * 根据id删除exam信息
   * 
   * @param id
   * @return 封装的exam信息
   */
  @RequestMapping(value = "deleteExam", method = RequestMethod.POST)
  @ResponseBody
  public ResponseResult examDelete(@RequestBody List<Long> ids) {
    examService.deleteExamByIds(ids);
    return ResponseResult.success(true);
  }


}
