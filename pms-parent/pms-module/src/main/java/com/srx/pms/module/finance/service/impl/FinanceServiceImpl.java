package com.srx.pms.module.finance.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.module.finance.mapper.FinanceMapper;
import com.srx.pms.module.finance.model.entity.Finance;
import com.srx.pms.module.finance.model.querybean.FinanceQueryBean;
import com.srx.pms.module.finance.service.FinanceService;
@Service("financeService")
@Transactional
public class FinanceServiceImpl extends BaseServiceImpl<Finance, FinanceQueryBean> implements
		FinanceService {
	private static final Logger LOG = LoggerFactory.getLogger(FinanceServiceImpl.class);
	private FinanceMapper financeMapper;
	@Autowired
	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
		LOG.info(this.financeMapper.toString());
		super.setBaseMapper(financeMapper);
	}
}
