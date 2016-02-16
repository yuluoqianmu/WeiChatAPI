package com.lyz.pay;

import com.lyz.service.OrderService;

/**
 * 订单状态
 * @author luyongzhao
 *
 */
public enum OrderStat {
	
	/*该节点在数据库中不存在*/
    START {public String getName(){return "开始";}
    public Operation getPreOper(){return null;}
    public OrderStat[] getNextStats(){return new OrderStat[]{SUC_PAY_BARGAIN,FAIL_PAY_BARGAIN,SUC_PAY_FULL,FAIL_PAY_FULL,FIX_OFFLINE};}},
    
    /*该节点在数据库中不存在*/
    END {public String getName(){return "结束";}
    public Operation getPreOper(){return Operation.NO_OPER;}
    public OrderStat[] getNextStats(){return null;}},
    
    FAIL_PAY_BARGAIN {public String getName(){return "已预约";}
    public Operation getPreOper(){return Operation.PAY_BARGAIN;}
    public OrderStat[] getNextStats(){return new OrderStat[]{SUC_PAY_BARGAIN};}},
    
    SUC_PAY_BARGAIN {public String getName(){return "已支付定金";}
    public Operation getPreOper(){return Operation.PAY_BARGAIN;}
    public OrderStat[] getNextStats(){return new OrderStat[]{ACCEPT_ORDER_BARGAIN,CANCLE_ORDER,APPLY_REFUND};}
    public String getTip(){return OrderService.bargain+"元";}},
    
    FAIL_PAY_FULL {public String getName(){return "已预约";}
    public Operation getPreOper(){return Operation.PAY_FULL;}
    public OrderStat[] getNextStats(){return new OrderStat[]{SUC_PAY_FULL};}},
    
    SUC_PAY_FULL {public String getName(){return "已支付全款";}
    public Operation getPreOper(){return Operation.PAY_FULL;}
    public OrderStat[] getNextStats(){return new OrderStat[]{ACCEPT_ORDER_FULL,CANCLE_ORDER,APPLY_REFUND};}},
    
    FIX_OFFLINE {public String getName(){return "已预约";}
    public Operation getPreOper(){return Operation.PAY_OFFLINE;}
    public OrderStat[] getNextStats(){return new OrderStat[]{ACCEPT_ORDER_OFFLINE,CANCLE_ORDER_OFFLINE,CANCLE_ORDER_OFFLINE_USER};}},
    /*已接单（定金）*/
    ACCEPT_ORDER_BARGAIN {public String getName(){return "摄影师接单";}
    public Operation getPreOper(){return Operation.ACCEPT_ORDER_BARGAIN;}
    public OrderStat[] getNextStats(){return new OrderStat[]{ACK};}},
    
//    public OrderStat[] getNextStats(){return new OrderStat[]{APPLY_OFFLINE_FINAL,APPLY_ONLINE_FINAL};}
    
    CANCLE_ORDER {public String getName(){return "摄影师取消订单";}
    public Operation getPreOper(){return Operation.CANCLE_ORDER;}
    public OrderStat[] getNextStats(){return new OrderStat[]{FINISH_REFUND};}},
    
    APPLY_REFUND {public String getName(){return "顾客申请退款";}
    public Operation getPreOper(){return Operation.APPLY_REFUND;}
    public OrderStat[] getNextStats(){return new OrderStat[]{PASS_CHECK};}},
    
    /*已接单（全款）*/
    ACCEPT_ORDER_FULL {public String getName(){return "摄影师接单";}
    public Operation getPreOper(){return Operation.ACCEPT_ORDER_FULL;}
    public OrderStat[] getNextStats(){return new OrderStat[]{ACK};}},
    
    /*已接单（线下）*/
    ACCEPT_ORDER_OFFLINE {public String getName(){return "摄影师接单";}
    public Operation getPreOper(){return Operation.ACCEPT_ORDER_OFFLINE;}
    public OrderStat[] getNextStats(){return new OrderStat[]{END};}},
    
    /*已取消接单（线下）*/
    CANCLE_ORDER_OFFLINE {public String getName(){return "摄影师取消订单";}
    public Operation getPreOper(){return Operation.CANCLE_ORDER_OFFLINE;}
    public OrderStat[] getNextStats(){return new OrderStat[]{END};}},
    
    /*用户已取消接单（线下）*/
    CANCLE_ORDER_OFFLINE_USER {public String getName(){return "顾客取消订单";}
    public Operation getPreOper(){return Operation.CANCLE_ORDER_OFFLINE_USER;}
    public OrderStat[] getNextStats(){return new OrderStat[]{END};}},
    
//    /*申请线上支付尾款*/
//    APPLY_ONLINE_FINAL {public String getName(){return "已申请线上支付尾款";}
//    public Operation getPreOper(){return Operation.APPLY_FINAL_PAYMENT_ONLINE;}
//    public OrderStat[] getNextStats(){return new OrderStat[]{FINISH_FINAL_PAYMENT};}},
//    
//    /*申请线下支付尾款*/
//    APPLY_OFFLINE_FINAL {public String getName(){return "已申请线下支付尾款";}
//    public Operation getPreOper(){return Operation.APPLY_FINAL_PAYMENT_OFFLINE;}
//    public OrderStat[] getNextStats(){return new OrderStat[]{ACK};}},
    
    PASS_CHECK {public String getName(){return "来拍审核通过";}
    public Operation getPreOper(){return Operation.CHECK;}
    public OrderStat[] getNextStats(){return new OrderStat[]{FINISH_REFUND};}
    public String[] getRoleShow(){return new String[]{"U","M"};}},
    
//    FINISH_FINAL_PAYMENT {public String getName(){return "完成尾款支付";}
//    public Operation getPreOper(){return Operation.PAY_FINAL_PAYMENT_ONLINE;}
//    public OrderStat[] getNextStats(){return new OrderStat[]{ACK};}},
    
    /*已确认*/
    ACK {public String getName(){return "拍摄完成";}
    public Operation getPreOper(){return Operation.ACK;}
    public OrderStat[] getNextStats(){return new OrderStat[]{FINISH_PAY};}
    public String getTip(){return "点击确认，来拍付款给您";}
    /*只在摄影师端展示tip信息*/
    public String[] getTipRole(){return new String[]{"C"};}
    /*未操作展示tip信息*/
    public String[] getTipShowTime(){return new String[]{"0"};}},
    /*完成退款*/
    FINISH_REFUND {public String getName(){return "退款完成,等待银行转账";}
    public Operation getPreOper(){return Operation.REFUND;}
    public OrderStat[] getNextStats(){return new OrderStat[]{END};}
    public String[] getRoleShow(){return new String[]{"U","M"};}},
    
    /*完成打款*/
    FINISH_PAY {public String getName(){return "来拍已付款";}
    public Operation getPreOper(){return Operation.PAY_SELLER;}
    public OrderStat[] getNextStats(){return new OrderStat[]{END};}
    public String[] getRoleShow(){return new String[]{"C","M"};}};
    
//    EVALUATED {public String getName(){return "已评价";}
//    public Operation getPreOper(){return Operation.EVALUATE;}
//    public OrderStat[] getNextStats(){return null;}};
    
    public abstract String getName();
    /*获取上一次操作，经过该操作到达该状态节点*/
    public abstract Operation getPreOper();
    /*获取下几个状态*/
    public abstract OrderStat[] getNextStats();
    
    public String getTip(){
    	
    	return null;
    }
    /**
     * tip内容需要展示的角色
     * @return
     */
    public String[] getTipRole(){
    	
    	return new String[]{"U","C","M"};
    }
    /**
     * 实在操作状态完成展示(1)，还是在未操作状态(0)展示，默认都展示
     * @return
     */
    public String[] getTipShowTime(){
    	
    	return new String[]{"0","1"};
    }
    
  
    /**
     * 返回可展示该节点的角色集合
     * 默认，买家，摄影师，管理员都可展示
     * @return
     */
    public String[] getRoleShow(){
    	
    	return new String[]{"U","C","M"};
    }
    /**
     * 是否需要展示该节点
     * @param role
     * @return
     */
    public boolean canShow(String role, String[] roles){
    	
//    	String[] roles = getRoleShow();
    	if(roles == null || roles.length == 0){
    		return false;
    	}
    	
    	for(String r : roles){
    		if(r.equals(role)){
    			return true;
    		}
    	}
    	
    	return false;
    }

    /**
     * 根据名称获取状态枚举，调用valueOf,不抛出异常
     * @param val
     * @return 不存在返回null
     */
    public static OrderStat getValue(String val){
    	
    	if(val == null || "".equals(val.trim())){
    		return null;
    	}
    	
    	try {
			return valueOf(val);
		} catch (Exception e) {
			return null;
		}
    }
    /**
     * 是否为合法的下一个节点
     * @param nextStatName
     * @return
     */
    public boolean isValidNextStat(String nextStatName){
    	
    	OrderStat[] nextStats = getNextStats();
    	/*该节点本身没有下一节点，直接返回false*/
    	if(nextStats == null || nextStats.length==0){
    		return false;
    	}
    	/*如果存在下一个符合条件的节点，直接返回true*/
    	for(OrderStat stat : nextStats){
    		
    		if(stat.name().equals(nextStatName)){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public static void main(String args[]){
    	
//    	 for(OrderStat order : OrderStat.values()){
//             System.out.println(order.getName()+":"+OrderStat.START.ordinal());
//         }
    	
    	System.out.println(OrderStat.valueOf("EVALUATED1"));
    }
}
