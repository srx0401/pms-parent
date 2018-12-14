package com.srx.pms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srx.pms.common.controller.BaseController;
import com.srx.pms.system.model.entity.Role;
import com.srx.pms.system.model.querybean.RoleQueryBean;
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController<Role, RoleQueryBean> {

}
