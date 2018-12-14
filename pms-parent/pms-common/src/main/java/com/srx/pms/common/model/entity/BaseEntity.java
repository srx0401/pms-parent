package com.srx.pms.common.model.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.srx.pms.common.annotation.ExcelColumnMapping;

public class BaseEntity {
	@ExcelColumnMapping(columnName="编号")
	protected String id;
//	@ExcelColumnMapping(columnName="是否有效",defaultValue="1")
	protected Integer valid;
	@ExcelColumnMapping(columnName="备注",sort=99,defaultValue="导入")
	protected String remark;
	protected Date createTime;
	protected String createUserId;
	protected Date updateTime;
	protected String updateUserId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") 
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
}
