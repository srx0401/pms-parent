package com.srx.pms.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.UserRoleMapper;
import com.srx.pms.system.model.entity.UserRole;
import com.srx.pms.system.model.querybean.UserRoleQueryBean;
import com.srx.pms.system.service.UserRoleService;
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole,UserRoleQueryBean> implements
		UserRoleService {
	private static final Logger LOG = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	private UserRoleMapper userRoleMapper;
	@Autowired
	public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
		this.userRoleMapper = userRoleMapper;
		LOG.info(this.userRoleMapper.toString());
		super.setBaseMapper(userRoleMapper);
	}
}
