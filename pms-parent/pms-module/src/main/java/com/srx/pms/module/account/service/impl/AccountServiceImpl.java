package com.srx.pms.module.account.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.annotation.ExcelAnnotationFactory;
import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.module.account.mapper.AccountMapper;
import com.srx.pms.module.account.model.entity.Account;
import com.srx.pms.module.account.model.querybean.AccountQueryBean;
import com.srx.pms.module.account.service.AccountService;
import com.srx.pms.system.model.entity.DataDictionary;
import com.srx.pms.system.service.DataDictionaryService;
import com.srx.pms.util.file.ExcelSheet;
import com.srx.pms.util.file.ExcelTitleCell;
import com.srx.pms.util.file.POIUtil;
@Service("accountService")
@Transactional
public class AccountServiceImpl extends BaseServiceImpl<Account, AccountQueryBean> implements
		AccountService {
	private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
	private AccountMapper accountMapper;
	@Autowired
	private DataDictionaryService dictService;
	@Autowired
	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
		LOG.info(this.accountMapper.toString());
		super.setBaseMapper(accountMapper);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void downloadTemplate(Class clazz, HttpServletRequest req,
			HttpServletResponse resp) throws PMSException {
		ExcelSheet accountSheet = getExcelSheet(clazz == null ? Account.class : clazz);
		
		if (accountSheet == null) {
			return;
		}
		ExcelSheet accountTypeSheet = getAccountTypeSheet();
		try {
			String fileName = accountSheet.getName();
			POIUtil.writeExcel(fileName, new ExcelSheet[]{accountSheet,accountTypeSheet}, req, resp);
		} catch (Exception e) {
			throw new PMSException(Contant.Message.EXCEL_WRITE_ERROR);
		}
	}
	private ExcelSheet getAccountTypeSheet(){
		List<com.srx.pms.common.component.file.excel.ExcelTitleCell> titleCells = ExcelAnnotationFactory.getObjectTitleCellArray(DataDictionary.class, null);
		List<ExcelTitleCell> titleCellList = new ArrayList<ExcelTitleCell>();
		for (com.srx.pms.common.component.file.excel.ExcelTitleCell cell : titleCells) {
			titleCellList.add(new ExcelTitleCell(cell.getCode(), cell.getName(), cell.getComment(), cell.getSort(), cell.getExampleValue(), cell.getType()));
		}
		ExcelTitleCell[]titleCellArray =new ExcelTitleCell[titleCellList.size()];
		titleCellList.toArray(titleCellArray);
		List<DataDictionary> accountTypeList = dictService.loadDictByDataTypeCode("ACCOUNT_TYPE");
		try {
			List<Map<String, String>> data = ExcelAnnotationFactory.toList(accountTypeList, true, false);
			return new ExcelSheet("账户类别",titleCellArray,data);
		} catch (Exception e) {
			throw new PMSException(Contant.Message.EXCEL_PARSE_ERROR);
		}
		
	}
}
