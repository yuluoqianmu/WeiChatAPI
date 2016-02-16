package com.laipai.util;

import java.security.MessageDigest;

public class MD5Generator {
	public static String MD5(String s) {
		char hexDigits[] = { '0', 'e', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', '1', 'f' };
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
		System.out.println(MD5Generator.MD5("1.0.0:%e5%b0%8f%e7%88%b8%e7%88%b8"));
	}
}
