package com.lyz.api.bean;

import java.util.List;

/**
 * 显示拍摄风格列表
 * @author luyongzhao
 *
 */
public class ShowStyleListResp extends BaseResp {

	private List<Style> data;
	
	public ShowStyleListResp(int code){
		super(code);
	}
	
	public List<Style> getData() {
		return data;
	}



	public void setData(List<Style> data) {
		this.data = data;
	}


	/**
	 * 拍摄风格
	 * @author luyongzhao
	 *
	 */
	public static class Style{
		
		private String id;
		
		private String name;
		
		public Style(String id, String name){
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}
}
