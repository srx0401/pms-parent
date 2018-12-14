package com.srx.pms.common.annotation;

@ExcelFileMapping(fileName="用户")
public class User {
	@ExcelColumnMapping(columnName="用户名",sort=1)
	private String userName;
	@ExcelColumnMapping(columnName="密码",sort=1)
	private String password;
	@ExcelColumnMapping(columnName="是否有效",sort=2)
	private Boolean valid;
	@ExcelColumnMapping(columnName="员工",sort=3)
	private Employee employee;
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password
				+ ", valid=" + valid + ", employee=" + employee + "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String userName, String password, Boolean valid,
			Employee employee) {
		super();
		this.userName = userName;
		this.password = password;
		this.valid = valid;
		this.employee = employee;
	}
}
