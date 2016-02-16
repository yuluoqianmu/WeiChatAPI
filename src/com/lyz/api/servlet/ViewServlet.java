package com.lyz.api.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ViewReq;
import com.lyz.api.bean.ViewResp;
import com.lyz.labelData.bean.BaseData;
import com.lyz.labelData.bean.ViewData;
import com.lyz.labelData.oper.ICardOper;
import com.lyz.labelData.oper.IViewOper;
import com.lyz.labelData.oper.impl.DefaultCardOperImpl;
import com.lyz.labelData.oper.impl.DefaultViewOperImpl;
/**
 * 视图处理类包含综合类数据请求
 * @author luzi
 *
 */
public class ViewServlet extends BaseServlet {
	
	private IViewOper viewOper = null;

	@Override
	public void init() throws ServletException {
		
		viewOper = ViewOperUtil.getInstance().getViewOper();
	}

	@Override
	public String getNeed2Md5(BaseReq param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getParamClass() {
		
		return ViewReq.class;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ViewReq req = (ViewReq)param;
		
		if(req.getVid() == null || "".equals(req.getVid().trim())){
			
			return new BaseResp(CodeUtil.INVALID_VIEW_ID);
		}
		/*组装视图数据*/
		ViewData data = viewOper.getViewData(req.getVid());
		
		/*过滤数据*/
		filterViewData(data);
		
		int rCode = CodeUtil.SUCCESS;
		
		if(data == null){			
			rCode = CodeUtil.SERVER_ERROR;
		}
		
		return new ViewResp(rCode,data);
	}
	
	/**
	 * 过滤掉所有不希望在前端展示的数据
	 * @param data
	 */	
	public void filterViewData(final ViewData data){
		
		//nothing to do
	}

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
