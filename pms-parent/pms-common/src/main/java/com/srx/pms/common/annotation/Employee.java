package com.srx.pms.common.annotation;

import java.math.BigDecimal;
import java.util.Date;
public class Employee {
	@ExcelColumnMapping(columnName="工号",sort=1)
	private String employeeNO;
	@ExcelColumnMapping(columnName="姓名",sort=0)
	private String employeeName;
	@ExcelColumnMapping(columnName="性别",sort=2)
	private Integer gender;
	@ExcelColumnMapping(columnName="生日",pattern="yyyy-MM-dd",sort=4)
	private Date birthday;
	@ExcelColumnMapping(columnName="工资",sort=99)
	private BigDecimal salary;
	@ExcelColumnMapping(columnName="年龄",sort=3)
	private int age;
	@Override
	public String toString() {
		return "Employee [employeeNO=" + employeeNO + ", employeeName="
				+ employeeName + ", gender=" + gender + ", birthday="
				+ birthday + ", salary=" + salary + ", age=" + age + "]";
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEmployeeNO() {
		return employeeNO;
	}
	public void setEmployeeNO(String employeeNO) {
		this.employeeNO = employeeNO;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public BigDecimal getSalary() {
		return salary;
	}
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Employee(String employeeNO, String employeeName, Integer gender,
			Date birthday, BigDecimal salary, int age) {
		super();
		this.employeeNO = employeeNO;
		this.employeeName = employeeName;
		this.gender = gender;
		this.birthday = birthday;
		this.salary = salary;
		this.age = age;
	}
}
