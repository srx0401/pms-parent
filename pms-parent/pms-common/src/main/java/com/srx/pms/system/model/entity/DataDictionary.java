package com.srx.pms.system.model.entity;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.annotation.ExcelColumnMapping;
import com.srx.pms.common.annotation.ExcelFileMapping;
import com.srx.pms.common.model.entity.BaseEntity;
@Alias("DataDictionary")
@ExcelFileMapping(fileName="类别")
public class DataDictionary extends BaseEntity {
	private String code;
	@ExcelColumnMapping(columnName="名称",nullable=false,comment="必填",sort=1)
	private String name;
	private String sort;
	private DataType dataType;
	private String dataTypeId;
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
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public String getDataTypeId() {
		return dataTypeId;
	}
	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
	
}
