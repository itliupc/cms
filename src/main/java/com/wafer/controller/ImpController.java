package com.wafer.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wafer.domain.Car;
import com.wafer.domain.Insure;
import com.wafer.security.domain.SysUser;
import com.wafer.service.CarService;
import com.wafer.service.InsureService;

@Controller
@RequestMapping("/imp-manage")
@Transactional
public class ImpController {

	private final static String excel2003L = ".xls"; // 2003- 版本的excel
	private final static String excel2007U = ".xlsx"; // 2007+ 版本的excel

	@Autowired
	InsureService insureService;
	
	@Autowired
    CarService carService;

	Logger logger = LoggerFactory.getLogger(ImpController.class);

	/**
	 * 描述：根据文件后缀，自适应上传文件的版本
	 * 
	 * @param inStr
	 *            ,fileName
	 * @return
	 * @throws Exception
	 */
	private Workbook getWorkbook(InputStream inStr, String fileName)
			throws Exception {
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd"); // 日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00"); // 格式化数字
		if (null == cell) {
			return value;
		}

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle()
					.getDataFormatString())) {
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

	@RequestMapping(value = "/import/{name}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void exportExcel(@PathVariable("name") String name,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile file = multipartRequest.getFile("upfile");
		if (file.isEmpty()) {
			throw new Exception("文件不存在！");
		}
		InputStream in = file.getInputStream();
		Workbook work = this.getWorkbook(in, file.getOriginalFilename());
		if (null == work)
			return;
		Sheet sheet = work.getSheetAt(0);
		if (null == sheet)
			return;
		Row row = null;
		// 遍历当前sheet中的所有行
		for (int i = sheet.getLastRowNum(); i >= 1; i--) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			if ("insure".equalsIgnoreCase(name)) {
				SysUser principal = (SysUser) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				long userId = 0L;
				if (principal instanceof SysUser) {
					userId = principal.getUserId();
				}
				importInsure(row, userId);
			}
		}
		in.close();
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
		    if(null != car){
		      insure = insureService.findByCarId(car.getId());
		      car.setCarNum(carNum);
		    } else {
		      car = new Car();
		      car.setOperateNum(operateNum);
		      car.setCarNum(carNum);
		    }
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
}