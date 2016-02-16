package com.laipai.serviceInfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.dto.ServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.VLpService;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:30:26
 */
public interface IServiceService {
	
	public List queryAllService(HttpServletRequest request);

	public LpService queryById(String serviceId);
	
	public void addService(ServiceInfoBean serviceInfoBean);

	public void deleteById(String serviceId);

	public void modifyService(ServiceInfoBean serviceInfoBean);

	public void on_off_Service(String serviceId,String status);

	public List<LpStyle> getStyle(String userId);

	public List<VLpService> queryServiceByUserId(HttpServletRequest request,String userId);

	public LpUser queryUserByName(String cameraName);

	public List<LpUser> queryAllCamera();

	public List<LpStyle> getUserStyle(String userId);

	public int getCount(String gallaryId);


}
