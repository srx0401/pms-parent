package com.srx.pms.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.model.dataobject.Page;
import com.srx.pms.common.model.entity.BaseEntity;
import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.user.model.entity.User;
/**
 * 	带sessionUser参数的接口主要用于第三方,不带sessionUser参数的接口主要用于本系统
 * @author Simon
 *
 * @param <T>
 * @param <Q>
 */
public interface BaseService<T extends BaseEntity, Q extends BaseQueryBean<T>> {
	boolean save(T t,User sessionUser) throws PMSException;
	boolean remove(String id,User sessionUser) throws PMSException;
	boolean remove(Q q,User sessionUser) throws PMSException;
	List<T> query(Q q,User sessionUser) throws PMSException;
	T load(String id,User sessionUser) throws PMSException;
	Page<T> list(Q q,User sessionUser) throws PMSException;
	
	boolean save(T t) throws PMSException;
	
	boolean remove(String id) throws PMSException;
	boolean remove(Q q) throws PMSException;
	List<T> query(Q q) throws PMSException;
	T load(String id) throws PMSException;
	Page<T> list(Q q) throws PMSException;
	
	List<String> importExcel(MultipartFile file,Class<T> t) throws PMSException;
	@SuppressWarnings("rawtypes")
	void downloadTemplate(Class clazz,HttpServletRequest req,HttpServletResponse resp)throws PMSException;
}
