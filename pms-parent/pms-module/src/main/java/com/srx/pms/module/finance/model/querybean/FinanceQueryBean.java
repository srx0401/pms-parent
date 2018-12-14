package com.srx.pms.module.finance.model.querybean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.module.finance.model.entity.Finance;
@Alias("FinanceQueryBean")
public class FinanceQueryBean extends BaseQueryBean<Finance> {
	private String typeId;
	private String payer;
	private String payee;
	private Date dealTimeBegin;
	private Date dealTimeEnd;
	private String payWayId;
	private String categoryId;
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public Date getDealTimeBegin() {
		return dealTimeBegin;
	}
	public void setDealTimeBegin(Date dealTimeBegin) {
		this.dealTimeBegin = dealTimeBegin;
	}
	public Date getDealTimeEnd() {
		return dealTimeEnd;
	}
	public void setDealTimeEnd(Date dealTimeEnd) {
		this.dealTimeEnd = dealTimeEnd;
	}
	public String getPayWayId() {
		return payWayId;
	}
	public void setPayWayId(String payWayId) {
		this.payWayId = payWayId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
}
