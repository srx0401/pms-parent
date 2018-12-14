package com.srx.pms.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.component.Result;
import com.srx.pms.common.controller.BaseController;
import com.srx.pms.system.model.entity.DataType;
import com.srx.pms.system.model.querybean.DataTypeQueryBean;
import com.srx.pms.system.service.DataTypeService;
@Controller
@RequestMapping("/sys/dataType")
public class DataTypeController extends BaseController<DataType, DataTypeQueryBean> {
	@Autowired
	private DataTypeService dataTypeService;
	@ResponseBody
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public Result list(DataTypeQueryBean q) throws Exception {
		try {
	        return Result.successed(dataTypeService.list(q));
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
}
