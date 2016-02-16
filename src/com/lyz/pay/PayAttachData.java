package com.lyz.pay;

public class PayAttachData {
	/*支付工具类型，wexin，zhifubao*/
	private String payToolType;
	/*买家email*/
	private String buyerEmail;
	/*买家账号*/
	private String buyerId;
	/*通知id*/
	private String notifyId;
	/*支付金额,以分为单位*/
	private int totalFee;
	/*交易号*/
	private String tradeNo;
	/*支付类型*/
	private int payType;
	
	public PayAttachData(String payToolType, String buyerEmail,String buyerId, String notifyId, int totalFee, String tradeNo,int payType){
		
		this.payToolType = payToolType;
		this.buyerEmail = buyerEmail;
		this.buyerId = buyerId;
		this.notifyId = notifyId;
		this.totalFee = totalFee;
		this.tradeNo = tradeNo;
		this.payType = payType;
	}

	public String getPayToolType() {
		return payToolType;
	}

	public void setPayToolType(String payToolType) {
		this.payToolType = payToolType;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
	
	
}
