package com.srx.pms.system.service;

import java.util.List;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.service.BaseService;
import com.srx.pms.system.model.entity.Resource;
import com.srx.pms.system.model.querybean.ResourceQueryBean;

public interface ResourceService extends BaseService<Resource, ResourceQueryBean> {
	List<Resource> loadMenus() throws PMSException;
}
