package com.srx.pms.system.model.entity;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
@Alias("Permission")
public class Permission extends BaseEntity {
	private String desc;
	private Role role;
	private String roleId;
	private Resource resource;
	private String resourceId;
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
}
