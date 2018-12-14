//package com.srx.pms.util.json;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig;
//import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
//
//public class JsonUtil {
//		private static ObjectMapper objectMapper = new ObjectMapper(); /*
//																		 * 初始化objectMapper
//																		 */
//		static { /* 对象的所有字段全部列入序列化 */
//			objectMapper.setSerializationInclusion(Inclusion.ALWAYS); /* 取消默认转换timestamps */
//			objectMapper.configure(
//					SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
//					false); /* 忽略空bean转json错误 */
//			objectMapper.configure(
//					SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false); /*
//																			 * 所有的日期格式都统一为以下格式
//																			 * ：
//																			 * yyyy
//																			 * -
//																			 * MM
//																			 * -
//																			 * dd
//																			 * HH
//																			 * :
//																			 * mm
//																			 * :
//																			 * ss
//																			 */
//			objectMapper.setDateFormat(new SimpleDateFormat(
//					DateTimeUtil.STANDARD_FORMAT)); /*
//													 * 反序列化时，忽略在json字符串当中存在，
//													 * 但是在java对象当中不存在的对应属性的情况
//													 * ，防止错误
//													 */
//			objectMapper.configure(
//					DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
//					false);
//		}
//
//		/**
//		 * object转换json序列化
//		 * 
//		 * @param obj
//		 *            object
//		 * @param <T>
//		 *            泛型
//		 * @return 返回object序列化后json字符串，异常或object=null则返回null
//		 */
//		public static <T> String obj2String(T obj) {
//			if (obj == null) {
//				return null;
//			}
//			try {
//				return obj instanceof String ? (String) obj : objectMapper
//						.writeValueAsString(obj);
//			} catch (Exception e) {
//				log.warn("parse object to String error ", e);
//				return null;
//			}
//		}
//
//		/**
//		 * object 转换json序列化，并且返回格式化之后的字符串 <br>
//		 * 格式化
//		 * 
//		 * @param obj
//		 *            object
//		 * @param <T>
//		 *            泛型
//		 * @return 返回object序列化后json字符串，异常或object=null则返回null
//		 */
//		public static <T> String obj2StringPretty(T obj) {
//			if (obj == null) {
//				return null;
//			}
//			try {
//				return obj instanceof String ? (String) obj : objectMapper
//						.writerWithDefaultPrettyPrinter().writeValueAsString(
//								obj);
//			} catch (IOException e) {
//				log.warn("parse object to String error ", e);
//				return null;
//			}
//		}
//
//		/**
//		 * String反序列化为执行类型clazz <br>
//		 * 无法满足List<User>等集合的反序列化(json转对象)
//		 * 
//		 * @param str
//		 *            待序列化json字符串
//		 * @param clazz
//		 *            json序列化成的对象
//		 * @param <T>
//		 *            泛型
//		 * @return 返回对象，str为null和异常则返回null。否则返回clazz对象
//		 */
//		public static <T> T String2Obj(String str, Class<T> clazz) {
//			if (StringUtils.isEmpty(str) || clazz == null) {
//				return null;
//			}
//			try {
//				return clazz.equals(String.class) ? (T) str : objectMapper
//						.readValue(str, clazz);
//			} catch (IOException e) {
//				log.warn("parse string to object error ", e);
//				return null;
//			}
//		}
//
//		/**
//		 * string反序列化<br>
//		 * json反序列化成以下：List<User> Map<User,Category>等对象
//		 * 
//		 * @param str
//		 *            json
//		 * @param typeReference
//		 *            jackson当中序列化对象
//		 * @param <T>
//		 *            泛型
//		 * @return 返回typeReference当中制定类型
//		 */
//		@SuppressWarnings("unchecked")
//		public static <T> T string2Obj(String str,
//				TypeReference<T> typeReference) {
//			if (StringUtils.isEmpty(str) || typeReference == null) {
//				return null;
//			}
//			try {
//				return (T) (typeReference.getType().equals(String.class) ? str
//						: objectMapper.readValue(str, typeReference));
//			} catch (IOException e) {
//				log.warn("parse string to object error ", e);
//				return null;
//			}
//		}
//
//		/**
//		 * string反序列化<br>
//		 * List<User>复杂对象collectionClass 和 elementClass分别为List.class User.class
//		 * 
//		 * @param str
//		 *            json
//		 * @param collectionClass
//		 *            集合类型
//		 * @param elementClasses
//		 *            集合当中元素
//		 * @param <T>
//		 *            泛型
//		 * @return 返回对象
//		 */
//		public static <T> T string2Obj(String str, Class<?> collectionClass,
//				Class<?>... elementClasses) {
//			JavaType javaType = objectMapper.getTypeFactory()
//					.constructParametricType(collectionClass, elementClasses);
//			try {
//				return objectMapper.readValue(str, javaType);
//			} catch (Exception e) {
//				log.warn("parse string to object error ", e);
//				return null;
//			}
//		}
//	}
//
//}
