package com.lyz.api.bean;

import java.util.List;

/**
 * 订单状态类
 * @author luyongzhao
 *
 */
public class OrderStatus {

	/*操作描述第一行*/
	private String statusDesc;
	/*操作描述第二行*/
	private String statusDesc2;
	/*提示信息，比如支付定金100*/
	private String tip;
	/*操作时间*/
	private String operTime;
	/*1-操作完成；0-未操作*/
	private int status = 1;
	/*该节点之下可操作的列表*/
	private List<Operation> operList;
	
	public String getStatusDesc() {
		return statusDesc;
	}



	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}



	public String getOperTime() {
		return operTime;
	}



	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}



	public List<Operation> getOperList() {
		return operList;
	}



	public void setOperList(List<Operation> operList) {
		this.operList = operList;
	}

	

	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public String getTip() {
		return tip;
	}



	public void setTip(String tip) {
		this.tip = tip;
	}



	public String getStatusDesc2() {
		return statusDesc2;
	}



	public void setStatusDesc2(String statusDesc2) {
		this.statusDesc2 = statusDesc2;
	}



	/**
	 * 操作类
	 * @author luyongzhao
	 *
	 */
	public static class Operation{
		/*去支付*/
		public static final String TO_PAY = "TO_PAY";
		/*去评价*/
		public static final String TO_COMMENT = "TO_COMMENT";
		
		/*操作状态名称,对于客户端本身的页面跳转（去支付，去评价），该值为null*/
		private String orderStatus;
		/*操作描述*/
		private String operDesc;
		/*TO_PAY-去支付；TO_COMMENT-去评价*/
		private String clientOper;
		/*操作级别，0-不能操作，1-正常操作，2-推荐操作*/
		private int operLevel;
		
		
		public Operation(){
			
			
		}
		
		public Operation(String orderStatus, String operDesc, String clientOper, int operLevel){
			
			this.orderStatus = orderStatus;
			this.operDesc = operDesc;
			this.clientOper = clientOper;
			this.operLevel = operLevel;
		}


		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getOperDesc() {
			return operDesc;
		}

		public void setOperDesc(String operDesc) {
			this.operDesc = operDesc;
		}

		public String getClientOper() {
			return clientOper;
		}

		public void setClientOper(String clientOper) {
			this.clientOper = clientOper;
		}

		public int getOperLevel() {
			return operLevel;
		}

		public void setOperLevel(int operLevel) {
			this.operLevel = operLevel;
		}
		
		
	}
	
	
}
