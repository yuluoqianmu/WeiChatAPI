package com.laipai.orderInfo.dao;

import java.util.List;

import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;


/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:50:21
 */
public interface OrderDao {

	public LpUser getUserName(String userId);

	public void cancelOrder(String orderId);

	public List queryOrderById(String galaryId);

	public List queryOrderByUserId(String userId);

	public List<LpOrder> queryOrderByService(String serviceId);

	public void updateOrder(LpOrder order);

	public void deleteOrder(LpOrder order);

	public LpOrder queryOrderByOrderId(String orderId);

	public void addOrder(LpOrder order);

	public int getUserOrderNumber(String string);

}
