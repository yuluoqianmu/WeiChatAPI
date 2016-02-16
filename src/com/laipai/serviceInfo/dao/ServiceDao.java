package com.laipai.serviceInfo.dao;

import java.util.List;
import java.util.Map;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:30:04
 */
public interface ServiceDao {

	public void addService(LpService service);

	public void modifyService(LpService service);

	public void on_off_Service(LpService service);

	public List<LpStyle> getStyle(String userId);

	public void deleteService(LpService service);

 	public LpService queryService(String serviceId);

	public List<LpGalary> queryGalary(String serviceId);
	
	List<LpService> getListBySql(String hqlString, Object... values);

	public LpUser queryUserByName(String cameraName);

	public List<LpService> queryListByHql(String string,String userId);


}
