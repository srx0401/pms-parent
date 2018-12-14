package com.srx.pms.module.finance.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.system.model.entity.DataDictionary;
@Alias("Finance")
public class Finance extends BaseEntity {
	private DataDictionary type;
	private BigDecimal amount;
	private String payer;
	private String payee;
	private Date dealTime;
	private String dealLocation;
	private DataDictionary payWay;
	private DataDictionary category;
	private String usedFor;
	private String witness;
	private String moneyFrom;
	private String moneyTo;
	private String fromUserId;
	private String toUserId;
	
	private String typeId;
	private String payWayId;
	private String categoryId;
	public DataDictionary getType() {
		return type;
	}
	public void setType(DataDictionary type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealLocation() {
		return dealLocation;
	}
	public void setDealLocation(String dealLocation) {
		this.dealLocation = dealLocation;
	}
	public DataDictionary getPayWay() {
		return payWay;
	}
	public void setPayWay(DataDictionary payWay) {
		this.payWay = payWay;
	}
	public DataDictionary getCategory() {
		return category;
	}
	public void setCategory(DataDictionary category) {
		this.category = category;
	}
	public String getUsedFor() {
		return usedFor;
	}
	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
	}
	public String getWitness() {
		return witness;
	}
	public void setWitness(String witness) {
		this.witness = witness;
	}
	public String getMoneyFrom() {
		return moneyFrom;
	}
	public void setMoneyFrom(String moneyFrom) {
		this.moneyFrom = moneyFrom;
	}
	public String getMoneyTo() {
		return moneyTo;
	}
	public void setMoneyTo(String moneyTo) {
		this.moneyTo = moneyTo;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
