package com.srx.pms.system.mapper;

import java.sql.SQLException;
import java.util.List;

import com.srx.pms.common.mapper.BaseMapper;
import com.srx.pms.system.model.entity.Resource;
import com.srx.pms.system.model.querybean.ResourceQueryBean;

public interface ResourceMapper extends BaseMapper<Resource, ResourceQueryBean> {
	List<Resource> loadMenus(final ResourceQueryBean q) throws SQLException;
}
