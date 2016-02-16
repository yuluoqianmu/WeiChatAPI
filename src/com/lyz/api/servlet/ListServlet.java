package com.lyz.api.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ListReq;
import com.lyz.api.bean.ViewResp;
import com.lyz.labelData.bean.ViewData;
import com.lyz.labelData.bean.rule.CardRule;
import com.lyz.labelData.oper.ICardOper;
import com.lyz.labelData.oper.IViewOper;

/**
 * 列表处理类
 * @author luzi
 *
 */
public class ListServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(ListServlet.class);
	
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
		
		return ListReq.class;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ListReq req = (ListReq)param;
		/*合法性检验*/
//		if(req.getGn()==null || "".equals(req.getGn().trim())){
//			
//			return new BaseResp(CodeUtil.INVLID_GROUP_NAME);
//		}
//		
//		if(req.getCrk()==null || "".equals(req.getCrk().trim())){
//			
//			return new BaseResp(CodeUtil.INVLIAD_CARD_RULE_KEY);
//		}
//		
//		if(req.getOt()<=0 || req.getOt()>=4){
//			
//			return new BaseResp(CodeUtil.INVALID_OPER_TYPE);
//		}
//		
//		if(req.getLabs()==null || "".equals(req.getLabs())){ 
//			
//			return new BaseResp(CodeUtil.INVALID_LABELS);
//		}
//		
//		if(req.getPs() <= 0){
//			
//			return new BaseResp(CodeUtil.INVALID_PAGE_SIZE);
//		}
//		
//		if(req.getPn() <= 0){
//			
//			return new BaseResp(CodeUtil.INVLIAD_PAGE_NUM);
//		}
		
		/**=====程序逻辑===================================*/
		
		/*获取规则*/
		ICardOper cardOper = viewOper.getCardOper(req.getGn());
		/*如果规则为空，则直接返回错误信息*/
		if(cardOper == null){
			logger.warn("no cardOper for "+req.getGn());
			return new BaseResp(CodeUtil.INVLID_GROUP_NAME);
		}
		
		/*获取card规则实例*/
		CardRule rule = cardOper.getCardRule(req.getCrk());
		/*如果获取不到规则，则返回错误信息*/
		if(rule == null){
			logger.warn("no card rule for "+req.getCrk());
			return new BaseResp(CodeUtil.INVLIAD_CARD_RULE_KEY); 
		}
		/*动态替换规则标签，获取数据*/
		rule.setLabelList(req.getLabs().split("+"));
		rule.setType(req.getOt());
		rule.setPageNum(req.getPn());
		rule.setNum(req.getPs());
		
		/*获取数据*/
		
		ViewData data = viewOper.getViewData(req.getGn(),rule);
		
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
