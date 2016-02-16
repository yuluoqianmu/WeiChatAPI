package com.laipai.orderInfo.service.imple;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.SendMessageTool;
import com.laipai.base.util.ShortMessageUtil;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.orderInfo.dto.OrderBean;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.pojo.VLpOrder;
import com.laipai.orderInfo.service.IOrderService;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dao.ICommentDao;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:50:41
 */
@Repository("orderService")
public class OrderServiceImple extends BaseServiceImpl implements IOrderService{
	@Resource(name="orderDao")
	private OrderDao orderDao;
	@Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
	private IUserManInfoDao userManDao;
	@Resource(name=IGalleryDao.DAO_NAME)
	private IGalleryDao galleryDao;
	@Resource(name="serviceDao")
	private ServiceDao serviceDao;
	@Resource(name=ICommentDao.COMENTDAO_NAME)
	private ICommentDao commentDao;
	@Resource(name="baseDao")
	private IBaseDao baseDao;
	
	@Override
	public List queryAllOrders(HttpServletRequest request) {
		
		String hql="from VLpOrder order by createTime";
		try {
			List<LpOrder> orderInfo=querylistForPage(request, hql, 20);
			return orderInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public LpUser getUserName(String userId) {
		return orderDao.getUserName(userId);
	}

	@Override
	public void cancelOrder(String orderId) {
		orderDao.cancelOrder(orderId);
	}

	@Override
	public List queryOrderById(String galaryId) {
		List orderInfo =orderDao.queryOrderById(galaryId);
		return orderInfo;
	}

	@Override
	public List sortOrder(HttpServletRequest request,String id,String galaryId) {
		String hql=" from VLpOrder";
		//是否在单个作品集中查看订单
		if(StringUtils.isNotBlank(galaryId)){
			hql=hql+" where galaryId='"+galaryId+"' ";
		}
		//根据条件查询
		if(id.equals("status")){
			hql=hql+" order by orderStatus";
		}else if(id.equals("userman")){
			hql=hql+" order by userId";
		}else if(id.equals("cameraman")){
			hql=hql+" order by cameraId";
		}else{
			hql=hql+" order by createTime";
		}
		try {
			List<VLpOrder> orderInfo=querylistForPage(request, hql, 10);
			return orderInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List queryOrderByUser(HttpServletRequest request,String userId) {
		String hql=" from VLpOrder where userId='"+userId+"'";
		try {
			List list=querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List queryOrderByCamera(HttpServletRequest request,String cameraId) {
		String hql=" from VLpOrder where cameraId='"+cameraId+"'";
		try {
			List<VLpOrder> list=querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteOrder(String orderId) {
		LpOrder order=orderDao.queryOrderByOrderId(orderId);
		orderDao.deleteOrder(order);
		
	}
	@Transactional
	@Override
	public String addOrder(OrderBean orderBean) {
		LpOrder order=new LpOrder();
		//得到用户信息
		LpUser user=userManDao.queryUserByName(orderBean.getUserName());
		order.setLpUser(user);
		//设置作品集和摄影师
		LpGalary galary=galleryDao.getgalleryByID(orderBean.getGalaryId());
		order.setLpGalary(galary);
		LpUser camera=galary.getLpUser();
		order.setLpCamera(camera);
		//是否是私人订制
		LpService service=galary.getLpService();
		if(service!=null){
			order.setLpService(service);
		}
		order.setCreateTime(new Timestamp(new Date().getTime()));
		order.setOrderTime(new Timestamp(new Date().getTime()));
		order.setOrderStatus(3);
		order.setOrderMobile(orderBean.getOrderMobile());
		Serializable orderId= baseDao.saveObjectReturnId(order);
		//添加留言信息
		LpComment lpComment=new LpComment();
		lpComment.setCommentType(2);
		lpComment.setCommentDetail(orderBean.getComment());
		lpComment.setCreateTime(new Timestamp(new Date().getTime()));
		lpComment.setLpGalary(galary);
		lpComment.setLpOrder(order);
		lpComment.setLpUser(user);
		commentDao.saveComment(lpComment);
		return orderId.toString();
	}

	@Override
	public LpOrder queryOrderByOrderId(String orderId) {
		return (LpOrder)baseDao.getObjectById(LpOrder.class, orderId);
	}
	
	public void sendMessage(String messageTemplate, LpOrder order){
	    //推送系统消息通知
  		addPushCommentAlert(messageTemplate, order);
  		//发送短信通知
  		sendMessageAlert(messageTemplate, order);
	}
	
	/**
	 * 推送系统消息通知
	 * */
	private void addPushCommentAlert(String messageTemplate, LpOrder order){
		String title="系统消息";
		new PushNoticeMessage().pushOrderAlert(order.getLpUser(), title, messageTemplate,"","5");
	}

	private void sendMessageAlert(String messageTemplate, LpOrder order) {
		String mobile = order.getOrderMobile();
		LpUser user = order.getLpUser();
		if(StringUtils.isEmpty(mobile)){
			mobile = user.getMobile();
		}
		if(StringUtils.isEmpty(mobile)){
			mobile = user.getUserName();
		}
		ShortMessageUtil.sendCommonMessage(order.getOrderMobile(), messageTemplate,"");
	}

	
}
