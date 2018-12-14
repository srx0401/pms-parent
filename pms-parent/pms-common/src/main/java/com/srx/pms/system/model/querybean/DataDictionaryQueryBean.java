package com.srx.pms.system.model.querybean;

import org.apache.ibatis.type.Alias;

import com.srx.pms.common.model.querybean.BaseQueryBean;
import com.srx.pms.system.model.entity.DataDictionary;
@Alias("DataDictionaryQueryBean")
public class DataDictionaryQueryBean extends BaseQueryBean<DataDictionary> {
	private String dataTypeId;
	private String dataTypeCode;
	private boolean isDefault;
	public String getDataTypeCode() {
		return dataTypeCode;
	}

	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}

	public DataDictionaryQueryBean(String dataTypeCode) {
		super();
		this.dataTypeCode = dataTypeCode;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public DataDictionaryQueryBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDataTypeId() {
		return dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}
}
