package com.laipai.orderInfo.app;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.service.IOrderService;
@NotLogin
@Service("ModifyOrderExecutorImpl")
public class ModifyOrderExecutorImpl implements RequestExecutor {

	@Resource(name="baseDao")
	private IBaseDao baseDao;
	@Autowired
	private IOrderService orderService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		System.out.println("ModifyOrderExecutorImpl");
		JSONObject json=parameters.getJson();
		//订单ID
		String orderId=json.getString("orderId");
		if(StringUtils.isEmpty(orderId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		LpOrder order=orderService.queryOrderByOrderId(orderId);
		if(order==null){
			return JSONTools.newAPIResult(1, "更新订单失败");
		}else{
			//订单状态设置成"已回电"
			String sql="update lp_order set order_status= 2 where order_id='"+orderId+"'";
			baseDao.executeSql(sql);
			return JSONTools.newAPIResult(0, "更新订单成功");
		}
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
