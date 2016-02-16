package com.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laipai.operationManage.pojo.LpCity;
import com.laipai.orderInfo.pojo.LpOrder;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.OperOrderResp;
import com.lyz.api.bean.OrderStatus;
import com.lyz.api.bean.OrderStatus.Operation;
import com.lyz.api.bean.ShowOrderListResp;
import com.lyz.api.bean.ShowOrderListResp.Order;
import com.lyz.api.bean.ShowOrderListResp.OrderStatis;
import com.lyz.api.bean.addOrderInfoReq;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpPayOperation;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.db.bean.LpMergedService;
import com.lyz.db.bean.LpUser;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.labelData.json.GsonExt;
import com.lyz.labelData.util.BaseTypeUtil;
import com.lyz.pay.OrderStat;
import com.lyz.pay.PayAttachData;
import com.lyz.pay.PayType;
import com.lyz.util.LaiPaiEntityIdGenerator;
/**
 * 订单操作服务
 * @author luyongzhao
 *
 */
public class OrderService {
	
	private static final Logger logger = Logger.getLogger(OrderService.class);
	/*订单数据库操作*/
	private static IBaseDao<LpPayOrder> orderDao = new BaseDaoImpl<LpPayOrder>();
	
	private static IBaseDao<LpMergedService> serviceDao = new BaseDaoImpl<LpMergedService>();
	
	private static IBaseDao<LpUser> userDao = new BaseDaoImpl<LpUser>();
	
	private static IBaseDao<LpCity> cityDao = new BaseDaoImpl<LpCity>();
	/*交易明细操作类*/
	private static IBaseDao<LpPayOperation> operDao = new BaseDaoImpl<LpPayOperation>();
	/*未完成*/
	public static final int PAY_STATUS_UNFINISHED = 0;
	/*完成*/
	public static final int PAY_STATUS_FINISHED = 1;
	/*退款*/
	public static final int PAY_STATUS_REFUND = 1;
	/*取消订单*/
	public static final int PAY_STATUS_CANCLE = 1;
	
	private static final GsonExt gson = new GsonExt();
	/*定金,元为单位*/
	public static final double bargain = 100;
	
	
	/**
	 * 插入操作
	 * @param oper
	 * @return
	 */
	public static boolean insertOperation(LpPayOperation oper){
		
		if(oper == null){
			
			return false;
		}
		
		String sql = "select count(oper_id) from lp_pay_operation where order_id=? and oper_type=?";
		
		int count = operDao.count(sql,oper.getOrderId(),oper.getOperType());
		/*如果操作已经插入过，则不再插入*/
		if(count > 0){
			return true;
		}
		/*如果节点是未知节点，则直接返回*/
		OrderStat stat = OrderStat.getValue(oper.getOperType());
		if(stat == null){
			logger.error("unknow order stat:"+oper.getOperType());
			return false;
		}
		
		/*判断当前操作是否是上一次操作之后的流程节点*/
		LpPayOperation lastOper = getLastPayOperation(oper.getOrderId());
		
		/*第一条记录必须是支付或者线下预定*/
		if(lastOper==null && !oper.getOperType().equals(OrderStat.SUC_PAY_BARGAIN.name()) 
				&& !oper.getOperType().equals(OrderStat.FAIL_PAY_BARGAIN.name())  
				&& !oper.getOperType().equals(OrderStat.SUC_PAY_FULL.name()) 
				&& !oper.getOperType().equals(OrderStat.FAIL_PAY_FULL.name()) 
				&& !oper.getOperType().equals(OrderStat.CANCLE_ORDER_OFFLINE.name())
				&& !oper.getOperType().equals(OrderStat.CANCLE_ORDER_OFFLINE_USER.name())
				&& !oper.getOperType().equals(OrderStat.ACCEPT_ORDER_OFFLINE.name())){
			logger.error("invalid start node:"+oper.getOperType());
			return false;
		}else if(lastOper!=null){
			
			OrderStat lastStat = OrderStat.getValue(lastOper.getOperType());
			if(lastStat == null){/*上一次操作未知，直接返回false*/
				logger.error("invalid last node:"+lastOper.getOperType());
				return false;
			}else{
				/*不是合法的下一个节点，直接返回false*/
				boolean flag = lastStat.isValidNextStat(oper.getOperType());
				if(!flag){
					logger.error("invalid next node:"+oper.getOperType());
					return false;
				}
			}
			
		}
		
		/*是合法的下一个节点，则直接插入操作*/
		String insertOper = operDao.getSqlForInsert(oper, "lp_pay_operation");
		count = operDao.upsertObject(insertOper);
		
		
		return count > 0;
		
	}
	
	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	public static LpPayOrder getOrderInfo(long orderId){
		
		/*查询订单列表*/
		String sql = "select order_id,buyer_id,buyer_name,buyer_phone,buyer_msg,order_time,camera_id,camera_name,service_id,service_name,service_price,pay_type,camera_head_img,camera_city,buyer_head_img " +
				"from lp_pay_order " +
				"where order_id=? "; 
		
		return orderDao.queryObject(sql, LpPayOrder.class, orderId);
	}
	
	/**
	 * 获取指定用户对指定摄影师特定时间内下单的指定服务的数量
	 * @param req
	 * @param miliTimeout 超时时间
	 * @return 订单数量
	 */
	public static int getOrderCount(addOrderInfoReq req, int miliTimeout){
		
		String sql = "select count(*) from lp_pay_order where buyer_id=? and camera_id=? and service_id=? and order_time>=?";
		
		return orderDao.count(sql, req.getBuyerId(),req.getCameraId(),req.getServiceId(),System.currentTimeMillis()-miliTimeout);
	}
	
	
	/**
	 * 提交订单
	 * @param req
	 * @return
	 */
	public static long addOrder(addOrderInfoReq req){
		
		if(req == null){
			
			return -1;
		}
		
//		/*获取服务信息*/
//		String serviceSql = "select ser.service_id service_id,service_name,price " +
//				"from lp_service ser,lp_service_detail det " +
//				"where ser.service_id=det.service_id and ser.service_id=?";
//		
//		LpService service = serviceDao.queryObject(serviceSql, LpService.class, req.getServiceId());
//		/*服务不存在*/
//		if(service == null){
//			logger.warn("service with id:"+req.getServiceId()+" no exist!");
//			return false;
//		}
		
		/*获取用户信息*/
		String userSql = "select nick_name,head_image,city_id from lp_user " +
				"where user_id=?";
		/*获取摄影师信息*/
		LpUser camera = userDao.queryObject(userSql, LpUser.class, req.getCameraId());
		if(camera == null){
			logger.warn("camera does not exist, userId="+req.getCameraId());
			return -1;
		}
		String citySql = "select city_name from lp_city where city_id=?";
		LpCity city = cityDao.queryObject(citySql, LpCity.class, camera.getCityId());
		/*获取用户信息*/
		LpUser buyer = userDao.queryObject(userSql, LpUser.class, req.getBuyerId());
		/*买家信息不存在*/
		if(buyer == null){
			logger.warn("buyer does not exist, userId="+req.getBuyerId());
			return -1;
		}
		
		/*拼凑订单信息*/
		LpPayOrder order = new LpPayOrder();
		order.setBuyerId(req.getBuyerId());
		order.setBuyerMsg(req.getBuyerMsg());
		order.setBuyerName(buyer.getNickName());
		order.setBuyerHeadImg(buyer.getHeadImage());
		order.setBuyerPhone(req.getBuyerPhone());
		order.setCameraCity(city==null?"":city.getCityName());
		order.setCameraHeadImg(camera.getHeadImage()==null?"":camera.getHeadImage());
		order.setCameraId(req.getCameraId());
		order.setCameraName(camera.getNickName());
		long orderId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeOrder);
		order.setOrderId(orderId);
		order.setOrderTime(System.currentTimeMillis());
		order.setPayStatus(PAY_STATUS_UNFINISHED);
		order.setPayType(req.getPayType());
		order.setServiceId(req.getServiceId());
		order.setServiceName(req.getServiceName());
		order.setServicePrice((int)(req.getServicePrice()*100));
		order.setUptime(System.currentTimeMillis());
		
		/*插入数据库*/
		String sql = orderDao.getSqlForInsert(order, "lp_pay_order");
		
		int count = orderDao.upsertObject(sql);
		if(count > 0){

			return orderId;
		}
		logger.error("fail to insert order:"+sql);
		return -1;
	}
	
	public static LpPayOperation getLastPayOperation(long orderId){
		
		
		/*获取订单操作详情单*/
		String operSql = "select oper_type,oper_person,oper_time,attach_data,oper_desc " +
				"from lp_pay_operation " +
				"where order_id=? order by oper_time desc " +
				"limit 1";
		
		LpPayOperation oper = operDao.queryObject(operSql, LpPayOperation.class, orderId);
		/*没有操作历史，虚拟出所有虚拟的节点*/
		if(oper == null){
			LpPayOrder order = getOrderInfo(orderId);
			if(order.getPayType() == PayType.PAY_OFFLINE){
				oper = new LpPayOperation(orderId, OrderStat.FIX_OFFLINE.name(), OrderStat.FIX_OFFLINE.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}else if(order.getPayType() == PayType.PAY_BARGAIN){
				
				oper = new LpPayOperation(orderId, OrderStat.FAIL_PAY_BARGAIN.name(), OrderStat.FAIL_PAY_BARGAIN.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}else if(order.getPayType() == PayType.PAY_ALL){
				oper = new LpPayOperation(orderId, OrderStat.FAIL_PAY_FULL.name(), OrderStat.FAIL_PAY_FULL.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}
			
		}
		
		return oper;
	}
	/**
	 * 获取最后一次操作，如果没有操作记录，需要虚拟出虚拟的操作记录节点
	 * @param orderId
	 * @return
	 */
	public static LpPayOperation getLastPayOperation(long orderId, String role){
		
		
		/*获取订单操作详情单*/
		String operSql = "select oper_type,oper_person,oper_time,attach_data,oper_desc " +
				"from lp_pay_operation " +
				"where order_id=? order by oper_time desc " +
				"limit 4";
		
		List<LpPayOperation> operList = operDao.queryObjects(operSql, LpPayOperation.class, orderId);
		LpPayOperation oper = null;
		/*没有操作历史，虚拟出所有虚拟的节点*/
		if(operList == null || operList.size()==0){
			LpPayOrder order = getOrderInfo(orderId);
			if(order.getPayType() == PayType.PAY_OFFLINE){
				oper = new LpPayOperation(orderId, OrderStat.FIX_OFFLINE.name(), OrderStat.FIX_OFFLINE.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}else if(order.getPayType() == PayType.PAY_BARGAIN){
				
				oper = new LpPayOperation(orderId, OrderStat.FAIL_PAY_BARGAIN.name(), OrderStat.FAIL_PAY_BARGAIN.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}else if(order.getPayType() == PayType.PAY_ALL){
				oper = new LpPayOperation(orderId, OrderStat.FAIL_PAY_FULL.name(), OrderStat.FAIL_PAY_FULL.getName(), "U", null);
				oper.setOperTime(order.getOrderTime());
			}
			
		}else{
			
			for(LpPayOperation op : operList){
				
				OrderStat stat = OrderStat.getValue(op.getOperType());
				if(stat!= null && stat.canShow(role, stat.getRoleShow())){
					return op;
				}
			}
		}
		
		return oper;
	}
	
	
	public static boolean isFinished(LpPayOperation oper){
		
		if(oper == null){
			return false;
		}
		
		return OrderStat.ACK.name().equals(oper.getOperType()) 
				|| OrderStat.FINISH_PAY.name().equals(oper.getOperType())
//				|| OrderStat.EVALUATED.name().equals(oper.getOperType())
				|| OrderStat.ACCEPT_ORDER_OFFLINE.name().equals(oper.getOperType());
	}
	
	public static boolean isUnfinished(LpPayOperation oper){
		
		if(oper == null){
			return true;
		}
		
		return OrderStat.SUC_PAY_BARGAIN.name().equals(oper.getOperType())
				|| OrderStat.FAIL_PAY_BARGAIN.name().equals(oper.getOperType())
				|| OrderStat.SUC_PAY_FULL.name().equals(oper.getOperType())
				|| OrderStat.FAIL_PAY_FULL.name().equals(oper.getOperType())
				|| OrderStat.FIX_OFFLINE.name().equals(oper.getOperType())
				|| OrderStat.ACCEPT_ORDER_BARGAIN.name().equals(oper.getOperType())
				|| OrderStat.ACCEPT_ORDER_FULL.name().equals(oper.getOperType());
		
//		|| OrderStat.APPLY_OFFLINE_FINAL.name().equals(oper.getOperType())
//		|| OrderStat.APPLY_ONLINE_FINAL.name().equals(oper.getOperType())
//		|| OrderStat.FINISH_FINAL_PAYMENT.name().equals(oper.getOperType())
	}
	
	public static boolean isCancle(LpPayOperation oper){
		
		if(oper == null){
			return false;
		}
		
		return OrderStat.CANCLE_ORDER.name().equals(oper.getOperType())  
		||		OrderStat.APPLY_REFUND.name().equals(oper.getOperType())  
		||		OrderStat.CANCLE_ORDER_OFFLINE.name().equals(oper.getOperType())  
		||		OrderStat.CANCLE_ORDER_OFFLINE_USER.name().equals(oper.getOperType())  
		||		OrderStat.PASS_CHECK.name().equals(oper.getOperType())  
		|| OrderStat.FINISH_REFUND.name().equals(oper.getOperType());
	}
	
	/**
	 * 获取摄影师的订单数量
	 * @param cameraId
	 * @return
	 */
	public static int getOrderCount4Camera(String cameraId){
		
		String sql = "select count(*) from lp_pay_order where camera_id=? ";
		
		return orderDao.count(sql,cameraId);
	}
	/**
	 * 获取用户预约的订单数量
	 * @param userId
	 * @return
	 */
	public static int getOrderCount4User(String userId){
		
		String sql = "select count(*) from lp_pay_order where buyer_id=?";
		
		return orderDao.count(sql,userId);
	}
	
	/**
	 * 获取订单列表
	 * @param userId
	 * @param role
	 * @param orderStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static ShowOrderListResp getOrderList(String userId, String role, String orderStatus, int pageNo, int pageSize, boolean isNeedStat){
		
		/*查询订单列表*/
		String buyerSql = "select order_id,buyer_id,buyer_name,buyer_phone,buyer_msg,order_time,camera_id,camera_name,service_id,service_name,service_price,pay_type,camera_head_img,camera_city,buyer_head_img " +
				"from lp_pay_order " +
				"where buyer_id=? order by order_time desc "; 
		
		String cameraSql = "select order_id,buyer_id,buyer_name,buyer_phone,buyer_msg,order_time,camera_id,camera_name,service_id,service_name,service_price,pay_type,camera_head_img,camera_city,buyer_head_img " +
				"from lp_pay_order " +
				"where camera_id=? order by order_time desc "; 
		
		int finished = 0;
		int unfinished = 0;
		int cancle = 0;
		
		String sql = "";
		if("U".equals(role)){
			sql = buyerSql;
		}else{
			sql = cameraSql;
		}
		
		List<LpPayOrder> orderList = orderDao.queryObjects(sql, LpPayOrder.class, userId);
		/*不存在订单*/
		if(orderList==null ||orderList.size()==0 || orderList.size()<=(pageNo-1)*pageSize){
			return null;
		}
		List<Order> allList = new ArrayList<Order>();
		/*筛选订单*/
		for(LpPayOrder order : orderList){
			
			/*获取最后一条订单操作*/
			LpPayOperation oper = getLastPayOperation(order.getOrderId(), role);
			/*如果没有操作，则忽略*/
			if(oper == null){
				
				continue;
			}
			
			/*如果需要统计数据，则统计*/
			if(isNeedStat){
				if(isFinished(oper)){
					finished++;
				}else if(isCancle(oper)){
					cancle++;
				}else if(isUnfinished(oper)){
					unfinished++;
				}
			}
			
			
			if("all".equals(orderStatus) && allList.size() < pageNo*pageSize){/*查询所有*/
				allList.add(getOrder(order, oper, role, order.getPayType()));
			}else if("finished".equals(orderStatus) && allList.size() < pageNo*pageSize){/*已完成*/
				if(isFinished(oper)){
					
					allList.add(getOrder(order, oper, role, order.getPayType()));
				}
			}else if("cancel".equals(orderStatus) && allList.size() < pageNo*pageSize){/*已取消*/
				
				if(isCancle(oper)){
					allList.add(getOrder(order, oper, role, order.getPayType()));
				}
			}else if("unfinished".equals(orderStatus) && allList.size() < pageNo*pageSize){/*未完成订单*/
				
				if(isUnfinished(oper)){
					
					allList.add(getOrder(order, oper, role, order.getPayType()));
				}//end if
			}
			
			
			/*如果到达指定数量则停止获取*/
			if(allList.size() >= pageNo*pageSize && !isNeedStat){
				
				break;
			}
		}//for
		
		ShowOrderListResp resp = new ShowOrderListResp();
		resp.setResult(CodeUtil.SUCCESS);
		if(isNeedStat){
			List<OrderStatis> statList = new ArrayList<OrderStatis>();
			statList.add(new OrderStatis("已完成", finished,"finished"));
			statList.add(new OrderStatis("未完成", unfinished,"unfinished"));
			statList.add(new OrderStatis("已取消",cancle,"cancel"));
			resp.setStatList(statList);
		}
		/*截取指定的子串，小于最小偏移，则取空*/
		if(allList.size() < (pageNo-1)*pageSize+1){
			
		}else{
			resp.setOrderList(allList.subList((pageNo-1)*pageSize, allList.size()));
		}
					
		return resp;
		
	}
	
	private static Order getOrder(LpPayOrder order, LpPayOperation oper, String role, int payType){
		
		Order ord = new Order();
		
		ord.setOrderId(order.getOrderId()+"");
		if("U".equals(role)){
			
			ord.setUserHeadImg(order.getCameraHeadImg());
			ord.setUserId(order.getCameraId());
			ord.setUserName(order.getCameraName());
		}else{
			ord.setUserHeadImg(order.getBuyerHeadImg());
			ord.setUserId(order.getBuyerId());
			ord.setUserName(order.getBuyerName());
		}
		ord.setServiceId(order.getServiceId());
		ord.setServiceName(order.getServiceName());
		ord.setServicePrice(order.getServicePrice());
		ord.setPayType(order.getPayType());
		ord.setCameraId(order.getCameraId());
		ord.setCity(order.getCameraCity());
		/*指定需要支付的金额*/
		if(order.getPayType() == 1){/*全款*/
			ord.setPayPrice(order.getServicePrice());
		}else if(order.getPayType() == 2){/*定金*/
			ord.setPayPrice(bargain*100);
		}
		OrderStatus status = new OrderStatus();
		OrderStat lastStat = null;
		if(oper != null){
			lastStat = OrderStat.getValue(oper.getOperType());
		}	
		if(lastStat != null){
			status.setStatusDesc(lastStat.getName());
			status.setOperTime(BaseTypeUtil.getStrDateTime(oper==null?order.getOrderTime():oper.getOperTime(), "yyyy.MM.dd"));			
		}
//		else{
//			status.setStatusDesc("未支付");
//			status.setOperTime(BaseTypeUtil.getStrDateTime(oper==null?order.getOrderTime():oper.getOperTime(), "yyyy.MM.dd"));
//		}
		
		fillOper(lastStat, status, role,order.getOrderId(),order.getBuyerId(), payType);
		ord.setLatestStatus(status);
		
		
		return ord;
		
	}
	/**
	 * 填充未操作完成的流程
	 * @param statList
	 * @param orderStat
	 * @param role
	 * @param orderId
	 * @param userId
	 * @param payType
	 */
	private static void fillUnOperStatus(List<OrderStatus> statList, String orderStat, String role, long orderId, String userId, int payType){
		
		if(statList == null){
			statList = new ArrayList<OrderStatus>();
		}
		
		/*获取最后一个流程节点的状态*/
		OrderStat stat = OrderStat.getValue(orderStat);
		/*如果节点为空*/
		if(stat == null){
			logger.error("unknow stat node:"+orderStat);
			return;
		}
		
		if(stat.getNextStats() == null || stat.getNextStats().length==0 || stat.isValidNextStat(OrderStat.END.name())){
			return;
		}
		/*第一个节点为默认节点*/
		OrderStat nextStat = stat.getNextStats()[0];
//		/*如果是开始节点，需要判断用户首先选择的是支付全款还是定金*/
//		if(OrderStat.START.name().equals(stat.name())){
//			
//			if(payType == 1){/*全款*/
//				nextStat = OrderStat.SUC_PAY_FULL;
//			}else if(payType == 2){/*定金*/
//				nextStat = OrderStat.SUC_PAY_BARGAIN;
//			}else{/*线下*/
//				nextStat = OrderStat.FIX_OFFLINE;
//			}
//		}
		OrderStatus status = new OrderStatus();
		status.setStatus(0);
		status.setStatusDesc(nextStat.getName());
		/*设置这个节点接下来的操作*/
		/*填充操作*/
//		fillOper(nextStat, status, role, orderId, userId);
		if(nextStat.canShow(role, nextStat.getRoleShow())){
			statList.add(status);
		}
		/*如果指定用户角色显示，并且节点状态符合，则展示*/
		if(nextStat.canShow(role, nextStat.getTipRole()) && nextStat.canShow(status.getStatus()+"", nextStat.getTipShowTime())){
			status.setTip(nextStat.getTip());
		}
		
		/*递归调用下一个操作节点*/
		fillUnOperStatus(statList, nextStat.name(), role, orderId, userId, payType);
	}
	/**
	 * 根据订单获取第一个虚拟节点
	 * @param order
	 * @return
	 */
	public static LpPayOperation getFirstOperation(LpPayOrder order){
		
		LpPayOperation oper = new LpPayOperation();
		
		if(order.getPayType() == PayType.PAY_OFFLINE){
			
			oper.setOperType(OrderStat.FIX_OFFLINE.name());
			oper.setOperPerson("U");
			oper.setOperTime(order.getOrderTime());
			oper.setOperDesc(OrderStat.FIX_OFFLINE.getName());
		}else if(order.getPayType() == PayType.PAY_BARGAIN){
			
			oper.setOperType(OrderStat.FAIL_PAY_BARGAIN.name());
			oper.setOperPerson("U");
			oper.setOperTime(order.getOrderTime());
			oper.setOperDesc(OrderStat.FAIL_PAY_BARGAIN.getName());
		}else if(order.getPayType() == PayType.PAY_ALL){
			
			oper.setOperType(OrderStat.FAIL_PAY_FULL.name());
			oper.setOperPerson("U");
			oper.setOperTime(order.getOrderTime());
			oper.setOperDesc(OrderStat.FAIL_PAY_FULL.getName());
		}else{
			
			return null;
		}
		
		return oper;
	}
	
	public static OperOrderResp getOrderDetail(long orderId, String role){
		
		/*获取订单信息*/
		String sql = "select buyer_id,buyer_name,buyer_phone,buyer_msg,order_time,camera_id,camera_name,service_id,service_name,service_price,pay_type,camera_head_img,camera_city,buyer_head_img " +
				"from lp_pay_order where order_id=?";
		
		
		LpPayOrder order = orderDao.queryObject(sql, LpPayOrder.class, orderId);
		/*订单不存在*/
		if(order == null){
			logger.error("order not exist,orderId="+orderId);
			return null;
		}
		
		/*获取订单操作详情单*/
		String operSql = "select oper_type,oper_person,oper_time,attach_data,oper_desc " +
				"from lp_pay_operation " +
				"where order_id=? order by oper_time";
		List<LpPayOperation> operList = operDao.queryObjects(operSql, LpPayOperation.class, orderId);
		
		/*组装返回对象*/
		OperOrderResp resp = new OperOrderResp();
		resp.setResult(CodeUtil.SUCCESS);
		resp.setUserHeadImg(order.getBuyerHeadImg());
		resp.setUserId(order.getBuyerId());
		resp.setUserName(order.getBuyerName());
		resp.setUserPhone(order.getBuyerPhone());
		resp.setUserMsg(order.getBuyerMsg());
		resp.setCameraHeadImg(order.getCameraHeadImg());
		resp.setCameraId(order.getCameraId());
		resp.setServiceId(order.getServiceId());
		resp.setCameraName(order.getCameraName());
		resp.setCameraCity(order.getCameraCity());
		resp.setServiceName(order.getServiceName());
		resp.setServicePrice(order.getServicePrice());
		resp.setPayType(order.getPayType());
		resp.setOrderId(orderId+"");
		resp.setOrderTime(BaseTypeUtil.getStrDateTime(order.getOrderTime(), "yyyy.MM.dd HH:mm"));

		/*获取虚拟节点*/
		LpPayOperation firstOper = getFirstOperation(order);
		if(operList == null){
			operList = new ArrayList<LpPayOperation>();
		}
		/*在第一个位置插入用户预约操作*/
		operList.add(0, firstOper);
		
		/*组装状态列表*/
		if(operList != null){
			
			List<OrderStatus> statList = new ArrayList<OrderStatus>();
			OrderStat lastStat = null;
			for(LpPayOperation oper : operList){
				/*开始和结束节点，不往外吐，虚拟节点不会插入*/
//				if(oper.getOperType().equals(OrderStat.START.name()) || oper.getOperType().equals(OrderStat.END.name())){
//					
//					continue;
//				}
				/*未知操作，忽略*/
				OrderStat currStat = OrderStat.getValue(oper.getOperType());
				if(currStat==null || !currStat.canShow(role, currStat.getRoleShow())){
					continue;
				}
				OrderStatus stat = new OrderStatus();
				stat.setOperTime(BaseTypeUtil.getStrDateTime(oper.getOperTime(), "yyyy.MM.dd HH:mm"));
				stat.setStatusDesc(OrderStat.getValue(oper.getOperType()).getName());
				/*如果指定用户角色显示，并且节点状态符合，则展示*/
				if(currStat.canShow(role, currStat.getTipRole()) && currStat.canShow(stat.getStatus()+"", currStat.getTipShowTime())){
					stat.setTip(currStat.getTip());
				}
				
				lastStat = OrderStat.getValue(oper.getOperType());
				/*加入列表*/
				statList.add(stat);
			}
			resp.setStatList(statList);
			/*存在最后一条记录，则添加操作，针对买家和摄影师有所区别*/
			if(statList.size() != 0){
				fillOper(lastStat, statList.get(statList.size()-1), role, orderId, order.getBuyerId(),order.getPayType());
			}else{
				OrderStatus startStatus = new OrderStatus();
				fillOper(null, startStatus, role, orderId, order.getBuyerId(),order.getPayType());
				statList.add(startStatus);
			}
			/*填补带操作流程*/
			String name = lastStat.name();
//			if(lastStat == null){/*如果不操作记录，则默认从头开始*/
//				name = OrderStat.START.name();
//			}else{
//				name = lastStat.name();
//			}
			fillUnOperStatus(statList, name, role, orderId, order.getBuyerId(),order.getPayType());		
			
			/*将操作按钮，逐个下移一个位置*/
			downButton(statList);
		}//end if operList
		
		return resp;
		
	}
	
	/**
	 * 将操作按钮下移一位
	 * @param statList
	 */
	private static void downButton(List<OrderStatus> statList){
		
		if(statList == null || statList.size()<2){
			return;
		}
		
		/*如果最后一个节点有操作，则忽略*/
		if(statList.get(statList.size()-1).getOperList() != null && statList.get(statList.size()-1).getOperList().size()>0){
			return;
		}
		
		/*最后一个节点肯定没有操作，所以可以从倒数第一个下移*/
		for(int i=statList.size()-1; i>0; i--){

			statList.get(i).setOperList(statList.get(i-1).getOperList());			
		}
		/*第一个节点肯定不能有操作*/
		statList.get(0).setOperList(null);
	}
	/**
	 * 填充用户操作
	 * @param lastStat
	 * @param status
	 * @param role
	 * @return
	 */
	public static OrderStatus fillOper(OrderStat lastStat,OrderStatus status, String role, long orderId, String userId, int payType){
		
		List<Operation> showOperList = new ArrayList<Operation>();
		/*存在最后一条记录，则添加操作，针对买家和摄影师有所区别*/
		if(lastStat != null){
			
			
			/*如果当前是用户角色，并且已经摄影师确认完毕需要加入“去评价”操作*/
			if((OrderStat.ACK.name().equals(lastStat.name())||OrderStat.ACCEPT_ORDER_OFFLINE.name().equals(lastStat.name())) && "U".equals(role)){
				/*查询当前用户是否做过评价,没有做过评价的需要添加评价操作*/
				int count = CommentService.getPayCmtCount(orderId, userId);
				if(count <= 0){
					showOperList.add(new Operation(null, com.lyz.pay.Operation.TO_EVALUATE.getOperDesc(),Operation.TO_COMMENT,com.lyz.pay.Operation.TO_EVALUATE.getOperLevel()));
				}
			}
			/*如果有接下来的操作，则加入操作*/
			OrderStat[] stats = lastStat.getNextStats();
			if(stats!= null && stats.length>0){
								
				for(OrderStat stat : stats){
					
					if(stat.getPreOper().getRole().equals(role)){
						/*如果是支付，需要伪造一个操作节点，让客户端跳转到支付页面*/
						if(stat.name().equals(OrderStat.SUC_PAY_BARGAIN.name())
								|| stat.name().equals(OrderStat.SUC_PAY_FULL.name())){
							
							Operation oper = new Operation();
							oper.setOrderStatus(null);
							oper.setOperDesc(stat.getPreOper().getOperDesc());
							oper.setClientOper(Operation.TO_PAY);
							oper.setOperLevel(com.lyz.pay.Operation.TO_PAY.getOperLevel());
							showOperList.add(oper);
							
						}else{
							Operation oper = new Operation();
							oper.setOrderStatus(stat.name());
							oper.setOperDesc(stat.getPreOper().getOperDesc());
							oper.setOperLevel(stat.getPreOper().getOperLevel());
							showOperList.add(oper);
						}
						
					}
				}//end for
				
				
			}//end if stats
			
			
		}
//		else{//end if lastStat /*用户没有进行支付操作*/
//			status.setStatusDesc(OrderStat.START.getName());
//			
//			/*如果不是线下支付，需要提示用户去支付*/
//			if(payType != PayType.PAY_OFFLINE && "U".equals(role)){
//				Operation oper = new Operation();
//				oper.setOrderStatus(null);
//				oper.setOperDesc(com.lyz.pay.Operation.TO_PAY.getOperDesc());
//				oper.setClientOper(Operation.TO_PAY);
//				oper.setOperLevel(com.lyz.pay.Operation.TO_PAY.getOperLevel());
//				showOperList.add(oper);	
//			}
//			
//		}
		/*如果有需要加入的操作，则加入*/
		if(showOperList.size() > 0){				
			status.setOperList(showOperList);					
		}//end if showOperList
		return status;
	}
	/**
	 * 是否存在订单
	 * @param orderId
	 * @param role
	 * @param userId
	 * @return
	 */
	public static boolean existOrder(long orderId, String role, String userId){
		
		String buyerSql = "select count(*) from lp_pay_order where order_id=? and buyer_id=?";
		
		String cameraSql = "select count(*) from lp_pay_order where order_id=? and camera_id=?";
		
		int count = 0;
		
		if("U".equals(role)){
			
			count = orderDao.count(buyerSql,orderId,userId);
		}else if("C".equals(role)){
			
			count = orderDao.count(cameraSql,orderId,userId);
		}
		
		return count > 0;
	}
	/**
	 * 是否存在支付记录
	 * @param orderId
	 * @param tradeNo
	 * @return
	 */
	public static boolean existPayRecord(String orderId, int payType){
		
		long lorderId = BaseTypeUtil.getLong(orderId, 0);
		
		String sql = "select attach_data from lp_pay_operation " +
				"where order_id=? and oper_type in ('"+OrderStat.SUC_PAY_FULL.name()+"','"+OrderStat.SUC_PAY_BARGAIN.name()+"')";
//				"where order_id=? and oper_type in ("+OrderStat.SUC_PAY_FULL.ordinal()+","+OrderStat.SUC_PAY_BARGAIN.ordinal()+","+OrderStat.FINISH_FINAL_PAYMENT.ordinal()+")";
		/*获取该订单的所有支付记录*/
		List<LpPayOperation> operList = operDao.queryObjects(sql, LpPayOperation.class, lorderId);
		
		if(operList == null || operList.isEmpty()){
			return false;
		}
		/*判断支付记录中是否存在指定的支付记录*/
		for(LpPayOperation oper : operList){
			
			String data = oper.getAttachData();
			PayAttachData payData = gson.fromJson(data, PayAttachData.class);
			if(payData == null){
				continue;
			}
			if(payType == payData.getPayType()){
				return true;
			}
		}
		
		return false;
		
	}
	
	
	public static void main(String args[]){
		
		
		addOrderInfoReq req = new addOrderInfoReq();
		req.setBuyerId("8a2a76634ccb8b6b014ccbbb42750363");
		req.setCameraId("8a2a76634c5af695014c5b3f9f570043");
		req.setServiceId("1111111111081");
		System.out.println(OrderService.getOrderCount(req, 1000));
		System.out.println(System.currentTimeMillis());
		
		
//		LpPayOperation oper = new LpPayOperation(1432003293091101l, OrderStat.SUC_PAY_BARGAIN.name(), OrderStat.SUC_PAY_BARGAIN.getName(), "U", "{orderId:\"1432003293091101\"}");
//		OrderService.insertOperation(oper);
		
//		System.out.println(OrderStat.FINISH_PAY.canShow("C",OrderStat.FINISH_PAY.getRoleShow()));
		
//		List<OrderStatus> statList = new ArrayList<OrderStatus>();
//		OrderStatus status = new OrderStatus();
//		statList.add(status);
//		OrderService.fillOper(null, status, "U", 11,"123",1);
////		OrderService.fillUnOperStatus(statList, OrderStat.START.name(), "C",11,"123");
//		
//		for(OrderStatus stat : statList){
//			
//			System.out.println(stat.getStatusDesc());
//			List<Operation> operList = stat.getOperList();
//			if(operList==null || operList.size()==0){
//				continue;
//			}
//			Operation oper = operList.get(0);
//			if(oper == null){
//				System.out.println("null");
//				continue;
//			}
//			System.out.println("\t"+oper.getOperDesc()+" "+oper.getOrderStatus());
//		}
	}
	
	
}
