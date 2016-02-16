package com.lyz.pay;
/**
 * 支付需要的额外信息
 * @author luyongzhao
 *
 */
public class Body {
	/*支付类型（1-全款，2-支付定金，3-线下支付）*/
	private int pt;
	
	public Body(int pt){
		
		this.pt = pt;
	}

	public int getPt() {
		return pt;
	}

	public void setPt(int pt) {
		this.pt = pt;
	}
	
	
}
