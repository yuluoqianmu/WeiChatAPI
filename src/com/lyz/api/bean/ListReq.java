package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 列表请求
 * @author luzi
 *
 */
public class ListReq extends BaseReq {
	/**
	 *所属分组
	 */
	@ValidateNotNull(message="分组")
	@ValidateSize(minSize="1",maxSize="100",message="分组")
	private String gn;
	/*card rule Key*/
	@ValidateNotNull(message="规则")
	@ValidateSize(minSize="1",maxSize="100",message="规则")
	private String crk;
	/*标签操作类型:1-交集，2-并集，3-差集*/
	@ValidateLong(min=1,max=3,message="操作类型")
	private int ot;
	/**
	 * 多个标签通过加号连接：a+b
	 */
	@ValidateNotNull(message="标签")
	@ValidateSize(minSize="1",maxSize="100",message="分组")
	private String labs;

	@ValidateLong(min=1,max=100,message="页码")
	private int pn = 1;
	@ValidateLong(min=10,max=30,message="每页记录数")
	private int ps = 10;
	
	
	public String getGn() {
		return gn;
	}
	public void setGn(String gn) {
		this.gn = gn;
	}
	public String getLabs() {
		return labs;
	}
	public void setLabs(String labs) {
		this.labs = labs;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getOt() {
		return ot;
	}
	public void setOt(int ot) {
		this.ot = ot;
	}
	public String getCrk() {
		return crk;
	}
	public void setCrk(String crk) {
		this.crk = crk;
	}
	
	
	
	
	
	
}
