package com.laipai.app.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public static String md5Encode(String str) {
		MessageDigest digest = null;

		try {

			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(str.getBytes("UTF-8"));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = digest.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				buf.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				buf.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return buf.toString().toUpperCase();
	}

}
