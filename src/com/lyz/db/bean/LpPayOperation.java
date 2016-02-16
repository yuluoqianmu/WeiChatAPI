package com.lyz.db.bean;

public class LpPayOperation {
	/*操作记录id*/
	private int operId;

	/*操作类型()*/
	private String operType;

	/*操作人*/
	private String operPerson;

	/*操作时间*/
	private long operTime;

	/*附加字段（json格式字符串，比如退款理由）*/
	private String attachData = "";

	/*操作描述*/
	private String operDesc;
	
	/*所属订单id*/
	private long orderId;
	
	public LpPayOperation(){
		
	}
	
	public LpPayOperation(long orderId,String operType, String operDesc, String operPerson, String attachData){
		
		this.orderId = orderId;
		this.operType = operType;
		this.operDesc = operDesc;
		this.operPerson = operPerson;
		this.attachData = attachData;
		this.operTime = System.currentTimeMillis();
	}

	public int getOperId() {
		return operId;
	}

	public void setOperId(int operId) {
		this.operId = operId;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperPerson() {
		return operPerson;
	}

	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}

	public long getOperTime() {
		return operTime;
	}

	public void setOperTime(long operTime) {
		this.operTime = operTime;
	}

	public String getAttachData() {
		return attachData;
	}

	public void setAttachData(String attachData) {
		this.attachData = attachData;
	}

	public String getOperDesc() {
		return operDesc;
	}

	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	
}
