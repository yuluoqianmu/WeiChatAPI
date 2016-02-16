package com.lyz.api.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ShowStyleListReq;
import com.lyz.api.bean.ShowStyleListResp;
import com.lyz.api.bean.ShowStyleListResp.Style;
import com.lyz.api.cache.ICache;
import com.lyz.api.cache.OcsCache;
import com.lyz.service.StyleService;
/**
 * 返回拍摄风格列表
 * @author luyongzhao
 *
 */
public class ShowStyleListServlet extends BaseServlet {
	
	
	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return null;
	}
	
	@Override
	public String getKey4Cache(String uri, String queryString){
		
		return "styleList";
	}

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
		
		return ShowStyleListReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		List<Style> sList = StyleService.getStyleList();
		
		ShowStyleListResp resp = new ShowStyleListResp(CodeUtil.SUCCESS);
		resp.setData(sList);
		
		return resp;
	}

}
