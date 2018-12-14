package com.srx.pms.account.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.component.Result;
import com.srx.pms.common.controller.BaseController;
import com.srx.pms.module.account.model.entity.Account;
import com.srx.pms.module.account.model.querybean.AccountQueryBean;
import com.srx.pms.module.account.service.AccountService;
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController<Account,AccountQueryBean> {
	private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private AccountService accountService;
	@ResponseBody
    @RequestMapping(value = "list")
    public Result list(AccountQueryBean q) throws Exception {
		try {
			return Result.successed(accountService.list(q));
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "save")
    public Result save(Account t) throws Exception {
		try {
			return Result.successed(accountService.save(t));
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "detail")
    public Result detail(String id) throws Exception {
		try {
			return Result.successed(accountService.load(id));
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "remove")
    public Result remove(AccountQueryBean q) throws Exception {
		try {
			return Result.successed(accountService.remove(q));
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "import")
    public Result importExcel(MultipartFile file) throws Exception {
		try {
			List<String> errList = accountService.importExcel(file,Account.class);
			return Result.successed(errList);
		} catch (PMSException e) {
			LOG.error(e.getMessage(),e);
			return Result.error(e.getMsg());
		}
    }
	@ResponseBody
    @RequestMapping(value = "downloadTemplate")
    public void downloadTemplate() throws Exception {
		accountService.downloadTemplate(Account.class, getRequest(), getResponse());
    }
}
