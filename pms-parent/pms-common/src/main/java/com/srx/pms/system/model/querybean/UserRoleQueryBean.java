package com.srx.pms.system.model.querybean;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.system.model.entity.UserRole;
@Alias("UserRoleQueryBean")
public class UserRoleQueryBean extends BaseQueryBean<UserRole> {
	private String roleName;
	private String userName;
	private String userPhone;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
}
