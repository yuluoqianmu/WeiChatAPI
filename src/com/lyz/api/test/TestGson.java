package com.lyz.api.test;

import com.laipai.cameraManInfo.action.CameraManAction.GalaryCacheData;
import com.lyz.labelData.json.GsonExt;

public class TestGson {
	
	public static void main(String args[]){
		
		GsonExt gson = new GsonExt();
		
		GalaryCacheData data = new GalaryCacheData(null, 1, null, 1, "aaa");
		System.out.println(gson.toJson(data));
	}
}
