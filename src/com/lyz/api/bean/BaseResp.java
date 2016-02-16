package com.lyz.api.bean;

import java.io.Serializable;

/**
 * 基础响应类，所有的响应类都需要继承该类
 * @author luyongzhao
 *
 */
public class BaseResp implements Serializable{
	/*返回码：0-成功，其他失败*/
	private int result;
	/*返回提示消息*/
	private String message;
	
	public BaseResp(){
		
	}
	
	public BaseResp(int result){
		
		this(result, null);
	}
	
	public BaseResp(int result, String message){
		this.result = result;
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		this.message = msg;
	}

	public String toString(){
		
		return "";
	}
}
