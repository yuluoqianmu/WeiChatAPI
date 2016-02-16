package com.laipai.base.util;

public class UserUtil {
	
	/**
	 * 获取用户名称，对于特定角色需要添加前缀
	 * @param userName 真实的用户名称
	 * @param role
	 * @return
	 */
	public static String getUserName(String userName, int role){
		
		/*摄影师需要添加前缀*/
		if(role == 1){
			
			return "摄影师:"+userName;
		}
		
		return userName;
	}
}
