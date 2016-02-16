package com.laipai.orderInfo.app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollowView;
@NotLogin
@Service("QueryOrderExecutorImpl")
public class QueryOrderExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		System.out.println("QueryOrderExecutorImpl");
		JSONObject json=parameters.getJson();
		//获取服务器域名
	    String serverName = json.getString("serverName");
		String cameraId=json.getString("userId");
		String page=JSONTools.getString(json,"page");
		if(StringUtils.isEmpty(cameraId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		/*String hql="from LpOrder lp where lp.lpCamera.userId='"+cameraId+"'";
		List<LpOrder> orderList=baseDao.queryListObjectAll(hql);*/
		
		List<LpOrder> orderList=this.getfanslist(cameraId, page);
		
		if(orderList.size()==0){
			return JSONTools.newAPIResult(0, "当前没有订单");
		}
		JSONArray jsonArray=listToArray(parameters,orderList,serverName);
		return jsonArray;
	}

	private JSONArray listToArray(BaseRequestParameters parameters, List<LpOrder> orderList,String serverName) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(LpOrder order:orderList){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("orderId", order.getOrderId());
			//作品集未被删除的情况
			LpGalary galary=order.getLpGalary();
			if(galary!=null){
				//作品集ID
				map.put("galaryId", order.getLpGalary().getGalaryId());
				//作品集封面
//				String galaryCoverUrl=serverName+order.getLpGalary().getGalaryCover();
				String galaryCover=ImgUtil.getImgUrl(order.getLpGalary().getGalaryCover(),parameters.getPicType());	
				map.put("galaryCover", galaryCover);
				//作品集主题
				map.put("subjectName", order.getLpGalary().getSubjectName());
			}else{
				//作品集ID
				map.put("galaryId", "");
				map.put("galaryCover", "该作品集已被删除");
				//作品集主题
				map.put("subjectName", "该作品集已被删除");
			}
			//预订人
			map.put("userId", order.getLpUser().getUserId());
			map.put("userName", order.getLpUser().getUserName());
			//昵称
			map.put("nickName", order.getLpUser().getNickName());
			//用户头像
//			if(order.getLpUser().getAccountSource()==0){
////			String url=serverName+order.getLpUser().getHeadImage();
//			String headImage=ImgUtil.getImgUrl(order.getLpUser().getHeadImage());
//			map.put("headImage",headImage);
//			}else{
//				map.put("headImage",order.getLpUser().getHeadImage());	
//				
//			}
			String headImage=ImgUtil.getImgUrl(order.getLpUser().getHeadImage(),parameters.getPicType());
			map.put("headImage",headImage);
			//摄影师
			map.put("cameraId", order.getLpCamera().getUserId());
			//摄影师昵称
			map.put("cameraName", order.getLpCamera().getNickName());
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
			//订单状态
			map.put("typeId", order.getTypeId());
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
			//留言
			Set<LpComment> lcList=order.getLpComments();
			if(lcList.size()>0){
				LpComment comment=(LpComment) lcList.toArray()[0];
				String commentDetail=comment.getCommentDetail();
				String newCommentDetail=replaceBlank(commentDetail);
				map.put("comment", comment.getCommentDetail());
			}else{
				map.put("comemnt", "");
			}
			
			list.add(map);
		}
		JSONArray array=null;
		try {
			array = JSONArray.fromObject(list);
		} catch (Exception e) {
			e.printStackTrace();
			array=JSONArray.fromObject(JSONTools.newAPIResult(1, "当前没有订单"));
		}
		return array;
	}
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	
	public List<LpOrder> getfanslist(String userId,String pageStr){
		int page=1;
		String hql="from LpOrder lp where lp.lpCamera.userId='"+userId+"' and lp.lpGalary.status=0 order by lp.createTime DESC";
		List<LpOrder> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
		}
		
		List<LpOrder> newList = new ArrayList<LpOrder>();;
		if(list!=null && !list.isEmpty()){
			for(LpOrder order : list){
				LpGalary lpGalary = order.getLpGalary();
//				if(lpGalary !=null && lpGalary.getStatus()==0){ //过滤已经删掉的作品集的订单
					newList.add(order);
//				}
			}
		}
		return newList;
		
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
}
