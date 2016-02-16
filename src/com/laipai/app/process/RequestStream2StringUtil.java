package com.laipai.app.process;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import com.laipai.app.common.Global;

/**
 * 把请求转成字符
 * 
 * @author lts
 * 
 */
public class RequestStream2StringUtil {

	public static String conver(HttpServletRequest request) {

		try {
			InputStream in = request.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			String value = new String(data, Global.DATA_ENCODE);

			return value;
		} catch (Exception e) {

		}
		return null;
	}
}
