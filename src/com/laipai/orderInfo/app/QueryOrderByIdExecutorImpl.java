package com.laipai.orderInfo.app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.img.ImgUtil;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.service.IOrderService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
@NotLogin
@Service("QueryOrderByIdExecutorImpl")
public class QueryOrderByIdExecutorImpl implements RequestExecutor{

	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IBaseDao baseDao;
	
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		System.out.println("QueryOrderByIdExecutorImpl");
		try {
			JSONObject json=parameters.getJson();
			//获取服务器域名
			String serverName = json.getString("serverName");
			//订单ID
			String orderId=json.getString("orderId");
			LpOrder order=orderService.queryOrderByOrderId(orderId);
			if(order==null){
				return JSONTools.newAPIResult(1, "查询订单详情失败");
			}
			Map<String,Object> map=new HashMap<String,Object>();
			//用户账号
			map.put("userName", order.getLpCamera().getUserName());
			map.put("userId", order.getLpCamera().getUserId());
			//普通用户昵称
			map.put("nickName", order.getLpCamera().getNickName());
			//普通用户头像
//			if(order.getLpCamera().getAccountSource()==0){
//				String url=serverName+order.getLpCamera().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL,LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL);
//				map.put("headImage",url);			
//			}else{
//				map.put("headImage",order.getLpCamera().getHeadImage());			
//			}
			map.put("headImage",ImgUtil.getImgUrl(order.getLpCamera().getHeadImage(),parameters.getPicType()));
			//预留电话
			if(order.getOrderMobile()!=null){
			map.put("orderMobile", order.getOrderMobile());
			}else{
				
				map.put("orderMobile", "");
			}
			//留言
			Set<LpComment> comments=order.getLpComments();
			LpComment comment = (LpComment) comments.toArray()[0];
			if(comments.size()>0){
				map.put("comment",comment.getCommentDetail());
			}else{
				map.put("comment", "");
			}
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
			//订单日期
			Timestamp createTime=order.getCreateTime();
			DateFormat date=new SimpleDateFormat("yyyy年MM月dd日");
			String dateToString="";
			try {
				dateToString=date.format(createTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("createTime", dateToString);
			//摄影师电话
			if(order.getLpCamera().getMobile()!=null){
			map.put("cameraMobile", order.getLpCamera().getMobile());
			}else{
				
				map.put("cameraMobile", order.getLpCamera().getUserName());
			}
			//摄影师所在城市
			map.put("cameraCity", order.getLpCamera().getLpCity().getCityName());
			//作品集id
			map.put("galaryId", order.getLpGalary().getGalaryId());
			//预约作品集的主题
			map.put("subjectName", order.getLpGalary().getSubjectName());
			//作品集价格
			if(order.getLpService()!=null){
				//价格
				map.put("price", ((double)order.getLpService().getLpServiceDetail().getPrice())/100);
				//价格单位
				map.put("priceUnit", order.getLpService().getLpServiceDetail().getPriceUnit());
			}else{
				//私人订制
				map.put("price", "");
				map.put("priceUnit","");
			}
			return JSONObject.fromObject(map);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "查询订单详情失败");
		}
		
	}


	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
