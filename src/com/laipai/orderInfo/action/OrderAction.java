package com.laipai.orderInfo.action;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.util.SendMessageTool;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.service.IOrderService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:34:22
 */
@Controller("orderAction")
public class OrderAction extends BaseAction {
	@Resource(name="orderService")
	private IOrderService orderService;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	 
	/**
	 * 
	
	 * @Description:订单管理——查询所有订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-17 下午1:33:12
	 */
	public String queryAllOrders(){
		List orderInfo=orderService.queryAllOrders(request);
		request.setAttribute("orderinfo", orderInfo);
		return "orderList";
	}
	
	/**
	 * 
	
	 * @Description:订单管理——取消订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-17 下午6:07:05
	 */
	public String cancelOrder(){
		String orderId=request.getParameter("orderId");
		orderService.cancelOrder(orderId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:订单管理——根据作品集id查询订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 上午10:18:47
	 */
	public String queryOrderByGalaryId(){
		String galaryId =request.getParameter("galaryId");
		List orderInfo=orderService.queryOrderById(galaryId);
		request.setAttribute("galaryId", galaryId);
		request.setAttribute("orderinfo", orderInfo);
		return "orderList";
	}
	/**
	 * 
	
	 * @Description:订单管理管理——按状态/摄影师/预订人排序
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-18 下午2:25:45
	 */
	public String sortOrder(){
		//id：状态（status） 摄影师(camera_id) 预订人(user_id)
		String id=request.getParameter("id");
		//分页的时候，将排序条件放在session中
		String sortId="";
		if(StringUtils.isNotBlank(id)){
			request.getSession().setAttribute("sortId", id);
		}
		  try {
			sortId=request.getSession().getAttribute("sortId").toString();
		} catch (Exception e) {
			sortId="";
		}finally{
			//是否在作品集中查看订单
			String galaryId=request.getParameter("galaryId");
			List orderInfo=new ArrayList();
			if("null".equals(galaryId)||galaryId==null){
				 orderInfo=orderService.sortOrder(request,sortId,"");
			}else{
				 orderInfo=orderService.sortOrder(request,sortId,galaryId);
			}
		 	request.setAttribute("galaryId", galaryId);
			request.setAttribute("orderinfo", orderInfo);
			return "orderList";
		}
	}
	
	/**
	 * 
	
	 * @Description:用户管理——根据用户ID查询预约
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 下午1:51:11
	 */
	public String queryOrderByUser(){
		String userId=request.getParameter("userId");
		LpUser user=userManService.queryUserById(userId);
		List orderInfo=orderService.queryOrderByUser(request,userId);
		request.setAttribute("user", user);
		request.setAttribute("userId", user.getUserId());
		request.getSession().setAttribute("orderInfo", orderInfo);
		return "user_orderlist";
	}
	
	/**
	 * 
	
	 * @Description:根据摄影师ID查询订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午4:15:11
	 */
	public String queryOrderByCamera(){
		String cameraId=request.getParameter("userId");
		LpUser user=userManService.queryUserById(cameraId);
		List list=orderService.queryOrderByCamera(request,cameraId);
		request.setAttribute("user", user);
		request.setAttribute("userId", cameraId);
		request.setAttribute("orderInfo", list);
		return "camera_orderlist";
	}
	
	/**
	 * 
	
	 * @Description:用户管理——根据订单ID删除订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 下午7:49:13
	 */
	public String deleteOrderByUser(){
	   String orderId=request.getParameter("orderId");
	   orderService.deleteOrder(orderId);
	    return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:摄影师管理——根据订单ID删除订单
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午4:20:33
	 */
	public String deleteOrderByCamera(){
		orderService.deleteOrder(request.getParameter("orderId"));
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:订单管理——系统回复
	
	
	 * void
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-19 上午10:38:05
	 */
//	public void sendMessage(){
//	 	String mobile=request.getParameter("mobile");
//		String message="对不起，您的预约无法实现，系统已帮您取消预约";
//	  	boolean sendSuccess=SendMessageTool.sendSMS(mobile, message);
//		PrintWriter info=getPrintWriter();
//		if(sendSuccess){
//			info.print("yes");
//		}else{
//			info.print("no");
//		}
//	}
	public String toSendMessagePage(){
		String orderId=request.getParameter("orderId");
		LpOrder order = orderService.queryOrderByOrderId(orderId);
		request.setAttribute("order", order);
		String messageTemplate = "对不起，您的预约无法实现，系统已帮您取消预约";
		request.setAttribute("messageTemplate", messageTemplate);
		return "toSendMessagePage";
	}
	
	/**
	 * 短信发送
	 * */
	public String sendMessage(){
		String orderId=request.getParameter("orderId");
		String messageTemplate=request.getParameter("messageTemplate");
		
		LpOrder order = orderService.queryOrderByOrderId(orderId);
		orderService.sendMessage(messageTemplate,order);
		return "cancelSuccess";
	}
	
	
	
}
