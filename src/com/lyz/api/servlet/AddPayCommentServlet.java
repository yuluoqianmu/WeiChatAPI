package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.AddPayCommentReq;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.labelData.util.BaseTypeUtil;
import com.lyz.service.CommentService;
/**
 * 下单完成，去评价摄影师服务
 * @author luyongzhao
 *
 */
public class AddPayCommentServlet extends BaseServlet {

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
		
		return AddPayCommentReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		AddPayCommentReq req = (AddPayCommentReq)param;
		
		long orderId = BaseTypeUtil.getLong(req.getOrderId(), -1);
		/*判断是否添加过评论，添加过就不能再次添加*/
		int count = CommentService.getPayCmtCount(orderId, req.getUserId());
		if(count > 0){
			return new BaseResp(CodeUtil.CLIENT_ERROR, "已添加评论，不能再次添加");
		}
		/*插入评论信息*/
		count = CommentService.addPayComment(req);
		
		if(count <= 0){
			
			return new BaseResp(CodeUtil.SERVER_ERROR, "评论失败");
		}
		
		return new BaseResp(CodeUtil.SUCCESS);
	}

}
