package com.srx.pms.util.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

public class EntityUtil {
	/**
	 * 实体类转Map
	 * @param object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Map<String, Object> entity2Map(Object object) throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new HashMap<String,Object>();
	    for (Field field : object.getClass().getDeclaredFields()){
        	boolean flag = field.isAccessible();
            field.setAccessible(true);
            Object o = field.get(object);
            map.put(field.getName(), o);
            field.setAccessible(flag);
	    }
	    return map;
	}
	
	/**
	 * Map转实体类
	 * @param map 		需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
	 * @param entity  	需要转化成的实体类
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static T map2Entity(Map<String, Object> map, Class<T> entity) throws InstantiationException, IllegalAccessException {
		T res = entity.newInstance();
		for(Field field : entity.getDeclaredFields()) {
			if (map.containsKey(field.getName())) {
				boolean flag = field.isAccessible();
	            field.setAccessible(true);
	            Object object = map.get(field.getName());
	            if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
	            	 field.set(res, object);
				}
	            field.setAccessible(flag);
			}
		}
		return res;
	}
	/**
	 * 	集合解析为指定类型的对象集合,解析错误时不中断.
	 * @param list			待解析集合
	 * @param entity		指定的类型
	 * @param errorList		解析错误的数据
	 * @return
	 */
	public static List<T> parse2Entity(List<Map<String,Object>> list,Class<T> entity,List<Map<String,Object>> errorList){
		List<T> res = new ArrayList<T>();
		if (errorList == null) {
			errorList = new ArrayList<Map<String,Object>>();
		}
		for(Map<String,Object> map : list){
			try {
				res.add(map2Entity(map, entity));
			} catch (Exception e) {
				errorList.add(map);
			}
		}
		return res;
	}
	/**
	 * 	集合解析为指定类型的对象集合,解析错误时中断,并抛出异常
	 * @param list		待解析集合
	 * @param entity	指定的类型
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List<T> parse2Entity(List<Map<String,Object>> list,Class<T> entity) throws InstantiationException, IllegalAccessException{
		List<T> res = new ArrayList<T>();
		for(Map<String,Object> map : list){
			res.add(map2Entity(map, entity));
		}
		return res;
	}
}
