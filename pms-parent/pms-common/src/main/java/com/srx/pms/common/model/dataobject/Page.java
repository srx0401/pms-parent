package com.srx.pms.common.model.dataobject;

import java.util.List;

import com.srx.pms.common.model.entity.BaseEntity;

public class Page<T extends BaseEntity> {
	private int page;
	private int size;
	private Long start;
	private Long limit;
	private Long total = 0L;
	private List<T> list;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	public long getLimit() {
		return limit;
	}
	public void setLimit(long limit) {
		this.limit = limit;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
	public Page(Long start, Long limit) {
		super();
		this.start = start;
		this.limit = limit;
	}
	
	public Page(int page, Long start, Long limit) {
		super();
		this.page = page;
		this.start = start;
		this.limit = limit;
	}
	public Page(Long start, Long limit, List<T> list) {
		super();
		this.start = start;
		this.limit = limit;
		this.list = list;
	}
	
	public Page(Long total, List<T> list) {
		super();
		this.total = total;
		this.list = list;
	}
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}
}