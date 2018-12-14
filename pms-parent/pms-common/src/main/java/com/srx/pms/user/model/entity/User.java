package com.srx.pms.user.model.entity;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.system.model.entity.Role;
@Alias("User")
public class User extends BaseEntity{
    private String loginName;
    private String password;
    private String salt;
    private String isRoot;
    private UserDetail detail;
    private List<Role> roles;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public UserDetail getDetail() {
		return detail;
	}
	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}
}
