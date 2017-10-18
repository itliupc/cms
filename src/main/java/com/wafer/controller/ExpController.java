package com.wafer.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafer.domain.Car;
import com.wafer.domain.Exam;
import com.wafer.domain.Gps;
import com.wafer.domain.Insure;
import com.wafer.domain.Manage;
import com.wafer.domain.Operate;
import com.wafer.domain.Violate;
import com.wafer.service.CarService;
import com.wafer.service.ExamService;
import com.wafer.service.GpsService;
import com.wafer.service.InsureService;
import com.wafer.service.ManageService;
import com.wafer.service.OperateService;
import com.wafer.service.ViolateService;
import com.wafer.utils.DateUtils;


@Controller
@RequestMapping("/exp-manage")
@Transactional
public class ExpController {

  @Autowired
  CarService carService;

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

  Logger logger = LoggerFactory.getLogger(ExpController.class);

  @RequestMapping("/export/{name}")
  public void exportExcel(@PathVariable("name") String name, @RequestParam("param1") String param1,
      @RequestParam("param2") String param2, @RequestParam("param3") String param3,
      HttpServletRequest request, HttpServletResponse response) {
    Map<String, String> map = new HashMap<String, String>();
    map.put("page", String.valueOf(1));
    map.put("rows", String.valueOf(1000000));
    // 生成提示信息，
    response.setContentType("application/vnd.ms-excel");
    String codedFileName = null;
    OutputStream fOut = null;
    String agent = request.getHeader("USER-AGENT").toLowerCase();
    String fileCode = "UTF-8";
    if (agent.indexOf("firefox") > -1) {
      fileCode = "ISO8859-1";
    }
    try {
      if ("insure".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("保险清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("保险清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("deadline", param3);
      } else if ("violate".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("违章清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("违章清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("hasDeal", param3);
      } else if ("gps".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("GPS清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("GPS清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("deadline", param3);
      } else if ("operate".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("营运清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("营运清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("deadline", param3);
      } else if ("exam".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("审车清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("审车清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("deadline", param3);
      } else if ("manage".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("管理费清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("管理费清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
        map.put("deadline", param3);
      } else if ("car".equalsIgnoreCase(name)) {
        // 进行转码，使其支持中文文件名
        if (agent.indexOf("firefox") > -1) {
          codedFileName = new String("车辆清单".getBytes(), fileCode);
        } else {
          codedFileName = java.net.URLEncoder.encode("车辆清单", fileCode);
        }
        map.put("carNum", param1);
        map.put("operateNum", param2);
      }
      response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
      // 产生工作簿对象
      HSSFWorkbook workbook = new HSSFWorkbook();
      if ("insure".equalsIgnoreCase(name)) {
        createInsureSheet(workbook, map);
      } else if ("violate".equalsIgnoreCase(name)) {
        createViolateSheet(workbook, map);
      } else if ("gps".equalsIgnoreCase(name)) {
        createGpsSheet(workbook, map);
      } else if ("operate".equalsIgnoreCase(name)) {
        createOperateSheet(workbook, map);
      } else if ("exam".equalsIgnoreCase(name)) {
        createExamSheet(workbook, map);
      } else if ("manage".equalsIgnoreCase(name)) {
        createManageSheet(workbook, map);
      } else if ("car".equalsIgnoreCase(name)) {
        createCarSheet(workbook, map);
      }

      fOut = response.getOutputStream();
      workbook.write(fOut);
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    } catch (Exception e2) {
      e2.printStackTrace();
    } finally {
      try {
        fOut.flush();
        fOut.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void createInsureSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    sheet.setColumnWidth(6, 20 * 256);
    sheet.setColumnWidth(7, 20 * 256);
    sheet.setColumnWidth(8, 20 * 256);
    sheet.setColumnWidth(9, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("交强止期");

    cell = head.createCell((int) 6);
    cell.setCellStyle(cs);
    cell.setCellValue("商业止期");

    cell = head.createCell((int) 7);
    cell.setCellStyle(cs);
    cell.setCellValue("未缴费");

    cell = head.createCell((int) 8);
    cell.setCellStyle(cs);
    cell.setCellValue("未领取");

    cell = head.createCell((int) 9);
    cell.setCellStyle(cs);
    cell.setCellValue("外购");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);
    CellStyle yellowCell = this.createStyle(workbook, HSSFColor.YELLOW.index);
    CellStyle blueCell = this.createStyle(workbook, HSSFColor.LIGHT_BLUE.index);
    CellStyle greenCell = this.createStyle(workbook, HSSFColor.GREEN.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Insure> insures = insureService.getInsureList(map).getContent();
    for (int i = 1; i <= insures.size(); i++) {
      Insure insure = insures.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(insure.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(insure.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(insure.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(insure.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != insure.getForceInsure()) {
        if (insure.getForceInsure().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(insure.getForceInsure())
            && insure.getForceInsure().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(insure.getForceInsure()));
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 6);
      if (null != insure.getBusInsure()) {
        if (insure.getBusInsure().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(insure.getBusInsure())
            && insure.getBusInsure().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(insure.getBusInsure()));
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 7);
      if (0 == insure.getHasPay()) {
        cell.setCellStyle(yellowCell);
        cell.setCellValue("是");
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 8);
      if (0 == insure.getHasReceive()) {
        cell.setCellStyle(blueCell);
        cell.setCellValue("是");
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 9);
      if (1 == insure.getOutBuy()) {
        cell.setCellStyle(greenCell);
        cell.setCellValue("是");
      } else {
        cell.setCellValue("");
      }

    }
  }

  private void createViolateSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    sheet.setColumnWidth(6, 20 * 256);
    sheet.setColumnWidth(7, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("违章日期");

    cell = head.createCell((int) 6);
    cell.setCellStyle(cs);
    cell.setCellValue("缴费情况");

    cell = head.createCell((int) 7);
    cell.setCellStyle(cs);
    cell.setCellValue("备注");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Violate> violates = violateService.getViolateList(map).getContent();
    for (int i = 1; i <= violates.size(); i++) {
      Violate violate = violates.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(violate.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(violate.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(violate.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(violate.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != violate.getRecordDate()) {
        if (violate.getRecordDate().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(violate.getRecordDate())
            && violate.getRecordDate().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(violate.getRecordDate()));
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 6);
      if (0 == violate.getHasDeal()) {
        cell.setCellValue("");
      } else {
        cell.setCellValue("已缴费");
      }

      cell = row.createCell((int) 7);
      if (null == violate.getRemark()) {
        cell.setCellValue("");
      } else {
        cell.setCellValue(violate.getRemark());
      }
    }
  }

  private void createGpsSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("GPS止期");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Gps> gpsList = gpsService.getGpsList(map).getContent();
    for (int i = 1; i <= gpsList.size(); i++) {
      Gps gps = gpsList.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(gps.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(gps.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(gps.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(gps.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != gps.getEndDate()) {
        if (gps.getEndDate().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(gps.getEndDate())
            && gps.getEndDate().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(gps.getEndDate()));
      } else {
        cell.setCellValue("");
      }

    }
  }

  private void createOperateSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("营运证止期");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Operate> operateList = operateService.getOperateList(map).getContent();
    for (int i = 1; i <= operateList.size(); i++) {
      Operate operate = operateList.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(operate.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(operate.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(operate.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(operate.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != operate.getEndDate()) {
        if (operate.getEndDate().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(operate.getEndDate())
            && operate.getEndDate().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(operate.getEndDate()));
      } else {
        cell.setCellValue("");
      }

    }
  }

  private void createExamSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("年审日期");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Exam> examList = examService.getExamList(map).getContent();
    for (int i = 1; i <= examList.size(); i++) {
      Exam exam = examList.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(exam.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(exam.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(exam.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(exam.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != exam.getEndDate()) {
        if (exam.getEndDate().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(exam.getEndDate())
            && exam.getEndDate().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(exam.getEndDate()));
      } else {
        cell.setCellValue("");
      }

    }
  }

  private void createManageSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    sheet.setColumnWidth(5, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");
    
    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");

    cell = head.createCell((int) 5);
    cell.setCellStyle(cs);
    cell.setCellValue("管理费止期");

    CellStyle greyCell = this.createStyle(workbook, HSSFColor.GREY_40_PERCENT.index);
    CellStyle redCell = this.createStyle(workbook, HSSFColor.RED.index);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<Manage> manageList = manageService.getManageList(map).getContent();
    for (int i = 1; i <= manageList.size(); i++) {
      Manage manage = manageList.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(manage.getCar().getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(manage.getCar().getOperateNum());
      
      cell = row.createCell((int) 3);
      cell.setCellValue(manage.getCar().getOwnerName());

      cell = row.createCell((int) 4);
      cell.setCellValue(manage.getCar().getOwnerPhone());

      cell = row.createCell((int) 5);
      if (null != manage.getEndDate()) {
        if (manage.getEndDate().before(DateUtils.formatSqlDate(0))) {
          cell.setCellStyle(greyCell);
        } else if (!DateUtils.formatSqlDate(0).after(manage.getEndDate())
            && manage.getEndDate().before(DateUtils.formatSqlDate(2))) {
          cell.setCellStyle(redCell);
        }
        cell.setCellValue(sdf.format(manage.getEndDate()));
      } else {
        cell.setCellValue("");
      }

    }
  }

  private void createCarSheet(HSSFWorkbook workbook, Map<String, String> map) {
    HSSFSheet sheet = workbook.createSheet();
    sheet.setColumnWidth(0, 20 * 256);
    sheet.setColumnWidth(1, 20 * 256);
    sheet.setColumnWidth(2, 20 * 256);
    sheet.setColumnWidth(3, 20 * 256);
    sheet.setColumnWidth(4, 20 * 256);
    // 加粗
    HSSFFont font = workbook.createFont();
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    font.setFontHeightInPoints((short) 12);
    CellStyle cs = workbook.createCellStyle();
    cs.setFont(font);

    CellStyle contentStyle = workbook.createCellStyle();
    contentStyle.setAlignment(CellStyle.ALIGN_LEFT);

    HSSFRow head = sheet.createRow((int) 0);
    HSSFCell cell = head.createCell((int) 0);
    cell.setCellStyle(cs);
    cell.setCellValue("序号");

    cell = head.createCell((int) 1);
    cell.setCellStyle(cs);
    cell.setCellValue("车号");

    cell = head.createCell((int) 2);
    cell.setCellStyle(cs);
    cell.setCellValue("建运号");

    cell = head.createCell((int) 3);
    cell.setCellStyle(cs);
    cell.setCellValue("车主姓名");

    cell = head.createCell((int) 4);
    cell.setCellStyle(cs);
    cell.setCellValue("联系方式");


    List<Car> carList = carService.getCarList(map).getContent();
    for (int i = 1; i <= carList.size(); i++) {
      Car car = carList.get(i - 1);
      HSSFRow row = sheet.createRow((int) i);
      cell = row.createCell((int) 0);
      cell.setCellStyle(contentStyle);
      cell.setCellValue(i);

      cell = row.createCell((int) 1);
      cell.setCellValue(car.getCarNum());

      cell = row.createCell((int) 2);
      cell.setCellValue(car.getOperateNum());

      cell = row.createCell((int) 3);
      if (null != car.getOwnerName()) {
        cell.setCellValue(car.getOwnerName());
      } else {
        cell.setCellValue("");
      }

      cell = row.createCell((int) 4);
      if (null != car.getOwnerPhone()) {
        cell.setCellValue(car.getOwnerPhone());
      } else {
        cell.setCellValue("");
      }

    }
  }

  private CellStyle createStyle(HSSFWorkbook workbook, short index) {
    CellStyle cs = workbook.createCellStyle();
    cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
    cs.setFillForegroundColor(index);
    return cs;
  }
}
