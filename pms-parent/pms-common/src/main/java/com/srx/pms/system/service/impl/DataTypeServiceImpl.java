package com.srx.pms.system.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.DataTypeMapper;
import com.srx.pms.system.model.entity.DataType;
import com.srx.pms.system.model.querybean.DataTypeQueryBean;
import com.srx.pms.system.service.DataTypeService;
@Service("dataTypeService")
@Transactional
public class DataTypeServiceImpl extends
		BaseServiceImpl<DataType, DataTypeQueryBean> implements DataTypeService {
	private static final Logger LOG = LoggerFactory.getLogger(DataDictionaryServiceImpl.class);
	private DataTypeMapper dataTypeMapper;
	@Autowired
	public void setDataTypeMapper(DataTypeMapper dataTypeMapper) {
		this.dataTypeMapper = dataTypeMapper;
		LOG.info(this.dataTypeMapper.toString());
		super.setBaseMapper(dataTypeMapper);
	}
}
