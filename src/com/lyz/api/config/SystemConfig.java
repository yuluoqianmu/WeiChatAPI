package com.lyz.api.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 系统配置，包括接口配置的一些信息
 * @author luzi
 *
 */
public class SystemConfig {
	
	private static final Logger logger = Logger.getLogger(SystemConfig.class);
	/*初始化接口吐给客户端的key*/
	public static String key = "laipai";
	/*是否开启缓存机制，用于调试和开发*/
	public static boolean openCache = false;
	/*请求是否有过期*/
	public static boolean isReqExpired = false; 
	/*配置文件kv存储*/
	private static Map<String,Long> key2Val = new HashMap<String, Long>();
	
	public static String env = "prod";
//	public static String env = "test";
	
	static{
		/*缓存过期时间，-1表示不设置缓存，0-缓存不过期，>0表示过期秒数*/
		key2Val.put("cacheTimeout", -1l);
		/*请求过期时间*/
		key2Val.put("reqTimeout", 0l);
	}
	
//	public static void setConfig(String key, Long val){
//		
//		Object obj = key2Val.get(key);
//		if(obj == null){
//			logger.error("no config for "+key);
//			return;
//		}
//		
//		key2Val.put(key, val);
//	}
	/**
	 * 缓存过期时间，-1表示不设置缓存，0-缓存不过期，>0表示过期秒数
	 * @return
	 */
	public Long getCacheTimeout(){
		
		return key2Val.get("cacheTimeout");
	}
	/**
	 * 0-表示不过期，>0表示过期秒数
	 * @return
	 */
	public Long getReqTimeout(){
		
		return key2Val.get("reqTimeout");
	}
	
}
