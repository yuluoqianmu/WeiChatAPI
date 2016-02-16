package com.lyz.api.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.lyz.api.bean.ShowOrderListResp;
import com.lyz.labelData.json.GsonExt;
import com.lyz.util.BaseTypeUtil;

public class FileTest {
	
	public static void main(String args[]){
		
//		System.out.println(\"/Laipai/show\".replaceAll(\"/\", \"_\"));
//		System.out.println(BaseTypeUtil.getStrDateTime(1432017642973l,\"yyyy-MM-dd HH:mm:ss\"));
//		GsonExt gson = new GsonExt();
//		ShowOrderListResp resp = gson.fromJson("{\"result\":0,\"orderList\":[{\"userName\":\"柳叶\",\"latestStatus\":{\"status\":1,\"operTime\":\"2015.05.19 11:20\",\"statusDesc\":\"已支付定金\",\"operList\":[{\"orderStatus\":\"APPLY_REFUND\",\"operDesc\":\"申请退款\"}]},\"userHeadImg\":\"http://img.ilaipai.com/1429416060098010@100w_100h.webp\",\"servicePrice\":0,\"orderId\":\"0\",\"serviceName\":\"云南\",\"userId\":\"8a2a76634c5af695014c5b3f9f570043\"}]}", ShowOrderListResp.class);
		
//		System.out.println(System.currentTimeMillis()%1000*719759154+"");
		
		try {
			InputStream in = new FileInputStream(new File("e://test/test.txt"));

			int len = -1;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			String value = new String(data, "gbk");
			System.out.println(value);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
