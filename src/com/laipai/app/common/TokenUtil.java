package com.laipai.app.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 生成手机token
 * 
 * @author ts
 * 
 */
public class TokenUtil {

	/**
	 * 得到签名串
	 * 
	 * @param values
	 * @return
	 */
	public static String createToken(String mobile, String password, String mobileId) {
		Map<String, String> values = new TreeMap<String, String>();
		values.put("mobile", mobile);
		values.put("password", password);
		values.put("mobileId", mobileId);
		values.put("time", String.valueOf(System.currentTimeMillis()));

		String result = "";
		Iterator<String> it = values.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			String value = values.get(key);
			result += key + "=" + value;
		}

		result = result.replace("*", "%2A");
		result = toMD5Hex(result);

		return result;
	}

	private static String toMD5Hex(String datas) {
		StringBuilder sb = new StringBuilder();
		try {
			byte[] bt = datas.getBytes(Global.DATA_ENCODE);
			MessageDigest md = MessageDigest.getInstance("md5");
			bt = md.digest(bt);

			for (int i = 0; i < bt.length; i++) {
				String hex = Integer.toHexString(bt[i] & 0xFF);
				if (hex.length() <= 1) {
					sb.append('0');
				}
				sb.append(hex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	private static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, Global.DATA_ENCODE);
		} catch (UnsupportedEncodingException e) {
			return URLEncoder.encode(url);
		}
	}

}
