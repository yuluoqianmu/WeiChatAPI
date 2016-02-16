package com.laipai.userManInfo.util;

import org.apache.commons.lang3.StringUtils;

import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;

public class Md5utils {
	public static void md5Password(LpUser elecUser) {
		//获取加密前的密码
		String logonPwd = elecUser.getUserPassword();
		//指定加密后加密
		String md5LogonPwd = "";
		//如果密码没有填写，默认123（初始密码）
		if(StringUtils.isBlank(logonPwd)){
			logonPwd = "123";
		}
		//获取用来存放之前的密码（数据库对应的密码）
		String password = elecUser.getUserPassword();
		//数据中的密码（password）和修改后的密码要是一致，没有修改密码
		if(password!=null && password.equals(logonPwd)){
			md5LogonPwd = logonPwd;
		}
		else{
			//对密码进行加密
			MD5keyBean bean = new MD5keyBean();
			md5LogonPwd = bean.getkeyBeanofStr(logonPwd);
		}
		//将加密后的密码设置到ElecUser对象中
		elecUser.setUserPassword(md5LogonPwd);
		
	}
}
