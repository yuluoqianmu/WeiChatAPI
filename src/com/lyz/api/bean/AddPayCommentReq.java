package com.lyz.api.bean;

import com.laipai.util.EmojiFilter;
import com.lyz.validate.ValidateDigit;
import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 添加支付评论
 * @author luyongzhao
 *
 */
public class AddPayCommentReq extends BaseReq {
	
	@ValidateNotNull(message="评论者id")
	@ValidateSize(message="评论者id",minSize="10",maxSize="50")
	private String userId;
	
	@ValidateNotNull(message="摄影师id")
	@ValidateSize(message="摄影师id",minSize="10",maxSize="50")
	private String cameraId;
	
	@ValidateNotNull(message="订单id")
	@ValidateSize(message="订单id",minSize="10",maxSize="50")
	@ValidateDigit(message="订单id")
	private String orderId;
	
	@ValidateNotNull(message="服务id")
	@ValidateSize(message="服务id",minSize="10",maxSize="50")
	private String serviceId;

	@ValidateLong(message="评分",min=1,max=5)
	private int score;
	
	@ValidateNotNull(message="评论内容")
	@ValidateSize(message="评论内容",minSize="0",maxSize="1000")
	private String content = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public  int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}
	/**
	 * 过滤emoji表情
	 * @param content
	 */
	public void setContent(String content) {
		this.content = EmojiFilter.filterEmoji(content);
	}
	
	
}
