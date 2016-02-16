package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;

/**
 * 展示订单列表
 * @author luyongzhao
 *
 */
public class ShowOrderListReq extends BaseReq {
	
	@ValidateNotNull(message="用户类型")
	@ValidateStringIn(message="用户类型",value="U,C")
	private String role;
	
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="10",maxSize="50")
	private String userId;
	
	@ValidateNotNull(message="是否包含订单统计")
	@ValidateStringIn(message="是否包含订单统计",value="0,1")
	private String hasStat;
	
	@ValidateNotNull(message="订单状态")
	@ValidateStringIn(message="订单状态",value="all,finished,unfinished,cancel")
	private String orderStatus;
	
	/*页码*/
	private int pageNo = 1;
	/*每页包含的订单数量*/
	private int pageSize = 10;

	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHasStat() {
		/*保证客户端只获取一次统计数据*/
		if(pageNo > 1){
			hasStat = "0";
		}
		return hasStat;
	}

	public void setHasStat(String hasStat) {
		this.hasStat = hasStat;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}
