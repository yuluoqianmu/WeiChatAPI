package com.lyz.pay;
/**
 * 操作
 * @author luyongzhao
 *
 */
public enum Operation {
	
	TO_PAY {public String getOperDesc(){return "去支付";}
	public String getRole(){return "U";}},
	PAY_BARGAIN {public String getOperDesc(){return "支付定金";}
	public String getRole(){return "U";}},
	PAY_FULL {public String getOperDesc(){return "支付全款";}
	public String getRole(){return "U";}},
	PAY_OFFLINE {public String getOperDesc(){return "线下支付";}
	public String getRole(){return "U";}},
	ACCEPT_ORDER_BARGAIN {public String getOperDesc(){return "接单";}
	public String getRole(){return "C";}},
	CANCLE_ORDER {public String getOperDesc(){return "取消订单";}
	public String getRole(){return "C";}
	public int getOperLevel(){return 1;}},
	APPLY_REFUND {public String getOperDesc(){return "申请退款";}
	public String getRole(){return "U";}
	public int getOperLevel(){return 1;}},
	ACCEPT_ORDER_FULL {public String getOperDesc(){return "接单";}
	public String getRole(){return "C";}},
	ACCEPT_ORDER_OFFLINE {public String getOperDesc(){return "接单";}
	public String getRole(){return "C";}},
	CANCLE_ORDER_OFFLINE {public String getOperDesc(){return "取消订单";}
	public String getRole(){return "C";}
	public int getOperLevel(){return 1;}},
	CANCLE_ORDER_OFFLINE_USER {public String getOperDesc(){return "取消订单";}
	public String getRole(){return "U";}
	public int getOperLevel(){return 1;}},
	APPLY_FINAL_PAYMENT_ONLINE {public String getOperDesc(){return "申请线上收尾款";}
	public String getRole(){return "C";}},
	APPLY_FINAL_PAYMENT_OFFLINE {public String getOperDesc(){return "申请线下收尾款";}
	public String getRole(){return "C";}},
	REFUND {public String getOperDesc(){return "退款";}
	public String getRole(){return "M";}},
	CHECK {public String getOperDesc(){return "审核";}
	public String getRole(){return "M";}},
	ACK {public String getOperDesc(){return "确认";}
	public String getRole(){return "C";}},
	PAY_FINAL_PAYMENT_ONLINE {public String getOperDesc(){return "支付尾款";}
	public String getRole(){return "U";}},
	PAY_SELLER {public String getOperDesc(){return "打款";}
	public String getRole(){return "M";}},
	TO_EVALUATE {public String getOperDesc(){return "去评价";}
	public String getRole(){return "U";}},
	NO_OPER {public String getOperDesc(){return "不做操作(结束状态)";}
	public String getRole(){return "S";}};
	
	public abstract String getOperDesc();
	/*获取操作者角色(U:user(buyer),C:Camera,M:Manager,S:System)*/
	public abstract String getRole();
	/**
	 * 获取操作级别，0-不能操作，1-正常操作，2-推荐操作
	 * @return
	 */
	public int getOperLevel(){
		
		return 2;
	}
	
	
	public static void main(String args[]){
		
		System.out.println(Operation.PAY_BARGAIN.ordinal());
	}
}
