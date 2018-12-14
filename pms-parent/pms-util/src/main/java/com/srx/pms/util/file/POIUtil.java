package com.srx.pms.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.srx.pms.util.date.DateUtil;

public class POIUtil {
	private static FormulaEvaluator evaluator;
	public static final int CELL_TYPE_INTEGER = 6;
	private static CellStyle getColumnTopStyle(Workbook workbook) {
		CellStyle style = getStyle(workbook);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setBold(true);
		style.setFont(font);
		return style;

	}
	private static CellStyle getStyle(Workbook workbook) {
		Font font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short)10);
		font.setFontName("宋体");
		CellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
	/**
	 * 	获取小数格式(保留俩位小数)
	 * @param workbook
	 * @return
	 */
	private static CellStyle getNumberStyle(Workbook workbook) {
		CellStyle style = getStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
		return style;
	}
	/**
	 * 	获取整数格式
	 * @param workbook
	 * @return
	 */
	private static CellStyle getIntegerStyle(Workbook workbook) {
		CellStyle style = getStyle(workbook);
		style.setDataFormat(workbook.createDataFormat().getFormat("#,#0"));
		return style;
	}
	/**
	 * 获取一个cell数据转为String
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellContent(Cell cell,Workbook book) {
		Object result = null;
		// 如果此单元格为空，则返回null;
		if (cell == null) {
			return null;
		}
		CellType cellType=cell.getCellTypeEnum();
		if(cellType.equals(CellType.FORMULA)){ //表达式类型
			if (evaluator == null) {
				evaluator = book.getCreationHelper().createFormulaEvaluator();
			}
            cellType = evaluator.evaluate(cell).getCellTypeEnum();
        }
		if (cellType.equals(CellType.STRING)) {
			result = cell.getRichStringCellValue().getString().trim();
		}else if(cellType.equals(CellType.NUMERIC)){
			result = HSSFDateUtil.isCellDateFormatted(cell) ? DateUtil.DATE_FORMATER.format(cell.getDateCellValue()) : cell.getNumericCellValue();
		}else if(cellType.equals(CellType.BOOLEAN)){
			result = cell.getBooleanCellValue();
		}else{
			return null;
		}
		return result != null ? result.toString() : null;
	}
	public static List<Map<String,String>> readExcel(Workbook book,String[]columnMapping, int ignoreRows) throws IOException {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		try {
			// 获得 sheet总数
			int sheetCount = book.getNumberOfSheets();
			// 遍历sheet
			Sheet sheet = null;
			for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
				// 获得指定的sheet对象
				sheet = book.getSheetAt(sheetIndex);
				// 如果没有数据
				if (sheet.getLastRowNum() < 1) {
					continue;
				}
				Map<String,String> data = null;
				Row row = null;
				int cellCount = 0;
				for (int rowIndex = ignoreRows; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					data = new HashMap<>();
					row = sheet.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					cellCount = row.getLastCellNum() > columnMapping.length ? columnMapping.length : row.getLastCellNum();
					for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
						data.put(columnMapping[cellIndex], getCellContent(row.getCell(cellIndex),book));
					}
					res.add(data);
				}
			}
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return res;
	}
	public static List<Map<String,String>> readExcel(File file,String[]columnMapping, int ignoreRows) throws IOException {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		Workbook book = null;
		try {
			if (file.getName().endsWith(".xlsx")) {
				book = new XSSFWorkbook(new FileInputStream(file)); // excel2007
			} else {
				book = new HSSFWorkbook(new FileInputStream(file)); // excel2003
			}
			return readExcel(book, columnMapping, ignoreRows);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return res;
	}
	public static List<Map<String,String>> readExcel(MultipartFile file,String[]columnMapping, int ignoreRows) throws IOException {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		Workbook book = null;
		try {
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				book = new XSSFWorkbook(file.getInputStream()); // excel2007
			} else {
				book = new HSSFWorkbook(file.getInputStream()); // excel2003
			}
			return readExcel(book, columnMapping, ignoreRows);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return res;
	}
	
	public static List<Map<String,String>> readExcel(MultipartFile file) throws IOException {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		Workbook book = null;
		try {
			if (file.getOriginalFilename().endsWith(".xlsx")) {
				book = new XSSFWorkbook(file.getInputStream()); // excel2007
			} else {
				book = new HSSFWorkbook(file.getInputStream()); // excel2003
			}
			Sheet sheet = book.getSheetAt(0);
			// 如果没有数据
			if (sheet.getLastRowNum() < 2) {
				return res;
			}
			Map<String,String> data = null;
			Row row = null;
			Row titleRow = sheet.getRow(0);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				data = new HashMap<>();
				row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				for (int cellIndex = 0; cellIndex < titleRow.getLastCellNum(); cellIndex++) {
					data.put(getCellContent(titleRow.getCell(cellIndex),book), getCellContent(row.getCell(cellIndex),book));
				}
				res.add(data);
			}
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return res;
	}
	public static List<Map<String,String>> readExcel(File file) throws IOException {
		List<Map<String,String>> res = new ArrayList<Map<String,String>>();
		Workbook book = null;
		try {
			if (file.getName().endsWith(".xlsx")) {
				book = new XSSFWorkbook(new FileInputStream(file)); // excel2007
			} else {
				book = new HSSFWorkbook(new FileInputStream(file)); // excel2003
			}
			Sheet sheet = book.getSheetAt(0);
			// 如果没有数据
			if (sheet.getLastRowNum() < 2) {
				return res;
			}
			Map<String,String> data = null;
			Row row = null;
			Row titleRow = sheet.getRow(0);
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				data = new HashMap<>();
				row = sheet.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				for (int cellIndex = 0; cellIndex < titleRow.getLastCellNum(); cellIndex++) {
					data.put(getCellContent(titleRow.getCell(cellIndex),book), getCellContent(row.getCell(cellIndex),book));
				}
				res.add(data);
			}
		} finally {
			if (book != null) {
				book.close();
			}
		}
		return res;
	}
	/**
	 * 	导出(单sheet,支持合并)
	 * @param es			excelSheet对象
	 * @throws IOException
	 */
	public static Workbook writeExcel(ExcelSheet excelSheet) throws IOException {
		return writeExcel(new ExcelSheet[]{excelSheet});
	}
	/**
	 * 导出(支持多sheet,支持合并)
	 * @param sheets	sheet集合
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static Workbook writeExcel(ExcelSheet[] sheets) throws IOException {
		if (sheets == null || sheets.length < 1) {
			return null;
		}
		SXSSFWorkbook  workbook = new SXSSFWorkbook(1000);
		CellStyle titleStyle = getColumnTopStyle(workbook);
		CellStyle contentStyle = getStyle(workbook);
		CellStyle numberStyle = getNumberStyle(workbook);
		CellStyle integerStyle = getIntegerStyle(workbook);
		Cell cell = null;
		Object cellValue = null;
		for(ExcelSheet sh : sheets){
			SXSSFSheet sheet = workbook.createSheet(sh.getName());
			SXSSFDrawing  draw = sheet.createDrawingPatriarch();
			Row row = sheet.createRow(0);
			for (int i = 0; i < sh.getColumns().length; i++) {
				cell = row.createCell((int) i);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(sh.getColumns()[i].getName());
				cell.setCellStyle(titleStyle);
				if (sh.getColumns()[i].getComment() != null && sh.getColumns()[i].getComment().length() > 0) {
					ClientAnchor anchor = new XSSFClientAnchor(0,0,0,0,i,0,i+1,0);
					RichTextString commentString = new XSSFRichTextString(sh.getColumns()[i].getComment());
					Comment comment = draw.createCellComment(anchor);
					comment.setString(commentString );
					cell.setCellComment(comment);
				}
			}
			
			if (sh.getData() == null || sh.getData().size() < 1) {
				row = sheet.createRow(1);
				for (int j = 0; j < sh.getColumns().length; j++) {
					cell = null;
					cellValue = null;
					if (sh.getColumns()[j] == null) {
						continue;
					}
					cellValue = sh.getColumns()[j].getExampleValue();
					if (sh.getColumns()[j].getType() != null) {
						//	指定格式
						if(sh.getColumns()[j].getType().equals(CellType.NUMERIC) || sh.getColumns()[j].getType().getCode()== CELL_TYPE_INTEGER){
							cell = row.createCell(j,CellType.NUMERIC);
							if (sh.getColumns()[j].getType().equals(CellType.NUMERIC)) {
								cell.setCellStyle(numberStyle);
								cell.setCellValue(Double.parseDouble(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "0"));
							}else if(sh.getColumns()[j].getType().getCode()== CELL_TYPE_INTEGER){
								cell.setCellStyle(integerStyle);
								cell.setCellValue(Integer.parseInt(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "0"));
							}
						}
					}
					if (cell == null) {
						cell = row.createCell(j,CellType.STRING);
						cell.setCellStyle(contentStyle);
						cell.setCellValue(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "");
					}
					
				}
			}
			for (int i = 0; sh.getData() != null && i < sh.getData().size(); i++) {
				row = sheet.createRow(i + 1);
				for (int j = 0; j < sh.getColumns().length; j++) {
					cell = null;
					cellValue = null;
					if (sh.getColumns()[j] == null) {
						continue;
					}
					cellValue = sh.getData().get(i).get(sh.getColumns()[j].getName());
					if (sh.getColumns()[j].getType() != null) {
						//	指定格式
						if(sh.getColumns()[j].getType().equals(CellType.NUMERIC) || sh.getColumns()[j].getType().getCode()== CELL_TYPE_INTEGER){
							cell = row.createCell(j,CellType.NUMERIC);
							if (sh.getColumns()[j].getType().equals(CellType.NUMERIC)) {
								cell.setCellStyle(numberStyle);
								cell.setCellValue(Double.parseDouble(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "0"));
							}else if(sh.getColumns()[j].getType().getCode()== CELL_TYPE_INTEGER){
								cell.setCellStyle(integerStyle);
								cell.setCellValue(Integer.parseInt(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "0"));
							}
						}
					}
					if (cell == null) {
						cell = row.createCell(j,CellType.STRING);
						cell.setCellStyle(contentStyle);
						cell.setCellValue(cellValue != null && cellValue.toString().length() > 0 ? cellValue.toString() : "");
					}
					
				}
			}
			
			if (sh.getCellRangeAddress() != null && sh.getCellRangeAddress().length > 0) {
				for (int i = 0; i < sh.getCellRangeAddress().length; i++) {
					sheet.addMergedRegion(sh.getCellRangeAddress()[i]);
				}
			}
			sheet.trackAllColumnsForAutoSizing();
			for (int i = 0; i < sh.getColumns().length; i++) {
				sheet.autoSizeColumn(i);
			}
		}
		return workbook;
	}
	public static void writeExcel(String fileName,ExcelSheet[] sheets,HttpServletRequest req,HttpServletResponse resp) throws IOException {
		Workbook book = writeExcel(sheets);
		if (book != null) {
			String headStr = "attachment; filename=\"" + convertFileNameByExplore(req,fileName) + ".xlsx\"";
			resp.setContentType("APPLICATION/OCTET-STREAM");
			resp.setHeader("Content-Disposition", headStr);
			OutputStream out = resp.getOutputStream();
			book.write(out);
			out.flush();
			out.close();
		}
	}
	/**
	 * 	判断浏览器,转换文件名
	 * @param req
	 * @param fileName
	 * @return
	 */
	public static String convertFileNameByExplore(HttpServletRequest req,String fileName){
		if (req == null || fileName == null || fileName.length() < 1) {
			return fileName;
		}
		final String userAgent = req.getHeader("USER-AGENT");
		try {
			if (userAgent == null || userAgent.length() < 1) {
				return fileName;
			}
			if(userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0){//google,火狐浏览器
				return new String(fileName.getBytes(), "ISO8859-1");
			}else{
				return URLEncoder.encode(fileName,"UTF8");//其他浏览器
			}
		} catch (Exception e) {
		}
		return fileName;
	}
	
	public static void main(String[] args) throws IOException {
//		testRead();
		testWrite();
//		testWriteMultiSheet();
	}
	public static void testRead() throws IOException{
		File accountFile = new File("C:/Users/Simon/Desktop/账户清单.xlsx");
		List<Map<String,String>> list = readExcel(accountFile);
		for (Map<String,String> row : list) {
			System.out.println(row);
		}
	}
	@SuppressWarnings({ "serial" })
	public static void testWrite() throws IOException{
		File file = new File("C:/Users/Simon/Desktop/测试Excel写入.xlsx");
		 List<Map<String,String>> dataList = new ArrayList<Map<String,String>>(){{
			 add(new HashMap<String,String>(){{
				 put("code","001");
				 put("name", "零零壹");
//				 put("remark", "简单来说就是第一个喽.");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","002");
				 put("name", "零零贰");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","003");
				 put("name", "零零叁");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","000000003");
				 put("name", "零零零零零零零零零零叁");
				 put("remark", "简单来说就是第3个喽.");
			 }});
		 }};
		Workbook book = writeExcel(new ExcelSheet("Sheet1",new ExcelTitleCell[]{new ExcelTitleCell("code","编码","必填,一般为数字/字母/下划线组合"),new ExcelTitleCell("name","名称","必填,一般为中文描述"),new ExcelTitleCell("remark","备注","非必填")},dataList));
		book.write(new FileOutputStream(file));
		book.close();
	}
	@SuppressWarnings("serial")
	public static void testWriteMultiSheet() throws IOException{
		File file = new File("C:/Users/Simon/Desktop/测试Excel写入多sheet.xlsx");
		List<Map<String,String>> sheet1Data = new ArrayList<Map<String,String>>(){{
			 add(new HashMap<String,String>(){{
				 put("账户名称","雪花UAT-戴松延");
				 put("账户类别", "848D9077597011E891A3A44CC82187E9");
				 put("访问地址", "http://fsscuat.crb.cn:8081/ReimbursePlatform");
				 put("访问用户", "DAISONGYAN");
				 put("访问口令", "snowuat");
			 }});
			 add(new HashMap<String,String>(){{
				 put("账户名称","工作常用QQ");
				 put("账户类别", "C951A29A49F111E891A3A44CC82187E9");
				 put("访问地址", "QQ客户端");
				 put("访问用户", "775766979");
//				 put("访问口令", "");
			 }});
		}};
		List<Map<String,String>> sheet2Data = new ArrayList<Map<String,String>>(){{
			 add(new HashMap<String,String>(){{
				 put("code","001");
				 put("name", "零零壹");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","002");
				 put("name", "零零贰");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","003");
				 put("name", "零零叁");
			 }});
			 add(new HashMap<String,String>(){{
				 put("code","000000003");
				 put("name", "零零零零零零零零零零叁");
			 }});
		}};
		ExcelSheet sh1 = new ExcelSheet();
		sh1.setName("账户清单");
		sh1.setColumns(new ExcelTitleCell[]{new ExcelTitleCell("账户名称","账户名称",CellType.STRING),new ExcelTitleCell("账户类别","账户类别",CellType.STRING),new ExcelTitleCell("访问地址","访问地址",CellType.STRING),new ExcelTitleCell("访问用户","访问用户",CellType.STRING),new ExcelTitleCell("访问口令","访问口令",CellType.STRING)});
		sh1.setData(sheet1Data);
		ExcelSheet sh2 = new ExcelSheet();
		sh2.setName("账户类别");
		sh2.setColumns(new ExcelTitleCell[]{new ExcelTitleCell("code","编码",CellType.STRING),new ExcelTitleCell("name","名称",CellType.STRING)});
		sh2.setData(sheet2Data);
		
		ExcelSheet [] sheets = {sh1,sh2};
		Workbook book = writeExcel(sheets);
		book.write(new FileOutputStream(file));
		book.close();
	}
}
