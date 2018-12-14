package com.srx.pms.module.account.model.entity;

import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.srx.pms.common.annotation.ExcelColumnMapping;
import com.srx.pms.common.annotation.ExcelFileMapping;
import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.system.model.entity.DataDictionary;
@Alias("Account")
@ExcelFileMapping(fileName="账户")
public class Account extends BaseEntity {
	@ExcelColumnMapping(columnName="账户名称",nullable=false,example="工作常用QQ",comment="必填",sort=1)
	private String name;
	@ExcelColumnMapping(columnName="账户描述",sort=7)
	private String desc;
	@ExcelColumnMapping(columnName="访问地址",example="QQ客户端",sort=3)
	private String address;
	@ExcelColumnMapping(columnName="访问用户",nullable=false,example="775766979",comment="必填",sort=4)
	private String userName;
	@ExcelColumnMapping(columnName="访问口令",sort=5)
	private String password;
	@ExcelColumnMapping(columnName="手机号码",sort=6)
	private String phoneNo;
	@ExcelColumnMapping(columnName="账户类别",nullable=false,example="C951A29A49F111E891A3A44CC82187E9",comment="必填,从[账户类别]中选择类别对应编号录入本列",sort=2)
	private String typeId;
	@ExcelColumnMapping(columnName="最近使用日期",pattern="yyyy-MM-dd",example="2018-10-11",comment="建议手动设置此列为文本格式",sort=8)
	private Date lastUseDate;
	private Integer enable = 1;
	private String privateKey;
	private String promptMsg;
	private String encryptFields;
	private DataDictionary type;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public DataDictionary getType() {
		return type;
	}
	public void setType(DataDictionary type) {
		this.type = type;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
	public Date getLastUseDate() {
		return lastUseDate;
	}
	public void setLastUseDate(Date lastUseDate) {
		this.lastUseDate = lastUseDate;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPromptMsg() {
		return promptMsg;
	}
	public void setPromptMsg(String promptMsg) {
		this.promptMsg = promptMsg;
	}
	public String getTypeId() {
		return StringUtils.isEmpty(typeId) && type != null ? type.getId() : typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public String getEncryptFields() {
		return encryptFields;
	}
	public void setEncryptFields(String encryptFields) {
		this.encryptFields = encryptFields;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
