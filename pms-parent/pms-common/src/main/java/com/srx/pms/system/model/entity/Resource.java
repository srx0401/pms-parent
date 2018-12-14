package com.srx.pms.system.model.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
@Alias("Resource")
public class Resource extends BaseEntity {
	private DataDictionary type;
	private String typeId;
	private String code;
	private String name;
	private DataDictionary icon48;
	private String icon48Id;
	private DataDictionary icon16;
	private String icon16Id;
	private String desc;
	private String module;
	private int sort;
	private List<Permission> permissions;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public DataDictionary getType() {
		return type;
	}
	public void setType(DataDictionary type) {
		this.type = type;
	}
	public DataDictionary getIcon48() {
		return icon48;
	}
	public void setIcon48(DataDictionary icon48) {
		this.icon48 = icon48;
	}
	public DataDictionary getIcon16() {
		return icon16;
	}
	public void setIcon16(DataDictionary icon16) {
		this.icon16 = icon16;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getIcon48Id() {
		return icon48Id;
	}
	public void setIcon48Id(String icon48Id) {
		this.icon48Id = icon48Id;
	}
	public String getIcon16Id() {
		return icon16Id;
	}
	public void setIcon16Id(String icon16Id) {
		this.icon16Id = icon16Id;
	}
	
}
