package com.srx.pms.module.user.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.module.user.service.UserService;
import com.srx.pms.user.mapper.UserMapper;
import com.srx.pms.user.model.entity.User;
import com.srx.pms.user.model.querybean.UserQueryBean;
import com.srx.pms.util.security.MD5Util;
@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserQueryBean> implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserMapper userMapper;
	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
		LOG.info(this.userMapper.toString());
		super.setBaseMapper(userMapper);
	}
	@Override
	public User login(String loginName, String password) throws PMSException {
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			throw new PMSException(Contant.Message.PARAM_ERROR.getMsg() + ",用户名/密码未传入.");
		}
		List<User> users = null;
		try {
			users = userMapper.select(new UserQueryBean(loginName,MD5Util.MD5(loginName +"_"+ password)));
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
		if (users == null || users.size() < 1) {
			throw new PMSException(Contant.Message.LOGINNAME_OR_PASSWORD_INCORRECT);
		}
		if (users.size() > 1) {
			throw new PMSException(Contant.Message.LOGIN_ACCOUNT_EXCEPTION);
		}
		User u = users.get(0);
		u.setPassword("");
		return u;
	}
}
