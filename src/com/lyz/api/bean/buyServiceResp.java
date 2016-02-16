package com.lyz.api.bean;
/**
 * 购买服务，预约摄影师返回值
 * @author luyongzhao
 *
 */
public class buyServiceResp extends BaseResp {
	
	/*订单id*/
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
