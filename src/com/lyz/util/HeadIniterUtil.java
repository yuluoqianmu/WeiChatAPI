package com.lyz.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lyz.api.bean.Head;

/**
 * 初始化协议头
 * @author luyongzhao
 *
 */
public class HeadIniterUtil {
	
	public static List<Head> headList = null;
	
	public static List<Head> getHeadList(){
		
		if(headList != null){
			return null;
		}
		
		headList = new ArrayList<Head>();
		/*百度地址*/
		Head baiduHead = new Head();
		baiduHead.setTag("baidu");
		Map<String, String> baiduMap = new HashMap<String, String>();
		/*设定参数*/
		baiduMap.put("User-Agent", "android");
		baiduMap.put("Accept-Encoding", "gzip");
		baiduHead.setName2Val(baiduMap);
		
		/*h5 请求*/
		Head h5Head = new Head();
		h5Head.setTag("h5");
		Map<String, String> h5Map = new HashMap<String, String>();
		h5Map.put("User-Agent", "android");
		h5Map.put("Accept-Encoding", "gzip");
		h5Head.setName2Val(h5Map);
		
		headList.add(baiduHead);
		headList.add(h5Head);
		
		return headList;		
	}
}
