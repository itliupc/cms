package com.wafer.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wafer.domain.Car;
import com.wafer.domain.Exam;
import com.wafer.domain.Gps;
import com.wafer.domain.Insure;
import com.wafer.domain.Manage;
import com.wafer.domain.Operate;
import com.wafer.domain.Violate;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.ExamService;
import com.wafer.service.GpsService;
import com.wafer.service.InsureService;
import com.wafer.service.ManageService;
import com.wafer.service.OperateService;
import com.wafer.service.ViolateService;

@Controller
@RequestMapping("/imp-manage")
@Transactional
public class ImpController {

  private final static String excel2003L = ".xls"; // 2003- 版本的excel
  private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

  @Autowired
  InsureService insureService;

  @Autowired
  ViolateService violateService;

  @Autowired
  GpsService gpsService;

  @Autowired
  OperateService operateService;

  @Autowired
  ExamService examService;

  @Autowired
  ManageService manageService;

  @Autowired
  CarService carService;

  Logger logger = LoggerFactory.getLogger(ImpController.class);

  /**
   * 描述：根据文件后缀，自适应上传文件的版本
   * 
   * @param inStr ,fileName
   * @return
   * @throws Exception
   */
  private Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
    Workbook wb = null;
    String fileType = fileName.substring(fileName.lastIndexOf("."));
    if (excel2003L.equals(fileType)) {
      wb = new HSSFWorkbook(inStr); // 2003-
    } else if (excel2007U.equals(fileType)) {
      wb = new XSSFWorkbook(inStr); // 2007+
    } else {
      throw new Exception("解析的文件格式有误！");
    }
    return wb;
  }

  public String getCellValue(Cell cell) {
    String value = "";
    DecimalFormat df = new DecimalFormat("0"); // 格式化number String字符
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 日期格式化
    DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
    if (null == cell) {
      return value;
    }

    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        value = cell.getRichStringCellValue().getString().trim();
        break;
      case Cell.CELL_TYPE_NUMERIC:
        if ("General".equals(cell.getCellStyle().getDataFormatString())) {
          value = df.format(cell.getNumericCellValue());
        } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
          value = sdf.format(cell.getDateCellValue());
        } else if ("yyyy\\-m\\-d".equals(cell.getCellStyle().getDataFormatString())) {
          value = sdf.format(cell.getDateCellValue());
        } else {
          value = df2.format(cell.getNumericCellValue());
        }
        break;
      case Cell.CELL_TYPE_BOOLEAN:
        value = String.valueOf(cell.getBooleanCellValue());
        break;
      case Cell.CELL_TYPE_BLANK:
        value = "";
        break;
      default:
        break;
    }
    return value;
  }

  @RequestMapping(value = "/import/{name}", method = {RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public String importExcel(@PathVariable("name") String name, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

    MultipartFile file = multipartRequest.getFile("upfile");
    if (file.isEmpty()) {
      throw new Exception("文件不存在！");
    }
    InputStream in = file.getInputStream();
    Workbook work = this.getWorkbook(in, file.getOriginalFilename());
    if (null == work)
      return "";
    Sheet sheet = work.getSheetAt(0);
    if (null == sheet)
      return "";
    Row row = null;
    // 遍历当前sheet中的所有行
    StringBuffer result = new StringBuffer();
    for (int i = sheet.getLastRowNum(); i >= 1; i--) {
      row = sheet.getRow(i);
      if (row == null) {
        continue;
      }
      if ("insure".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          importInsure(row, userId);
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(2)).trim()).append(",\n");
        }
      } else if ("violate".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          importViolate(row, userId);
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(2)).trim()).append(",\n");
        }
      } else if ("gps".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          String failCarNum = importGps(row, userId);
          if (null != failCarNum) {
            result.append(failCarNum).append(",\n");
          }
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(0)).trim()).append(",\n");
        }
      } else if ("operate".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          importOperate(row, userId);
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(2)).trim()).append(",\n");
        }
      } else if ("car".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          importCar(row, userId);
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(2)).trim()).append(",\n");
        }
      } else if ("exam".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          String failCarNum = importExam(row, userId);
          if (null != failCarNum) {
            result.append(failCarNum).append(",\n");
          }
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(0)).trim()).append(",\n");
        }
      } else if ("manage".equalsIgnoreCase(name)) {
        SysUser principal =
            (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = 0L;
        if (principal instanceof SysUser) {
          userId = principal.getUserId();
        }
        try {
          importManage(row, userId);
        } catch (Exception e) {
          result.append(getCellValue(row.getCell(2)).trim()).append(",\n");
        }
      }
    }
    in.close();
    return result.toString();
  }

  private void importInsure(Row row, long userId) throws ParseException {
    String operateNum = getCellValue(row.getCell(2)).trim();
    if (null != operateNum && !operateNum.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      String carNum = getCellValue(row.getCell(1)).trim();
      String forceInsure = getCellValue(row.getCell(3)).trim();
      String busInsure = getCellValue(row.getCell(4)).trim();
      String hasPay = getCellValue(row.getCell(5)).trim();
      String hasReceive = getCellValue(row.getCell(6)).trim();
      String outBuy = getCellValue(row.getCell(7)).trim();
      Insure insure = null;
      Car car = carService.findByOperateNum(operateNum);
      if (null != car) {
        insure = insureService.findByCarId(car.getId());
      } else {
        car = new Car();
        car.setOperateNum(operateNum);
      }
      car.setCarNum(carNum);
      car.setUpdateUser(userId);
      carService.carSave(car);

      if (null == insure) {
        insure = new Insure();
        insure.setCarId(car.getId());
      }
      if (forceInsure.isEmpty()) {
        insure.setForceInsure(null);
      } else {
        insure.setForceInsure(sdf.parse(forceInsure));
      }
      if (busInsure.isEmpty()) {
        insure.setBusInsure(null);
      } else {
        insure.setBusInsure(sdf.parse(busInsure));
      }
      if ("是".equals(hasReceive)) {
        insure.setHasReceive(0);
      } else {
        insure.setHasReceive(1);
      }
      if ("是".equals(hasPay)) {
        insure.setHasPay(0);
      } else {
        insure.setHasPay(1);
      }
      if ("是".equals(outBuy)) {
        insure.setOutBuy(1);
      } else {
        insure.setOutBuy(0);
      }
      insure.setUpdateUser(userId);
      insureService.insureSave(insure);
    }
  }

  private void importViolate(Row row, long userId) throws ParseException {
    String operateNum = getCellValue(row.getCell(2)).trim();
    if (null != operateNum && !operateNum.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      String carNum = getCellValue(row.getCell(1)).trim();
      String recordDate = getCellValue(row.getCell(3)).trim();
      String hasDeal = getCellValue(row.getCell(4)).trim();
      String remark = getCellValue(row.getCell(5)).trim();
      Car car = carService.findByOperateNum(operateNum);
      if (null == car) {
        car = new Car();
        car.setOperateNum(operateNum);
      }
      car.setCarNum(carNum);
      car.setUpdateUser(userId);
      carService.carSave(car);

      Violate violate = new Violate();
      violate.setCarId(car.getId());
      if (recordDate.isEmpty()) {
        violate.setRecordDate(null);
      } else {
        violate.setRecordDate(sdf.parse(recordDate));
      }
      if ("已缴费".equals(hasDeal)) {
        violate.setHasDeal(1);
      } else {
        violate.setHasDeal(0);
      }
      violate.setRemark(remark);
      violate.setUpdateUser(userId);
      violateService.violateSave(violate);
    }
  }

  private String importGps(Row row, long userId) throws ParseException {
    String carNum = getCellValue(row.getCell(0)).trim();
    if (null != carNum && !carNum.isEmpty()) {
      Date endDate = null;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      String endDateStr = getCellValue(row.getCell(1)).trim();

      if (null != endDateStr && !endDateStr.isEmpty()) {
        if (endDateStr.contains(".")) {
          endDateStr = endDateStr.substring(0, endDateStr.indexOf("."));
        }
        endDate = sdf.parse(endDateStr);
      }
      Gps gps = null;
      Car car = carService.findByCarNum("陕" + carNum.trim().toUpperCase());
      if (null != car) {
        gps = gpsService.findByCarId(car.getId());
        if (null == gps) {
          gps = new Gps();
          gps.setCarId(car.getId());
        }
        gps.setEndDate(endDate);
        gps.setUpdateUser(userId);
        gpsService.gpsSave(gps);
        return null;
      } else {
        return carNum;
      }
    }
    return null;
  }

  private void importOperate(Row row, long userId) throws ParseException {
    String operateNum = getCellValue(row.getCell(2)).trim();
    if (null != operateNum && !operateNum.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      String carNum = getCellValue(row.getCell(1)).trim();
      String endDate = getCellValue(row.getCell(3)).trim();
      Operate operate = null;
      Car car = carService.findByOperateNum(operateNum);
      if (null != car) {
        operate = operateService.findByCarId(car.getId());
      } else {
        car = new Car();
        car.setOperateNum(operateNum);
      }
      car.setCarNum(carNum);
      car.setUpdateUser(userId);
      carService.carSave(car);

      if (null == operate) {
        operate = new Operate();
        operate.setCarId(car.getId());
      }
      if (endDate.isEmpty()) {
        operate.setEndDate(null);
      } else {
        operate.setEndDate(sdf.parse(endDate));
      }
      operate.setUpdateUser(userId);
      operateService.operateSave(operate);
    }
  }

  private void importCar(Row row, long userId) throws ParseException {
    String operateNum = getCellValue(row.getCell(2)).trim();
    if (null != operateNum && !operateNum.isEmpty()) {
      String carNum = getCellValue(row.getCell(1)).trim();
      String ownerName = getCellValue(row.getCell(3)).trim();
      String ownerPhone = getCellValue(row.getCell(4)).trim();
      Car car = carService.findByOperateNum(operateNum);
      if (null == car) {
        car = new Car();
        car.setOperateNum(operateNum);
      }
      car.setCarNum(carNum);
      if (null != ownerName && !ownerName.isEmpty()) {
        car.setOwnerName(ownerName);
      }
      if (null != ownerPhone && !ownerPhone.isEmpty()) {
        car.setOwnerPhone(ownerPhone);
      }
      car.setUpdateUser(userId);
      carService.carSave(car);
    }
  }

  private String importExam(Row row, long userId) throws ParseException {
    String carNum = getCellValue(row.getCell(0)).trim();
    if (null != carNum && !carNum.isEmpty()) {
      Date endDate = null;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      String endDateStr = getCellValue(row.getCell(1)).trim();

      if (null != endDateStr && !endDateStr.isEmpty()) {
        if (endDateStr.contains(".")) {
          endDateStr = endDateStr.replace(".", "");
        }
        endDate = sdf.parse(endDateStr);
      }

      Exam exam = null;
      Car car = carService.findByCarNum("陕" + carNum.trim().toUpperCase());
      if (null != car) {
        exam = examService.findByCarId(car.getId());
        if (null == exam) {
          exam = new Exam();
          exam.setCarId(car.getId());
        }
        exam.setEndDate(endDate);
        exam.setUpdateUser(userId);
        examService.examSave(exam);
        return null;
      } else {
        return carNum;
      }
    }
    return null;
  }

  private void importManage(Row row, long userId) throws ParseException {
    String operateNum = getCellValue(row.getCell(2)).trim();
    if (null != operateNum && !operateNum.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
      String carNum = getCellValue(row.getCell(1)).trim();
      String endDate = getCellValue(row.getCell(3)).trim();
      Manage manage = null;
      Car car = carService.findByOperateNum(operateNum);
      if (null != car) {
        manage = manageService.findByCarId(car.getId());
      } else {
        car = new Car();
        car.setOperateNum(operateNum);
      }
      car.setCarNum(carNum);
      car.setUpdateUser(userId);
      carService.carSave(car);

      if (null == manage) {
        manage = new Manage();
        manage.setCarId(car.getId());
      }
      if (endDate.isEmpty()) {
        manage.setEndDate(null);
      } else {
        manage.setEndDate(sdf.parse(endDate));
      }
      manage.setUpdateUser(userId);
      manageService.manageSave(manage);
    }
  }

}
