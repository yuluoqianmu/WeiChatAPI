package com.lyz.api.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 请求头
 * @author luyongzhao
 *
 */
public class Head implements Serializable{
	/*baidu,bdhd,other,h5*/
	private String tag;
	
	private Map<String,String> name2Val;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Map<String, String> getName2Val() {
		return name2Val;
	}

	public void setName2Val(Map<String, String> name2Val) {
		this.name2Val = name2Val;
	}
	
	
}
