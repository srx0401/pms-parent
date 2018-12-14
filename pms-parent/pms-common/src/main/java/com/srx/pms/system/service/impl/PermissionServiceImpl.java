package com.srx.pms.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.PermissionMapper;
import com.srx.pms.system.model.entity.Permission;
import com.srx.pms.system.model.querybean.PermissionQueryBean;
import com.srx.pms.system.service.PermissionService;
@Service("permissionService")
@Transactional
public class PermissionServiceImpl extends
		BaseServiceImpl<Permission, PermissionQueryBean> implements
		PermissionService {
	private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);
	private PermissionMapper permissionMapper;
	@Autowired
	public void setPermissionMapper(PermissionMapper permissionMapper) {
		this.permissionMapper = permissionMapper;
		LOG.info(this.permissionMapper.toString());
		super.setBaseMapper(permissionMapper);
	}
}
