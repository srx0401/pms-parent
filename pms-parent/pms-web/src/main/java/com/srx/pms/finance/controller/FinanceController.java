package com.srx.pms.finance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.component.Result;
import com.srx.pms.common.controller.BaseController;
import com.srx.pms.common.model.dataobject.Page;
import com.srx.pms.module.finance.model.entity.Finance;
import com.srx.pms.module.finance.model.querybean.FinanceQueryBean;
import com.srx.pms.module.finance.service.FinanceService;
@Controller
@RequestMapping("/finance")
public class FinanceController extends BaseController<Finance, FinanceQueryBean> {
	private static final Logger LOG = LoggerFactory.getLogger(FinanceController.class);
	@Autowired
	private FinanceService financeService;
	@ResponseBody
    @RequestMapping(value = "list")
    public Result list(FinanceQueryBean q) throws Exception {
		try {
			Page<Finance> res = financeService.list(q);
			return Result.successed(res);
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "save")
    public Result save(Finance t) throws Exception {
		try {
			return Result.successed(financeService.save(t));
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
}
