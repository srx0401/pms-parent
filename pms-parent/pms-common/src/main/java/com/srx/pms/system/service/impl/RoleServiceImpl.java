package com.srx.pms.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.RoleMapper;
import com.srx.pms.system.model.entity.Role;
import com.srx.pms.system.model.querybean.RoleQueryBean;
import com.srx.pms.system.service.RoleService;
@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleQueryBean>
		implements RoleService {
	private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
	private RoleMapper roleMapper;
	@Autowired
	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
		LOG.info(this.roleMapper.toString());
		super.setBaseMapper(roleMapper);
	}
}
