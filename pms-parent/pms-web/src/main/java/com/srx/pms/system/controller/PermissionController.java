package com.srx.pms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srx.pms.common.controller.BaseController;
import com.srx.pms.system.model.entity.Permission;
import com.srx.pms.system.model.querybean.PermissionQueryBean;
@Controller
@RequestMapping("/sys/perm")
public class PermissionController extends BaseController<Permission, PermissionQueryBean> {
	
}
