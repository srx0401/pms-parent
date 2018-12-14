package com.srx.pms.system.model.entity;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.user.model.entity.User;
@Alias("UserRole")
public class UserRole extends BaseEntity {
	private User user;
	private String userId;
	private Role role;
	private String roleId;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
