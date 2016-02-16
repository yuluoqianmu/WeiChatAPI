package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.GalleryShowReq;

public class GalleryShowServlet extends BaseServlet {

	@Override
	public String getNeed2Md5(BaseReq param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getParamClass() {
		return GalleryShowReq.class;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		GalleryShowReq req = (GalleryShowReq)param;
		
		return null;
		
	}

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
