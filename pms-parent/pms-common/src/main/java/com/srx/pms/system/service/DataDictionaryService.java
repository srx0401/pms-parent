package com.srx.pms.system.service;

import java.util.List;

import com.srx.pms.common.component.PMSException;
import com.srx.pms.common.service.BaseService;
import com.srx.pms.system.model.entity.DataDictionary;
import com.srx.pms.system.model.querybean.DataDictionaryQueryBean;

public interface DataDictionaryService extends BaseService<DataDictionary, DataDictionaryQueryBean> {
	List<DataDictionary> loadDictByDataTypeCode(String dataTypeCode) throws PMSException;
	List<DataDictionary> loadWallpapers() throws PMSException;
	DataDictionary loadDefaultWallpaper() throws PMSException;
	DataDictionary loadDefaultDesktopStartBtnIcon() throws PMSException;
}
