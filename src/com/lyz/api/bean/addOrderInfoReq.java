package com.lyz.api.bean;

import com.laipai.util.EmojiFilter;
import com.lyz.validate.ValidateDigit;
import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 支付前请求参数(提交订单)
 * @author luyongzhao
 *
 */
public class addOrderInfoReq extends BaseReq {

	/*买家id*/
	@ValidateNotNull(message="买家id")
	@ValidateSize(message="买家id",minSize="10",maxSize="50")
	private String buyerId;

	@ValidateNotNull(message="预留电话")
	@ValidateSize(message="预留电话",minSize="11",maxSize="11")
	private String buyerPhone;

	/*买家留言，备注信息*/
	private String buyerMsg = "";

	@ValidateNotNull(message="摄影师id")
	@ValidateSize(message="摄影师id",minSize="10",maxSize="50")
	private String cameraId;

	@ValidateNotNull(message="服务id")
	@ValidateSize(minSize="10",maxSize="50",message="服务id")
	private String serviceId;
	
	@ValidateNotNull(message="服务名称")
	@ValidateSize(minSize="1",maxSize="50",message="服务名称")
	private String serviceName;
	/*以元为单位*/
	private double servicePrice;

	/*支付类型（1-全款，2-支付定金，3-线下支付）*/
	@ValidateLong(message="支付类型", min=1,max=3)
	private int payType;
	


	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		
		this.serviceId = serviceId;	
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerMsg() {
		return buyerMsg;
	}

	/**
	 * 过滤emoji表情
	 * @param buyerMsg
	 */
	public void setBuyerMsg(String buyerMsg) {
		this.buyerMsg = EmojiFilter.filterEmoji(buyerMsg);
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
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

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

}
