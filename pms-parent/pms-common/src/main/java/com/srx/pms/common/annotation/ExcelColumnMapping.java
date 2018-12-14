package com.srx.pms.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Inherited
@Documented
/**
 * Excel导入导出映射注解
 * @author 
 *
 */
public @interface ExcelColumnMapping {
//	public Class type() default String.class;
	/**
	 * 	列名
	 * @return
	 */
	public String columnName();
	/**
	 * 	格式,主要用于格式转换,如:日期类型
	 * @return
	 */
	public String pattern() default "";
	/**
	 * 	默认值
	 * @return
	 */
	public String defaultValue() default "";
	/**
	 * 	排序,导出时可参考此属性排列Excel列
	 * @return
	 */
	public int sort() default 0;
	/**
	 * 	属性分隔符,属性为对象时,导入该属性的列名以指定分隔符连接,如:员工-姓名
	 * @return
	 */
	public String fieldSeparator() default "-";
	/**
	 * 	是否允许为空,默认允许为空
	 * @return
	 */
	public boolean nullable() default true;
	/**
	 * 	例值
	 * @return
	 */
	public String example() default "";
	/**
	 * 	批注
	 * @return
	 */
	public String comment() default "";
	public String valueOfTrue() default "是";
	public String valueOfFalse() default "否";
}
