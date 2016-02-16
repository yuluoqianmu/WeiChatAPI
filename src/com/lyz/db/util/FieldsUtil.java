package com.lyz.db.util;

import org.apache.log4j.Logger;

/**
 * 数据库中字段和java类字段之间的转换类
 * 
 * 约定：数据库中的字段通过下划线进行分割，java类中字段通过大小写分割
 * @author luyongzhao
 *
 */
public class FieldsUtil {
	
	private static final Logger logger = Logger.getLogger(FieldsUtil.class);
	/**
	 * 将数据库的属性转换成java类的属性，第一个属性段开头小写
	 * @param dbField
	 * @return 如果dbField不存在或者为空，则返回null
	 */
	public static String getJavaClassAttr(String dbField){
		
		if(dbField == null || "".equals(dbField.trim())){
			return null;
		}
		
		String[] fields = dbField.split("_");
		
		StringBuilder builder = new StringBuilder();
		int index = 0;
		
		for(String field : fields){
			if("".equals(field.trim())){
				continue;
			}
			if(index == 0){
				builder.append(field);
			}else{
				/*将第一个字母转换成大写*/
				char firstChar = field.charAt(0);
				String firstString = (""+firstChar).toUpperCase();
				/*重新拼接*/
				String subStr = field.substring(1);
				builder.append(firstString).append(subStr);
			}
			
			index++;
		}
		
		return builder.toString();
	}
	/**
	 * 首字母大写
	 * @param dbField
	 * @return
	 */
	public static String getJavaClassName(String dbField){
		
		if(dbField == null || "".equals(dbField.trim())){
			return null;
		}
		
		String[] fields = dbField.split("_");
		
		StringBuilder builder = new StringBuilder();
		
		
		for(String field : fields){
			if("".equals(field.trim())){
				continue;
			}
			/*将第一个字母转换成大写*/
			char firstChar = field.charAt(0);
			String firstString = (""+firstChar).toUpperCase();
			/*重新拼接*/
			String subStr = field.substring(1);
			builder.append(firstString).append(subStr);
			
		}
		
		return builder.toString();
	}
	
	public static String getJavaGetMethod(String dbField){
		
		if(dbField == null || "".equals(dbField.trim())){
			return null;
		}
		
		return "get"+getJavaClassName(dbField);
		
	}
	
	public static String getJavaSetMethod(String dbField){
		
		if(dbField == null || "".equals(dbField.trim())){
			return null;
		}
		
		return "set"+getJavaClassName(dbField);
		
	}
	
	public static String getJavaTypeFromSqlType(String sqlType){
		
		if(sqlType == null || "".equals(sqlType.trim())){
			return null;
		}
		
		if(sqlType.startsWith("varchar") || sqlType.startsWith("char")){
			return "String";
		}else if(sqlType.startsWith("int")){
			return "int";
		}else if(sqlType.startsWith("bigint")){
			return "long";
		}else if(sqlType.startsWith("boolean")){
			return "boolean";
		}else if(sqlType.startsWith("tinyint")){
			return "byte";
		}else if(sqlType.startsWith("smallint")){
			return "short";
		}else if(sqlType.startsWith("float")){
			return "float";
		}else if(sqlType.startsWith("double") || sqlType.startsWith("decimal")){
			return "double";
		}else{
			logger.warn("unknown sql type:"+sqlType);
			return "unknown";
			
		}
	}
	
	public static void main(String args[]){
		
		System.out.println(FieldsUtil.getJavaClassAttr("ming_tian"));
		System.out.println(FieldsUtil.getJavaClassName("ming_tian"));
		System.out.println(FieldsUtil.getJavaGetMethod("chong_qing_1"));
		System.out.println(FieldsUtil.getJavaSetMethod("chong_qing_1"));
	}
}
