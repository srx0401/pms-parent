package com.srx.pms.common.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.common.model.querybean.BaseQueryBean;

public interface BaseMapper<T extends BaseEntity, Q extends BaseQueryBean<T>> {
	
	int insert(final T entity) throws SQLException;

	int update(final T e) throws SQLException;

	int delete(final String id) throws SQLException;

	List<T> select(final Q q) throws SQLException;
	
	T load(final String id) throws SQLException;
	
	long count(final Q q) throws SQLException;
	
	int batchDelete( final @Param(value = "ids") List<String> ids) throws SQLException;
}