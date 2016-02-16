package com.laipai.orderInfo.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.orderInfo.dto.OrderBean;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:36:37
 */
public interface IOrderService {

	public List queryAllOrders(HttpServletRequest request);

	public LpUser getUserName(String userId);

	public void cancelOrder(String orderId);

	public List queryOrderById(String galaryId);

	public List sortOrder(HttpServletRequest request,String id, String galaryId);
	
	public List queryOrderByUser(HttpServletRequest request,String userId);

	public List<LpOrder> queryOrderByCamera(HttpServletRequest request,String cameraId);

	public void deleteOrder(String orderId);

	public String addOrder(OrderBean orderBean);

	public LpOrder queryOrderByOrderId(String orderId);

	public void sendMessage(String messageTemplate, LpOrder order);


	
}
