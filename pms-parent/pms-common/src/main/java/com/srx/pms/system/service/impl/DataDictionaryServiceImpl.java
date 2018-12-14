package com.srx.pms.system.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.contant.Contant;
import com.srx.pms.common.service.impl.BaseServiceImpl;
import com.srx.pms.system.mapper.DataDictionaryMapper;
import com.srx.pms.system.model.entity.DataDictionary;
import com.srx.pms.system.model.querybean.DataDictionaryQueryBean;
import com.srx.pms.system.service.DataDictionaryService;
@Service("dataDictionaryService")
@Transactional
public class DataDictionaryServiceImpl extends
		BaseServiceImpl<DataDictionary, DataDictionaryQueryBean> implements
		DataDictionaryService {
	private static final Logger LOG = LoggerFactory.getLogger(DataDictionaryServiceImpl.class);
	private DataDictionaryMapper dataDictionaryMapper;
	@Autowired
	public void setDataDictionaryMapper(DataDictionaryMapper dataDictionaryMapper) {
		this.dataDictionaryMapper = dataDictionaryMapper;
		LOG.info(this.dataDictionaryMapper.toString());
		super.setBaseMapper(dataDictionaryMapper);
	}
	@Override
	public List<DataDictionary> loadDictByDataTypeCode(String dataTypeCode)
			throws PMSException {
		if (StringUtils.isEmpty(dataTypeCode)) {
			throw new PMSException(Contant.Message.PARAM_ERROR.getMsg()+",[dataTypeCode]不能为空.");
		}
		try {
			DataDictionaryQueryBean q = new DataDictionaryQueryBean(dataTypeCode);
			return dataDictionaryMapper.select(q);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	@Override
	public List<DataDictionary> loadWallpapers() throws PMSException {
		try {
			DataDictionaryQueryBean q = new DataDictionaryQueryBean("WALLPAPER");
			return dataDictionaryMapper.select(q);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	@Override
	public DataDictionary loadDefaultWallpaper() throws PMSException {
		try {
			DataDictionaryQueryBean q = new DataDictionaryQueryBean("WALLPAPER");
			q.setDefault(true);
			List<DataDictionary> res = dataDictionaryMapper.select(q);
			return res != null && res.size() > 0 ? res.get(0) : null;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	@Override
	public DataDictionary loadDefaultDesktopStartBtnIcon() throws PMSException {
		try {
			DataDictionaryQueryBean q = new DataDictionaryQueryBean("DESKTOP_START_ICON");
			q.setDefault(true);
			List<DataDictionary> res = dataDictionaryMapper.select(q);
			return res != null && res.size() > 0 ? res.get(0) : null;
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
	@Override
	public List<DataDictionary> query(DataDictionaryQueryBean q)
			throws PMSException {
		if (q == null) {
			throw new PMSException(Contant.Message.PARAM_ERROR.getMsg()+",[q]不能为空.");
		}
		try {
			return dataDictionaryMapper.select(q);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new PMSException(Contant.Message.DATA_ACCESS_ERROR);
		}
	}
}
