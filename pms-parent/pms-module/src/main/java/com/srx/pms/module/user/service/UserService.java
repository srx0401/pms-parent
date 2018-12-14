package com.srx.pms.module.user.service;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.service.BaseService;
import com.srx.pms.user.model.entity.User;
import com.srx.pms.user.model.querybean.UserQueryBean;

public interface UserService extends BaseService<User, UserQueryBean> {
	User login(String loginName,String password) throws PMSException;
}
