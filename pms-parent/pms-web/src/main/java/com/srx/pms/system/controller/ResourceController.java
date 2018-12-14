package com.srx.pms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.srx.pms.common.controller.BaseController;
import com.srx.pms.system.model.entity.Resource;
import com.srx.pms.system.model.querybean.ResourceQueryBean;
@Controller
@RequestMapping("/sys/res")
public class ResourceController extends BaseController<Resource, ResourceQueryBean> {

}
