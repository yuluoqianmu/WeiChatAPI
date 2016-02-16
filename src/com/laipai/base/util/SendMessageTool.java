package com.laipai.base.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class SendMessageTool {

	/**
	 * command :   sendSMS-使用明码密码 ; sendSMSMD5-使用md5加密的密码
	 * 
	 * 
	 * */
	public static boolean sendSMS(String mobile,String vercode) {
		try {
			String smsContent = vercode; //短信内容
			String mobiles = mobile; //手机号码

			HashMap hashmap = new HashMap();
			hashmap.put("command", "sendSMS");
			hashmap.put("username", "ypAdmin");
			hashmap.put("pwd", "737227eeq");
			hashmap.put("extCode", ""); //扩展码
			hashmap.put("incode", "GBK");
			hashmap.put("outcode", "GBK");
			hashmap.put("rstype", "text");
			hashmap.put("content", smsContent);
			hashmap.put("mobiles", mobiles);
			String strUrl = "http://www.qymas.com/smsSendServlet.htm";

			send(strUrl, hashmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 公共发送方法
	 * */
	public static int send(String url, HashMap hashmap) {
		System.out.println(url);
		int statusCode = 0;

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		method.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=GBK");

		java.util.Date oldTime = new java.util.Date();
		java.util.Date curTime = new java.util.Date();

		try {
			// 设置基本参数
			java.util.Iterator it = hashmap.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
				String name = (String) entry.getKey(); // 返回与此项对应的属性
				String value = (String) entry.getValue(); // 返回与此项对应的值
				System.out.println(name + " : " + value);

				method.addParameter(name, value);
				//method.getParams().setParameter(name, value);
			}

			// Execute the POST method
			statusCode = client.executeMethod(method);

			// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理
			// 301或302
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
				// 从头中取出转向的地址
				Header locationHeader = method.getResponseHeader("location");
				String location = null;
				if (locationHeader != null) {
					location = locationHeader.getValue();
					System.out.println("The page was redirected to:" + location);
				} else {
					System.out.println("Location field value is null.");
				}

			} else {
				String status = method.getResponseBodyAsString();
				System.out.println("返回信息=" + new String(status.getBytes("ISO-8859-1"),"GBK"));
			}
			// Close connection.
			method.releaseConnection();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		curTime = new java.util.Date();

		System.out.println("访问接口,耗时=" + (curTime.getTime() - oldTime.getTime())
						+ "毫秒");

		return statusCode;
	}
	
	//生成验证码
	public static String getRandomNum(){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < 6; i++){
		    result = result * 10 + array[i];
		}  
		String returnValue = result+"";
		returnValue = returnValue.length()<6?("0"+returnValue):returnValue;
		
		return returnValue;
	}
	
}
