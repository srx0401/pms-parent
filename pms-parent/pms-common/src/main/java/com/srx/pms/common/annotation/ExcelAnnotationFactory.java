package com.srx.pms.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.StringUtil;
import com.srx.pms.common.component.file.excel.ExcelTitleCell;

public class ExcelAnnotationFactory {
	private static DateFormat format;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T parse(Map<String, String> data, Class<T> obj)
			throws Exception {
		if (obj == null) {
			return null;
		}
		Object res = obj.newInstance();
		Class clazz = res.getClass();
		Field[] declaredFields = null;
		while(!clazz.equals(Object.class)){
			declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				if(!field.isAnnotationPresent(ExcelColumnMapping.class)){ 
					continue;
				}
				ExcelColumnMapping target = field.getAnnotation(ExcelColumnMapping.class);
				
				field.setAccessible(true);
				
				String dataValue = data.get(target.columnName());
				
				String value = StringUtil.isEmpty(dataValue) ? target.defaultValue() : dataValue;
				Class fieldType = field.getType();
				Object val = null;
				if (fieldType.getClassLoader() == null && StringUtil.isEmpty(value)) {
					if (target.nullable()) {
						continue;
					}
					throw new Exception("["+target.columnName() + "]不允许为空;");
				}
				if (fieldType.isEnum()) {
					//	枚举
				}else if (fieldType.isArray()) {
					//	集合
				}else if (fieldType.getClassLoader() == null) {
					if (fieldType.equals(String.class)) {
						val = value;
					}else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
						val = StringUtil.isEmpty(value) ? null : Integer.parseInt(value);
					}else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
						val = StringUtil.isEmpty(value) ? null : Long.parseLong(value);
					}else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
						val = StringUtil.isEmpty(value) ? null : Double.parseDouble(value);
					}else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
						val = StringUtil.isEmpty(value) ? null : Float.parseFloat(value);
					}else if (fieldType.equals(Short.class) || fieldType.equals(short.class)) {
						val = StringUtil.isEmpty(value) ? null : new Short(value);
					}else if (fieldType.equals(Character.class) || fieldType.equals(char.class)) {
						val = StringUtil.isEmpty(value) ? null : new Character(value.charAt(0));
					}else if (fieldType.equals(Byte.class) || fieldType.equals(byte.class)) {
						val = StringUtil.isEmpty(value) ? null : Byte.valueOf(value);
					}else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
						val = StringUtil.isEmpty(value) ? null : new Boolean(value);
					}else if(fieldType.equals(BigDecimal.class)){
						val = StringUtil.isEmpty(value) ? null : new BigDecimal(value);
					}else if(fieldType.equals(Date.class)){
						if (value == null || value.length() < 1 || target.pattern() == null || target.pattern().length() < 1) {
							val = null;
						}else{
							format = new SimpleDateFormat(target.pattern());
							val = format.parseObject(value);
						}
					}
				}else{
					Map<String,String> fieldMap = new HashMap<String,String>();
					String sep = target.fieldSeparator();
					for(Map.Entry<String, String> m : data.entrySet()){
						if (m.getKey().startsWith(target.columnName() + sep)) {
							fieldMap.put(m.getKey().substring(m.getKey().indexOf(target.columnName() + sep) + (target.columnName() + sep).length()), m.getValue());
						}
					}
	//				自定义类型
					val = parse(fieldMap, fieldType);
				}
				if ((!target.nullable()) && val == null) {
					throw new Exception("["+target.columnName() + "]不允许为空;");
				}
				field.set(res, val);
			}
			clazz = clazz.getSuperclass();
		}
		return (T) res;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String getExcelFileName(Class clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if (clazz == null || (!clazz.isAnnotationPresent(ExcelFileMapping.class))){ 
			return null;
		}
		Annotation anno = clazz.getAnnotation(ExcelFileMapping.class);
        return anno.annotationType().getMethod("fileName").invoke(anno).toString();  
	}
	@SuppressWarnings("rawtypes")
	public static Map<String, String> toMap(Object obj,boolean getDefaultValue,boolean getExampleValue) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();
		Class clazz = obj.getClass();
		Field[] declaredFields = null;
		while(!clazz.equals(Object.class)){
			declaredFields = clazz.getDeclaredFields();
			for (Field field : declaredFields) {
				if(!field.isAnnotationPresent(ExcelColumnMapping.class)){ 
					continue;
				}
				ExcelColumnMapping target = field.getAnnotation(ExcelColumnMapping.class);
				field.setAccessible(true);
				if (field.getType().isEnum()) {
					//	枚举
				}else if (field.getType().isArray()) {
					//	集合
				}else if (field.getType().getClassLoader() == null) {
					Object val = field.get(obj);
					String defaultValue = target.defaultValue();
					String exampleValue = target.example();
					if (val == null) {
						if (getExampleValue) {
							val = exampleValue;
						}
					}
					if (val == null && getDefaultValue) {
						val = defaultValue;
					}
					//	基本类型
					if(field.getType().equals(Date.class)){
						if (field.get(obj) == null || target.pattern() == null || target.pattern().length() < 1) {
							
						}else{
							format = new SimpleDateFormat(target.pattern());
							map.put(target.columnName(), format.format(val));
						}
					}else if(field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
						map.put(target.columnName(), val.equals(true) ? target.valueOfTrue() : target.valueOfFalse());
					}else{
						map.put(target.columnName(), val.toString());
					}
				}else{
					//	自定义类型
					Map<String,String> fieldMap = toMap(field.get(obj),getDefaultValue,getExampleValue);
					for(Map.Entry<String, String> m : fieldMap.entrySet()){
						map.put(target.columnName() + target.fieldSeparator() + m.getKey(), m.getValue());
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		
		return map;
	}
	public static List<Map<String, String>> toList(List<?> list,boolean getDefaultValue,boolean getExampleValue) throws Exception {
		if (list == null || list.size() < 1) {
			return null;
		}
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for(Object obj : list){
			if (obj == null) {
				continue;
			}
			res.add(toMap(obj, getDefaultValue, getExampleValue));
		}
		return res;
	}
	@SuppressWarnings("rawtypes")
	public static List<ExcelTitleCell> getObjectTitleCellArray(Class clazz,BigDecimal salt){
		if (clazz == null) {
			return null;
		}
		List<ExcelTitleCell> cells = new ArrayList<ExcelTitleCell>();
		Class c = clazz;
		Field[] declaredFields = null;
		while(!c.equals(Object.class)){
			declaredFields = c.getDeclaredFields();
			for (Field field : declaredFields) {
				if(!field.isAnnotationPresent(ExcelColumnMapping.class)){ 
					continue;
				}
				
				ExcelColumnMapping target = field.getAnnotation(ExcelColumnMapping.class);
				field.setAccessible(true);
				if (field.getType().getClassLoader() == null) {
					//	基本类型
					cells.add(new ExcelTitleCell(field.getName(), target.columnName(), target.comment(), salt == null ? BigDecimal.valueOf(target.sort()) : salt.add(new BigDecimal("0."+String.valueOf(target.sort()))), target.example()));
				}else{
					//	自定义类型
					cells.addAll(getObjectTitleCellArray(field.getType(),new BigDecimal(target.sort())));
				}
			}
			c = c.getSuperclass();
		}
		Collections.sort(cells);
		return cells;
	}
	
	public static void main(String[] args) throws Exception  {
//		testParse();
//		testToMap();
//		testGetObjectColumnName();
//		testGetExcelFileName();
		testGetObjectTitleCellArray();
	}
	public static void testGetExcelFileName() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		System.out.println(getExcelFileName(User.class));
	}
	@SuppressWarnings("serial")
	public static void testParse(){
		try {
			Map<String,String> userData = new HashMap<String,String>(){{
				put("用户名", "user1001");
				put("密码", "password1001");
				put("是否有效", "true");
				put("员工-工号", "1001");
				put("员工-姓名", "张三");
				put("员工-生日", "1987-12-15");
				put("员工-性别", "1");
				put("员工-工资", "3500");
				put("员工-年龄", "31");
			}};
			User user = parse(userData, User.class);
			System.out.println(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void testToMap() throws Exception{
		Employee emp = new Employee("1001","张三",1,new Date(),BigDecimal.valueOf(3500),31);
		User u = new User("user1001","password1001",true,emp);
		Map<String,String> res = toMap(u,true,false);
		for(Map.Entry<String, String> m : res.entrySet()){
			System.out.println(m.getKey() + "=" + m.getValue());
		}
	}
	public static void testGetObjectTitleCellArray(){
		List<ExcelTitleCell> cells = getObjectTitleCellArray(User.class, null);
		for(ExcelTitleCell c : cells){
			System.out.println(c);
		}
	}
}
