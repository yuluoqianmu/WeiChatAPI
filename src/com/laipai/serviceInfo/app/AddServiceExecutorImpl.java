package com.laipai.serviceInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.serviceInfo.dto.ServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.LpServiceStyle;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
@NotLogin
@Service("AddServiceExecutorImpl")
public class AddServiceExecutorImpl implements RequestExecutor{
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	@Autowired
	private IServiceService serviceService;
	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		ServiceInfoBean bean=getServiceInfoBean(json);   
		if(bean==null){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		try {
			System.out.println("----------参数无误，开始添加");
//			serviceService.addService(bean);
			String returnValue = addService(json);
			if("errorStyleId".equals(returnValue)){
				return JSONTools.newAPIResult(1, "styleId为空,请传styleId值");
			}
			if("nullStyleId".equals(returnValue)){
				return JSONTools.newAPIResult(1, "添加错误,没有风格");
			}
			return JSONTools.newAPIResult(0, "添加服务成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "添加服务失败");
		}
	}
	
	private  ServiceInfoBean getServiceInfoBean(JSONObject json) {
		ServiceInfoBean bean=new ServiceInfoBean();
		//服务主题
		String serviceName=json.getString("serviceName");
		bean.setServiceName(serviceName);
		//当前状态(true:上架 false:下架)
		bean.setServiceStatus(true);
		//用户ID
		String cameraId=json.getString("userId");
		LpUser user=userManService.queryUserById(cameraId);
		bean.setCameraName(user.getUserName());
		//服务创建日期
		bean.setCreateTime(new Timestamp(new Date().getTime()));
		//风格
		String styleIdAll=json.getString("styleId");
		String[] style_id=styleIdAll.split(",");
		bean.setStyleId(style_id);
		//价格
		long price=json.getLong("price");
		bean.setPrice(price);
		//价格单位(按天算，按套算)
		String priceUnit=json.getString("priceUnit");
		bean.setPriceUnit(priceUnit);
		//拍摄天数
		String shootTime=json.getString("shoot_time");
		bean.setShoot_time(shootTime);
		//照片张数
		int pictureNum=Integer.parseInt(json.getString("picture_num"));
		bean.setPicture_num(pictureNum);
		//精修张数
		int truingNum=Integer.parseInt(json.getString("truing_num"));
		bean.setTruing_num(truingNum);
		//服装(自带 一——五套)
		String clothes=json.getString("clothes");
		bean.setClothes(clothes);
		//化妆(提供，不提供)
		String facepaint=json.getString("facepaint");
		bean.setFacepaint(facepaint);
		//附加说明
		String instructions=json.getString("instructions");
		bean.setInstructions(instructions);
		//参数判断，不能为空
		if(StringUtils.isEmpty(serviceName)||
		   StringUtils.isEmpty(cameraId)||
		   StringUtils.isEmpty(styleIdAll)||
		   StringUtils.isEmpty(priceUnit)||
		   StringUtils.isEmpty(clothes)||
		   StringUtils.isEmpty(facepaint)
		   ){
			return null;
		}
		return bean;
	}
	
	private String addService(JSONObject json){
		ServiceInfoBean bean=new ServiceInfoBean();
		
		LpService service = new LpService();
		LpServiceDetail detail = new LpServiceDetail();
		
		//服务主题
		String serviceName=json.getString("serviceName");
		//用户ID
		String cameraId=json.getString("userId");
		//风格
		String styleIdAll=json.getString("styleId");
		//价格
		long price=json.getLong("price");
		//价格单位(按天算，按套算)
		String priceUnit=json.getString("priceUnit");
		//拍摄天数
		String shootTime=json.getString("shoot_time");
		//照片张数
		int pictureNum=Integer.parseInt(json.getString("picture_num"));
		//精修张数
		int truingNum=Integer.parseInt(json.getString("truing_num"));
		//服装(自带 一——五套)
		String clothes=json.getString("clothes");
		//化妆(提供，不提供)
		String facepaint=json.getString("facepaint");
		//附加说明
		String instructions=json.getString("instructions");
		
		//保存service
		LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, cameraId);
		service.setLpUser(user);
		service.setOrderNum(0);
		service.setServiceName(serviceName);
		service.setServiceStatus(true);
		service.setCreateTime(DateUtils.dateToTimestamp(new Date()));
		Serializable serviceId = baseDao.saveObjectReturnId(service);
		//保存详情
		detail.setPictureNum(pictureNum);
		detail.setInstructions(instructions);
		detail.setFacepaint(facepaint);
		detail.setShootTime(shootTime);
		detail.setPrice(price*100);
		detail.setClothes(clothes);
		detail.setTruingNum(truingNum);
		detail.setPriceUnit(priceUnit);
		detail.setLpService(service);
		Serializable detailId = baseDao.saveObjectReturnId(detail);
		//保存服务风格中间表
		if(StringUtils.isEmpty(styleIdAll)){
			return "errorStyleId";
		}
		String[] styleIdArr = styleIdAll.split(",");
		if(styleIdArr.length == 0){
			return "nullStyleId";
		}
		for(int i=0;i<styleIdArr.length;i++){
			LpServiceStyle serviceStyle = new LpServiceStyle();
			serviceStyle.setStyleId(styleIdArr[i]);
			serviceStyle.setDetailId(detailId.toString());
			baseDao.save(serviceStyle);
		}
		
		return "success";
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
