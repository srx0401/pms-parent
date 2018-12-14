package com.srx.pms.common.component.file.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelSheet {
	private String name;
	private ExcelTitleCell[] titleCells;
	private CellRangeAddress [] cellRangeAddress;
	private List<Map<String,String>> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ExcelTitleCell[] getTitleCells() {
		return titleCells;
	}
	public void setTitleCells(ExcelTitleCell[] titleCells) {
		this.titleCells = titleCells;
	}
	public CellRangeAddress[] getCellRangeAddress() {
		return cellRangeAddress;
	}
	public void setCellRangeAddress(CellRangeAddress[] cellRangeAddress) {
		this.cellRangeAddress = cellRangeAddress;
	}
	public List<Map<String, String>> getData() {
		return data;
	}
	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}
	public ExcelSheet(String name, ExcelTitleCell[] titleCells,
			CellRangeAddress[] cellRangeAddress, List<Map<String, String>> data) {
		super();
		this.name = name;
		this.titleCells = titleCells;
		this.cellRangeAddress = cellRangeAddress;
		this.data = data;
	}
	public ExcelSheet(String name, ExcelTitleCell[] titleCells,
			List<Map<String, String>> data) {
		super();
		this.name = name;
		this.titleCells = titleCells;
		this.data = data;
	}
	
	public ExcelSheet(String name, ExcelTitleCell[] titleCells) {
		super();
		this.name = name;
		this.titleCells = titleCells;
	}
	public ExcelSheet() {
		super();
		// TODO Auto-generated constructor stub
	}
}
