package com.lyz.api.bean;
/**
 * 作品集列表展示请求类
 * @author luzi
 *
 */
public class GalleryShowReq extends BaseReq {
	/*城市id*/
	private String cityId;
	/*风格id*/
	private String styleId;
	/*页码*/
	private int pn=1;
	/*每页展示最大数量*/
	private int ps=20;
	
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		
		if(pn <= 0){
			
			pn = 1;
		}
		this.pn = pn;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		
		if(ps <= 0){
			
			ps = 20;
		}
		
		this.ps = ps;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	
	
}
