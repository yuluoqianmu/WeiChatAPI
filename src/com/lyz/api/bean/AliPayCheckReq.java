package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

public class AliPayCheckReq extends BaseReq {
	
	@ValidateNotNull(message="订单id")
	@ValidateSize(message="订单id",minSize="10",maxSize="50")
	private String orderId;
	
	@ValidateLong(message="支付类型", min=1,max=3)
	private int payType;
//	@ValidateNotNull(message="交易id")
//	@ValidateSize(message="交易id",minSize="10",maxSize="100")
//	private String tradeNo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
	

//	public String getTradeNo() {
//		return tradeNo;
//	}
//
//	public void setTradeNo(String tradeNo) {
//		this.tradeNo = tradeNo;
//	}
//	
	
}
