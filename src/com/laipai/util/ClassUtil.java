package com.laipai.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ClassUtil {
	
	private static final Logger logger = Logger.getLogger(ClassUtil.class);
	/**
	 * 反射机制实例化类
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className, Object[] args){
		if(className == null){
			return null;
		}
		Class cls = null;
		Class[] argsClass = null;
		/*构造参数*/
		if(args != null){
			argsClass = new Class[args.length];
			 for (int i = 0, j = args.length; i < j; i++) {                                
			        argsClass[i] = args[i].getClass();                                        
			 }	  
		}
		
		Object obj = null;
		try {
			cls = Class.forName(className);
			if(argsClass != null){
				Constructor con = cls.getConstructor(argsClass);
				obj = con.newInstance(args);
			}else{
				obj = cls.newInstance();
			}		
		} catch (ClassNotFoundException e) {
			logger.error("class not found!",e);
			return null;
		} catch (Exception e){
			logger.error(e);
			return null;
		} 
		
		return obj;
	}
	/**
	 * 不同对象的相同属性之间进行属性值拷贝
	 * @param srcObj 源对象
	 * @param target 目标对象
	 */
	public static void getObjFromAttrCopy(Object srcObj, Object target){
		
		if(srcObj==null || target==null){
			return;
		}
		
		Method[] methods = target.getClass().getMethods();
		if (methods == null || methods.length == 0) {
			logger.warn("no method in class:" + target.getClass().getName());
		}
		
		Map<String, Field> name2Field = new HashMap<String, Field>();
		/*获取源对象成员变量*/
		for(Field field : srcObj.getClass().getFields()){
			
			name2Field.put(field.getName(), field);
		}

		try {
			for (Method method : methods) {
				if(!method.getName().startsWith("set")){
					continue;
				}
				/*获取属性名称*/
				String filedName = getFieldName(method.getName());
				/*获取源属性*/
				Field srcF = null;
				try {
					srcF = srcObj.getClass().getDeclaredField(filedName);
				} catch (Exception e) {
					continue;
				}
				srcF.setAccessible(true);
				Object obj = srcF.get(srcObj);
				if(obj == null){
					continue;
				}
				/*通过set方法赋值*/
				method.invoke(target, obj);
			}
		} catch (Exception e) {
			logger.error("fail to getObjFromAttrCopy",e);
		}
	}
	
	/**
	 * 获取字段名称，字段必须首字母小写
	 * 
	 * @param setMethodName
	 * @return
	 */
	public static String getFieldName(String setMethodName) {

		String tmpName = setMethodName.substring(3);
		/* 将第一个大写字母小写 */
		String firstChar = (tmpName.charAt(0) + "").toLowerCase();

		return firstChar + tmpName.substring(1);
	}
}
