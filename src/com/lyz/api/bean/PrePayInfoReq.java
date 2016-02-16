package com.lyz.api.bean;

import com.lyz.validate.ValidateDigit;
import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 支付前获取支付所需要的参数
 * @author luyongzhao
 *
 */
public class PrePayInfoReq extends BaseReq{
	
	@ValidateNotNull(message="订单id")
	@ValidateSize(message="订单id",minSize="10",maxSize="50")
	private String orderId;
	
	/*支付类型（1-全款，2-支付定金，4-支付尾款）*/
	@ValidateLong(message="支付类型", min=1,max=4)
	private int payType;
	
	@ValidateNotNull(message="服务名称")
	@ValidateSize(minSize="1",maxSize="50",message="服务名称")
	private String serviceName;
	
	/*以元为单位*/
	private double servicePrice;
	
	/*支付工具类型：1-支付宝；2-微信*/
	@ValidateLong(message="支付工具类型", min=0,max=2)
	private int payToolType;

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

	public int getPayToolType() {
		return payToolType;
	}

	public void setPayToolType(int payToolType) {
		this.payToolType = payToolType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getServicePrice() {

		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}
	
	
	
}
