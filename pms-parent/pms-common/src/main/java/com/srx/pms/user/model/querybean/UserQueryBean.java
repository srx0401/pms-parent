package com.srx.pms.user.model.querybean;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.user.model.entity.User;
@Alias("UserQueryBean")
public class UserQueryBean extends BaseQueryBean<User>{
	private String loginName;
	private String password;
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
	public UserQueryBean(String loginName, String password) {
		super();
		this.loginName = loginName;
		this.password = password;
	}
	public UserQueryBean() {
		super();
	}
}
