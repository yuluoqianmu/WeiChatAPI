package com.laipai.orderInfo.app;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.orderInfo.dto.OrderBean;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.service.IOrderService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
@NotLogin
@Service("QueryUserOrderExecutorImpl")
public class QueryUserOrderExecutorImpl implements RequestExecutor{
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService manInfoService;
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IOrderService orderService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {

		try {
			JSONObject json=parameters.getJson();
			//用户ID
			String userId=json.getString("userId");
			String page=JSONTools.getString(json,"page");
			//获取服务器域名
		    String serverName = json.getString("serverName");
			if(StringUtils.isEmpty(userId)){
				return JSONTools.newAPIResult(1, "参数不能为空");
			}
			/*String hql="from LpOrder lp where lp.lpUser.userId='"+userId+"'";
			List<LpOrder> orderList=baseDao.queryListObjectAll(hql);*/
			
			List<LpOrder> orderList=this.getfanslist(userId, page);
			List<Map> list=new ArrayList<Map>();
			for(LpOrder order:orderList){
				Map<String,Object> map=new HashMap<String,Object>();
				//订单中作品集未被删除的情况
				LpGalary galary=order.getLpGalary();
				if(galary!=null){
					//作品集ID
					map.put("galaryId", order.getLpGalary().getGalaryId());
					//作品集封面
					String url=ImgUtil.getImgUrl(order.getLpGalary().getGalaryCover(),parameters.getPicType());
					map.put("galaryCover", url);
					//作品集主题
					map.put("subjectName", order.getLpGalary().getSubjectName());
				}else{
					//作品集ID
					map.put("galaryId", "");
					map.put("galaryCover", "该作品集已被删除");
					//作品集主题
					map.put("subjectName", "该作品集已被删除");
				}
				//订单ID
				map.put("orderId", order.getOrderId());
				//订单状态
				String orderStatus="";
				switch(order.getOrderStatus()){
				 case 0:
				    orderStatus="已支付";
				   	break;
				 case 1:
					 orderStatus="未支付";
					 break;
				 case 2:
					 orderStatus="已回电";
					 break;
				 case 3:
					 orderStatus="待回电";
					 break;
				 case 4:
					 orderStatus="已取消";
					 break;
				}
				map.put("orderStatus",orderStatus);
				//订单时间
				Timestamp createTime=order.getCreateTime();
				DateFormat date=new SimpleDateFormat("yyyy年MM月dd日");
				String dateToString="";
				try {
					dateToString=date.format(createTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.put("createTime", dateToString);
				list.add(map);
			}
			JSONArray jsonArray=JSONArray.fromObject(list);
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "查询客户预约失败");
		}
	}
	public List<LpOrder> getfanslist(String userId,String pageStr){
		int page=1;
		//and lp.lpGalary.status=0
		String hql="from LpOrder lp where lp.lpUser.userId='"+userId+"' and lp.lpGalary.status=0 order by lp.createTime DESC";
		List<LpOrder> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
		}
		
		return list;
		
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
