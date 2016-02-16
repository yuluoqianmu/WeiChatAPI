package com.lyz.api.bean;
/**
 * 保存订单响应
 * @author luyongzhao
 *
 */
public class AddOrderInfoResp extends BaseResp {
	/*订单id*/
	private String orderId;
	
	public AddOrderInfoResp(int code, String orderId){
		
		super(code);
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
}
