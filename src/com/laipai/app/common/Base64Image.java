package com.laipai.app.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * base 64编码
 * 
 * @author ts
 * @date 2014-4-11
 */
public class Base64Image {

	public static void checkStorePath(String path) {
		File f = new File(path);
		if (!f.exists()) f.mkdirs();
	}

	/**
	 * 图片转base64
	 * 
	 * @param in
	 * @return
	 */
	public static String encodeImage(InputStream in) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * 图片转base64
	 * 
	 * @param file
	 * @return
	 */
	public static String encodeImage(String file) {
		InputStream in = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(file);
			return encodeImage(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}

	/**
	 * 写入文件，如果已存在则删除
	 * 
	 * @param storePath
	 * @param imgStr
	 * @return
	 */
	public static boolean decodeImage(String storePath, String imgStr) {
		if (imgStr == null) return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;

		File file = new File(storePath);
		if (file.exists()) file.delete();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			out = new FileOutputStream(storePath);
			out.write(b);
			out.flush();
			return true;
		} catch (Exception e) {
			if (Global.DEBUG) {
				e.printStackTrace();
			}
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
