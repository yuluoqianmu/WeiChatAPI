package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;

/**
 * 显示订单详情请求
 * @author luyongzhao
 *
 */
public class ShowOrderDetailReq extends BaseReq {
	
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="10",maxSize="50")
	private String userId;
	
	@ValidateNotNull(message="角色")
	@ValidateStringIn(message="角色",value="U,C")
	private String role;
	
	@ValidateNotNull(message="订单id")
	@ValidateSize(message="订单id",minSize="10",maxSize="50")
	private String orderId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
}
