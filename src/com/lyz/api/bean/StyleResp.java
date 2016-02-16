package com.lyz.api.bean;

import java.util.List;

public class StyleResp extends BaseResp{
	
	private List<Style> data;
	
	public static class Style{
		/*风格id*/
		private String styleId;
		/*风格名称*/
		private String styleName;
		public String getStyleId() {
			return styleId;
		}
		public void setStyleId(String styleId) {
			this.styleId = styleId;
		}
		public String getStyleName() {
			return styleName;
		}
		public void setStyleName(String styleName) {
			this.styleName = styleName;
		}
		
		
	}
}
