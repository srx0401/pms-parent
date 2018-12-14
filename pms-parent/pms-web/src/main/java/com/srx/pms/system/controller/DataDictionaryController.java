package com.srx.pms.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.component.Result;
import com.srx.pms.common.controller.BaseController;
import com.srx.pms.system.model.entity.DataDictionary;
import com.srx.pms.system.model.querybean.DataDictionaryQueryBean;
import com.srx.pms.system.service.DataDictionaryService;
@Controller
@RequestMapping("/sys/dict")
public class DataDictionaryController extends BaseController<DataDictionary, DataDictionaryQueryBean> {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@ResponseBody
    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public Result list(DataDictionaryQueryBean q) throws Exception {
		try {
	        return Result.successed(dataDictionaryService.list(q));
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "/query.do", method = RequestMethod.GET)
    public Result query(DataDictionaryQueryBean q) throws Exception {
		try {
	        return Result.successed(dataDictionaryService.query(q));
		} catch (PMSException e) {
			return Result.error(e.getMsg());
		}
    }
}
