package com.laipai.base.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.laipai.app.common.JSONConvertUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImageBase64Util {
	
	public static String getImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
		in = new FileInputStream(imgFile);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

		/**
		* 将字符串转为图片
		* @param imgStr
		* @return
		*/
		public static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
			if (imgStr == null) // 图像数据为空
			return false;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				// 生成jpeg图片
				String imgFilePath = imgFile;// 新生成的图片
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
				return true;
			} catch (Exception e) {
				throw e;
			}
		}
		
		
}
