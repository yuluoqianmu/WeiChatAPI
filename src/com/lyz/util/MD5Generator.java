package com.lyz.util;

import java.security.MessageDigest;

public class MD5Generator {
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String args[]) {
//		System.out.println(MD5Generator.MD5("http://v.pps.tv/play_36G11N.html#from_baidu"));
		long time = System.currentTimeMillis();
		System.out.println(time);
		System.out.println(MD5Generator.MD5("laipai1431924939628"));
//		System.out.println(MD5Generator.MD5("appid=wxef9d0a6852f60f81&body=测试支付&mch_id=1237233202&nonce_str=123&notify_url=http://test.ilaipai.com/LaiPai/alipay&out_trade_no=11&spbill_create_ip=223.72.141.97&total_fee=1&trade_type=APP&key=41333DC0691A94977CC7D6314DE45D59"));
//		System.out.println(MD5Generator.MD5("appid=wxef9d0a6852f60f81&body=测试支付&mch_id=1237233202&nonce_str=123&notify_url=http://test.ilaipai.com/LaiPai/alipay&out_trade_no=11&spbill_create_ip=223.72.141.97&total_fee=1&trade_type=APP&key=41333DC0691A94977CC7D6314DE45D59"));
	}
}
