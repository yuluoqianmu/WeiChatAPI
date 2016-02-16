package com.lyz.api.bean;

import java.util.List;

/**
 * 获取城市响应
 * @author luzi
 *
 */
public class CityResp extends BaseResp {
	
	private List<City> data;
	
	
	public static class City{
		/*城市id*/
		private String cityId;
		/*城市名称*/
		private String cityName;
		public String getCityId() {
			return cityId;
		}
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
		
		
	}

	public List<City> getData() {
		return data;
	}

	public void setData(List<City> data) {
		this.data = data;
	}
	
	
}
