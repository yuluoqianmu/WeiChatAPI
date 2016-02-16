package com.lyz.api.servlet;

import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.api.bean.ApplyClubReq;
import com.lyz.api.bean.ApplyClubResp;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.db.bean.LPApplyClub;
import com.lyz.service.ApplyClubService;

public class ApplyClubServlet extends BaseServlet{
	private static final Logger logger = Logger.getLogger(ApplyClubServlet.class);
	/**
	 * 覆盖父类的方法，获取post请求的参数对
	 */
	public BaseReq getJsonData(HttpServletRequest req, Class clazz){
		
		return initParam4Post(req, clazz);
	}
	@Override
	public Class getParamClass() {
		return ApplyClubReq.class;
	}
	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return null;
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
	public Object handler(BaseReq param,HttpServletRequest request) {
		ApplyClubReq req = (ApplyClubReq)param;
		LPApplyClub applyClub = new LPApplyClub();
		
		String userName = req.getUserName();//姓名
		String sex = req.getSex();//性别
		String phoneNum = req.getPhoneNum();//电话号
		String wechat = req.getWechat();//微信号
		String city = req.getCity();//城市
		String cameraType = req.getCameraType();//相机型号
		String grapherDesc = req.getChk_value();//拍摄风格
		
		//先校验数据库中是否存在该摄影师
		
		boolean isExit = ApplyClubService.checkExit(phoneNum);
		
//		int count = ApplyClubService.getCount();
		
		if(isExit){
			return new ApplyClubResp(CodeUtil.SUCCESS,0,userName,"您已成功加入，请不要再重复加入！");
		}else{
			
		applyClub.setCameraName(userName);
		applyClub.setGender(Integer.parseInt(sex));
		applyClub.setPhoneNum(phoneNum);
		applyClub.setWechat(wechat);
		applyClub.setCityName(city);
		applyClub.setGrapherCarmer(cameraType);
		applyClub.setGrapherDesc(grapherDesc);
		applyClub.setCreateTime(System.currentTimeMillis());
		
		String id = ApplyClubService.saveApplyClub(applyClub);
		if(id == null){
			logger.error("加入摄影师失败！ApplyClubServlet");
			return new BaseResp(CodeUtil.SERVER_ERROR,"加入摄影师失败");
		}
		
		return new ApplyClubResp(CodeUtil.SUCCESS,0,userName,"");
		}
	}
}
