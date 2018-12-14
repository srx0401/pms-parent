package com.srx.pms.util.property;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

public class PropertyUtil {
	private static final Log LOG = LogFactory.getLog(PropertyUtil.class);
	private static final String MESSAGE_CFG_PATH = "message.properties";
	private static final String CONFIG_CFG_PATH = "config.properties";
	private static Map<String,Map<String,String>> properties;
	private static Map<String,Map<String,String>> loadProperties(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		if (properties == null) {
			properties = new HashMap<>();
		}
		if (properties.get(path) != null) {
			return properties;
		}
		LOG.info("load property from " + path);
		Properties props = new Properties();
		Resource location = new ClassPathResource(path);
		InputStream is = null;
		try {
			is = location.getInputStream();
			PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
			propertiesPersister.load(props, is);
		} catch (Exception e) {
			throw new RuntimeException("加载配置文件失败，配置文件路径:" + path, e);
		}
		Map<String,String> data = new HashMap<String,String>();
		if (props.size() > 0) {
			for (Map.Entry<Object, Object> e : props.entrySet()) {
				data.put(e.getKey().toString(), e.getValue() != null ? e.toString() : null);
			}
		}
		properties.put(path,data);
		return properties;
	}
	public static String get(final String path,final String key){
		if (StringUtils.isEmpty(path) || StringUtils.isEmpty(key)) {
			return null;
		}
		loadProperties(path);
		Map<String,String> cfg = properties.get(path);
		return cfg != null && cfg.size() > 0 ? cfg.get(key) : null;
	}
	public static String get(final String key){
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		String [] cfg = key.split(":");
		if (cfg.length < 2) {
			return null;
		}
		return get(cfg[0],cfg[1]);
	}
	public static String getMsg(final String key){
		return get(MESSAGE_CFG_PATH, key);
	}
	public static String getCfg(final String key){
		return get(CONFIG_CFG_PATH, key);
	}
}
