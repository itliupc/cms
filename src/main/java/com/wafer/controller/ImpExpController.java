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
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafer.domain.Insure;
import com.wafer.service.InsureService;

@Controller
@RequestMapping("/impexp-manage")
@Transactional
public class ImpExpController {

	@Autowired
	InsureService insureService;

	Logger logger = LoggerFactory.getLogger(ImpExpController.class);

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
		try {
			if("insure".equalsIgnoreCase(name)){
				// 进行转码，使其支持中文文件名
				codedFileName = java.net.URLEncoder.encode("保险清单", "UTF-8");
				map.put("carNum", param1);
				map.put("operateNum", param2);
				map.put("deadline", param3);
			}
			response.setHeader("content-disposition", "attachment;filename="
					+ codedFileName + ".xls");
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			createInsureSheet(workbook, map);
			
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

	private void createInsureSheet(HSSFWorkbook workbook,
			Map<String, String> map) {
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 20 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(7, 20 * 256);
		//加粗
		HSSFFont font = workbook.createFont();  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		font.setFontHeightInPoints((short) 14);
		CellStyle cs = workbook.createCellStyle();
		cs.setFont(font); 
		
		CellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setAlignment(CellStyle.ALIGN_LEFT);
		
		HSSFRow head = sheet.createRow((int) 0);// 创建一行
		HSSFCell cell = head.createCell((int) 0);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("序号");
		
		cell = head.createCell((int) 1);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("车号");
		
		cell = head.createCell((int) 2);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("建运号");
		
		cell = head.createCell((int) 3);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("交强止期");
		
		cell = head.createCell((int) 4);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("商业止期");
		
		cell = head.createCell((int) 5);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("未缴费");
		
		cell = head.createCell((int) 6);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("未领取");
		
		cell = head.createCell((int) 7);// 创建一列
		cell.setCellStyle(cs);
		cell.setCellValue("外购");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		List<Insure> insures = insureService.getInsureList(map).getContent();
		for (int i = 1; i <= insures.size(); i++) {
			Insure insure = insures.get(i-1);
			HSSFRow row = sheet.createRow((int) i);// 创建一行
			cell = row.createCell((int) 0);// 创建一列
			cell.setCellStyle(contentStyle);
			cell.setCellValue(i);
			
			cell = row.createCell((int) 1);// 创建一列
			cell.setCellValue(insure.getCarNum());
			
			cell = row.createCell((int) 2);// 创建一列
			cell.setCellValue(insure.getOperateNum());
			
			cell = row.createCell((int) 3);// 创建一列
			if(null != insure.getForceInsure()){
				cell.setCellValue(sdf.format(insure.getForceInsure()));
			}else{
				cell.setCellValue("");
			}
			
			cell = row.createCell((int) 4);// 创建一列
			if(null != insure.getBusInsure()){
				cell.setCellValue(sdf.format(insure.getBusInsure()));
			}else{
				cell.setCellValue("");
			}
			
			cell = row.createCell((int) 5);// 创建一列
			if(0 == insure.getHasPay()){
				cell.setCellValue("是");
			}else{
				cell.setCellValue("");
			}
			
			cell = row.createCell((int) 6);// 创建一列
			if(0 == insure.getHasReceive()){
				cell.setCellValue("是");
			}else{
				cell.setCellValue("");
			}
			
			cell = row.createCell((int) 7);// 创建一列
			if(1 == insure.getOutBuy()){
				cell.setCellValue("是");
			}else{
				cell.setCellValue("");
			}
			
		}
	}

}
