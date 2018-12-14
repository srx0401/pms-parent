package com.srx.pms.system.model.querybean;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.system.model.entity.Role;
@Alias("RoleQueryBean")
public class RoleQueryBean extends BaseQueryBean<Role> {

}
