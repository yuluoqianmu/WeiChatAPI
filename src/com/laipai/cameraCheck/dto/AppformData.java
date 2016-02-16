package com.laipai.cameraCheck.dto;

/**

 * @Description:TODO

 * @author:lxd

 * @time:2014-12-25 上午11:43:58

 */
public class AppformData implements java.io.Serializable  {

	/** 
	 * @Fields serialVersionUID : TODO
	 */  
	
	private static final long serialVersionUID = 1L;
	
	 private Integer await;
	 private Integer pass;
	 private Integer fail;
	public Integer getAwait() {
		return await;
	}
	public void setAwait(Integer await) {
		this.await = await;
	}
	public Integer getPass() {
		return pass;
	}
	public void setPass(Integer pass) {
		this.pass = pass;
	}
	public Integer getFail() {
		return fail;
	}
	public void setFail(Integer fail) {
		this.fail = fail;
	}
	
	
}
