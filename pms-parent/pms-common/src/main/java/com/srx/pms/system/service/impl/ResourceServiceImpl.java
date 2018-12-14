package com.srx.pms.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.ResourceMapper;
import com.srx.pms.system.model.entity.Resource;
import com.srx.pms.system.model.querybean.ResourceQueryBean;
import com.srx.pms.system.service.ResourceService;
import com.srx.pms.user.model.entity.User;
import com.srx.pms.util.BooleanUtil;
@Service("resourceService")
@Transactional
public class ResourceServiceImpl extends
		BaseServiceImpl<Resource, ResourceQueryBean> implements ResourceService {
	private static final Logger LOG = LoggerFactory.getLogger(ResourceServiceImpl.class);
	private ResourceMapper resourceMapper;
	@Autowired
	public void setResourceMapper(ResourceMapper resourceMapper) {
		this.resourceMapper = resourceMapper;
		LOG.info(this.resourceMapper.toString());
		super.setBaseMapper(resourceMapper);
	}
	@Override
	public List<Resource> loadMenus() throws PMSException {
		ResourceQueryBean q = new ResourceQueryBean();
		q.setTypeCode("RESOURCE_TYPE.MENU");
		q.setValid(1);
		User user = getSessionUser();
		validateUserWithIdNotNull(user);
		if (!BooleanUtil.parseBoolean(user.getIsRoot())) {
			q.setUserId(user.getId());
		}
		try {
			return resourceMapper.select(q);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
}
