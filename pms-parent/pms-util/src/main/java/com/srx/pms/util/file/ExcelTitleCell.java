package com.srx.pms.util.file;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.CellType;

public class ExcelTitleCell implements Comparable<ExcelTitleCell>{
	private String code;
	private String name;
	private String comment;
	private BigDecimal sort;
	private String exampleValue;
	private CellType type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public BigDecimal getSort() {
		return sort;
	}
	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}
	public String getExampleValue() {
		return exampleValue;
	}
	public void setExampleValue(String exampleValue) {
		this.exampleValue = exampleValue;
	}
	public CellType getType() {
		return type;
	}
	public void setType(CellType type) {
		this.type = type;
	}
	public ExcelTitleCell() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExcelTitleCell(String code, String name, CellType type) {
		super();
		this.code = code;
		this.name = name;
		this.type = type;
	}
	public ExcelTitleCell(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	public ExcelTitleCell(String code, String name, String comment,
			CellType type) {
		super();
		this.code = code;
		this.name = name;
		this.comment = comment;
		this.type = type;
	}
	public ExcelTitleCell(String code, String name, String comment) {
		super();
		this.code = code;
		this.name = name;
		this.comment = comment;
	}
	public ExcelTitleCell(String code, String name, String comment, BigDecimal sort,
			String exampleValue, CellType type) {
		super();
		this.code = code;
		this.name = name;
		this.comment = comment;
		this.sort = sort;
		this.exampleValue = exampleValue;
		this.type = type;
	}
	public ExcelTitleCell(String code, String name, String comment, BigDecimal sort,
			String exampleValue) {
		super();
		this.code = code;
		this.name = name;
		this.comment = comment;
		this.sort = sort;
		this.exampleValue = exampleValue;
	}
	
	@Override
	public String toString() {
		return "ExcelTitleCell [code=" + code + ", name=" + name + ", comment="
				+ comment + ", sort=" + sort + ", exampleValue=" + exampleValue
				+ ", type=" + type + "]";
	}
	@Override
	public int compareTo(ExcelTitleCell o) {
		return sort.compareTo(o.getSort());
	}
	
}
