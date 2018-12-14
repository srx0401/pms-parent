package com.srx.pms.util.file;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelSheet {
	private String name;
	private ExcelTitleCell[] columns;
	private CellRangeAddress [] cellRangeAddress;
	private List<Map<String,String>> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Map<String, String>> getData() {
		return data;
	}
	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
	public ExcelTitleCell[] getColumns() {
		return columns;
	}
	public void setColumns(ExcelTitleCell[] columns) {
		this.columns = columns;
	}
	public CellRangeAddress[] getCellRangeAddress() {
		return cellRangeAddress;
	}
	public void setCellRangeAddress(CellRangeAddress[] cellRangeAddress) {
		this.cellRangeAddress = cellRangeAddress;
	}
	public ExcelSheet(String name, ExcelTitleCell[] columns,
			CellRangeAddress[] cellRangeAddress, List<Map<String, String>> data) {
		super();
		this.name = name;
		this.columns = columns;
		this.cellRangeAddress = cellRangeAddress;
		this.data = data;
	}
	public ExcelSheet(String name, ExcelTitleCell[] columns,
			List<Map<String, String>> data) {
		super();
		this.name = name;
		this.columns = columns;
		this.data = data;
	}
	
	public ExcelSheet(String name, ExcelTitleCell[] columns) {
		super();
		this.name = name;
		this.columns = columns;
	}
	public ExcelSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

}
