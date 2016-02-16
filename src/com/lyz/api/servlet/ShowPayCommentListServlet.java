package com.lyz.api.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ShowPayCommentListReq;
import com.lyz.api.bean.ShowPayCommentListResp;
import com.lyz.api.bean.ShowPayCommentListResp.Cmt;
import com.lyz.service.CommentService;
/**
 * 展示评论列表
 * @author luyongzhao
 *
 */
public class ShowPayCommentListServlet extends BaseServlet {

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
		
		return ShowPayCommentListReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ShowPayCommentListReq req = (ShowPayCommentListReq)param;
		
		List<Cmt> cmtList = CommentService.getPayCmtList(req.getCameraId(), req.getPageNo(), req.getPageSize());
		
		ShowPayCommentListResp resp = new ShowPayCommentListResp();
		resp.setCmtList(cmtList);
		resp.setResult(CodeUtil.SUCCESS);
		
		return resp;
	}

}
