package com.srx.pms.user.model.querybean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.user.model.entity.UserDetail;
@Alias("UserDetailQueryBean")
public class UserDetailQueryBean extends BaseQueryBean<UserDetail> {
	private String userId;
	private String zhName;
	private String enName;
	private String phone;
	private String address;
	private Integer sex;
    private Date birthBegin;
    private Date birthEnd;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getZhName() {
		return zhName;
	}

	public void setZhName(String zhName) {
		this.zhName = zhName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthBegin() {
		return birthBegin;
	}

	public void setBirthBegin(Date birthBegin) {
		this.birthBegin = birthBegin;
	}

	public Date getBirthEnd() {
		return birthEnd;
	}

	public void setBirthEnd(Date birthEnd) {
		this.birthEnd = birthEnd;
	}
	
}
