package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
/**
 * 用户申请退款，摄影师接单之后不允许申请退款
 * @author luyongzhao
 *
 */
public class ApplyRefundReq extends BaseReq {
	@ValidateNotNull(message="订单id")
	@ValidateSize(message="订单id",minSize="10",maxSize="50")
	private String orderId;
	
	@ValidateNotNull(message="买家id")
	@ValidateSize(message="买家id",minSize="10",maxSize="50")
	private String buyerId;
	
	@ValidateNotNull(message="退款理由")
	@ValidateSize(message="退款理由",minSize="10",maxSize="200")
	private String reason;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
