package com.lyz.api.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.DetailReq;
import com.lyz.api.bean.ViewResp;
import com.lyz.labelData.bean.ViewData;
import com.lyz.labelData.bean.rule.CardRule;
import com.lyz.labelData.oper.IViewOper;

public class DetailServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(DetailServlet.class);
	
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
		
		return DetailReq.class;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		DetailReq req = (DetailReq)param;
		
//		if(req.getVid() == null || "".equals(req.getVid().trim())){
//			
//			return new BaseResp(CodeUtil.INVALID_VIEW_ID);
//		}
//		
//		if(req.getSdk() == null || "".equals(req.getSdk().trim())){
//			
//			return new BaseResp(CodeUtil.INVLIAD_SINGLE_DATA_KEY);
//		}
//		
//		if(req.getGn()==null || "".equals(req.getGn().trim())){
//			
//			return new BaseResp(CodeUtil.INVLID_GROUP_NAME);
//		}
		/*获取规则列表*/
		List<CardRule> ruleList = viewOper.getCardRule(req.getVid());
		if(ruleList==null || ruleList.isEmpty()){
			logger.error("not card rule list for view id:"+req.getVid());
			return new BaseResp(CodeUtil.INVALID_VIEW_ID);
		}
		Map<String,CardRule> groupName2Rule = new HashMap<String, CardRule>();
		/*修改规则，如果是详情，则修改id，如果是列表，则修改标签*/
		for(CardRule rule : ruleList){
			
			if(rule.getType() == CardRule.RULE_TYPE_DETAIL){
				rule.setSingleDataKey(req.getSdk());
			}else{
				rule.setLabelList(new String[]{req.getSdk()});
				rule.setType(CardRule.RULE_TYPE_INTER);
			}
			
			groupName2Rule.put(rule.getGroupName(), rule);
		}
		/*获取数据*/
		ViewData data = viewOper.getViewData(groupName2Rule);
		
		if(data == null){
			logger.warn("fail to get list data:"+gson.toJson(req));
			return new BaseResp(CodeUtil.SERVER_ERROR);
		}
		
		return new ViewResp(CodeUtil.SUCCESS, data);
		
		
	}

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
