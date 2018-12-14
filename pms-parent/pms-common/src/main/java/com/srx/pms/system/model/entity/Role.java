package com.srx.pms.system.model.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
@Alias("Role")
public class Role extends BaseEntity {
	private String name;
	private String desc;
	private List<Permission> permissions;
	private List<Resource> resources;
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
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
