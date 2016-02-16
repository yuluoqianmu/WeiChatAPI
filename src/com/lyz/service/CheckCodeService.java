package com.lyz.service;

import org.apache.log4j.Logger;

import com.lyz.api.cache.OcsCache;

/**
 * 验证码服务
 * @author luzi
 *
 */
public class CheckCodeService {
	
	private static final Logger logger = Logger.getLogger(CheckCodeService.class);
	/*注册*/
	public static final int type_register = 1;
	/*找回密码*/
	public static final int type_find_pwd = 2;
	
	private static OcsCache cache = OcsCache.getInstance();
	/**
	 * 保存验证码
	 * @param phoneNum 手机号码
	 * @param type 验证类型
	 * @param checkCode 验证码
	 * @param timeout 超时时间
	 * @return
	 */
	public static boolean saveCheckCode(String phoneNum, int type, String checkCode, int timeout){
		
		String key = phoneNum+":"+type;
		try {
			cache.setData(key, checkCode, timeout);
		} catch (Exception e) {
			logger.error("fail to set check code!type="+type);
			return false;
		}
		
		return true;
	}
	/**
	 * 获取验证码
	 * @param phoneNum
	 * @param type
	 * @return
	 */
	public static String  getCheckCode(String phoneNum, int type){
		
		String key = phoneNum+":"+type;
		
		return cache.getData(key);
		
//		return "1118";
	}
	
	
}
