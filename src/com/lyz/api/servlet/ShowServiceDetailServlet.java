package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import antlr.StringUtils;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ShowServiceDetailReq;
import com.lyz.api.bean.ShowServiceDetailResp;
import com.lyz.service.ServiceService;

public class ShowServiceDetailServlet extends BaseServlet {

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getParamClass() {
		
		return ShowServiceDetailReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ShowServiceDetailReq req = (ShowServiceDetailReq)param;
		
		/*默认服务*/
		if("1111111111081".equals(req.getServiceId())){
			
			if(req.getCameraId()==null || "".equals(req.getCameraId().trim())){
				
				return new BaseResp(CodeUtil.CLIENT_ERROR, "需要获取默认服务时，cameraId不能为空!");
			}
		}
		
		ShowServiceDetailResp resp = ServiceService.getServiceShowDetail(req.getServiceId(),req.getCameraId());
		
		if(resp == null){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR, "获取服务详情失败");
		}
		
		return resp;
	}

}
