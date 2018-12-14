package com.srx.pms.system.model.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
@Alias("DataType")
public class DataType extends BaseEntity {
	private String code;
	private String name;
	private int sort;
	private List<DataDictionary> dictionarys;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<DataDictionary> getDictionarys() {
		return dictionarys;
	}
	public void setDictionarys(List<DataDictionary> dictionarys) {
		this.dictionarys = dictionarys;
	}
	
}
