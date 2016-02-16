package com.laipai.serviceInfo.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollowView;
@NotLogin
@Service("QueryServiceDetailExecutorImpl")
public class QueryServiceDetailExecutorImpl implements RequestExecutor{
	
	@Autowired
	private IServiceService serviceService;
	
	@Autowired
	private IBaseDao baseDao;
	/**
	 * 根据服务ID查询服务详情
	 */
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String serviceId=json.getString("serviceId");
		String page=JSONTools.getString(json,"page");
		String serverName = json.getString("serverName");
		if(StringUtils.isEmpty(serviceId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		LpService service=serviceService.queryById(serviceId);
		if(service!=null){
			JSONObject jsonObject=mapToJson(service,serverName,page);
			return jsonObject;
		}else{
			return JSONTools.newAPIResult(1, "查询失败");
		}
	}
	private JSONObject mapToJson(LpService service,String serverName,String page) {
		Map<String,Object> map=new HashMap<String,Object>();
		int galleryNumber=baseDao.getCount("from LpGalary WHERE lpService.serviceId='"+service.getServiceId()+"' and status=0 and galaryStatus=0");	
		
		//服务详情	
		map.put("gllarynumber",galleryNumber);
		map.put("serviceId", service.getServiceId());
		map.put("serviceName", service.getServiceName());
		map.put("detailId",service.getLpServiceDetail().getDetailId());
		map.put("shootTime", service.getLpServiceDetail().getShootTime());
		map.put("pictureNum", service.getLpServiceDetail().getPictureNum());
		map.put("truingNum", service.getLpServiceDetail().getTruingNum());
		map.put("clothes", service.getLpServiceDetail().getClothes());
		map.put("facepaint", service.getLpServiceDetail().getFacepaint());
		map.put("instructions", service.getLpServiceDetail().getInstructions());
		
		
		//价格
		double price=((double)service.getLpServiceDetail().getPrice())/100;
		map.put("price", price);
		String priceUnit=service.getLpServiceDetail().getPriceUnit();
		map.put("priceUnit", priceUnit);
		map.put("priceIOS", price+"/"+priceUnit);
		//风格
		List<LpStyle> styleSet=service.getLpServiceDetail().getLpStyle();
		StringBuffer styleName=new StringBuffer();
		StringBuffer styleId=new StringBuffer();
		for(LpStyle style:styleSet){
			styleName.append(style.getStyleName()).append("、");
			styleId.append(style.getStyleId()).append(",");
		}
		if(StringUtils.isNotEmpty(styleName+"") && (styleName+"").length()>0){
			map.put("styleName", styleName.substring(0, styleName.lastIndexOf("、")));
		}else{
			map.put("styleName", "");
		}
		if(StringUtils.isNotEmpty(styleId+"") && (styleId+"").length()>0){
			map.put("styleId", styleId.substring(0, styleId.lastIndexOf(",")));
		}else{
			map.put("styleId", "");
		}
		 
		//int galleryNumber=baseDao.getCount("from LpGalary WHERE lpUser.userId='"+userId+"' and status=0 and galaryStatus=0");	
		//作品集
		List<LpGalary> galaryList=getfanslist(service.getServiceId(),page);
		List list=new ArrayList();
		for(LpGalary galary:galaryList){
			Map<Object,Object> galaryMap=new HashMap<Object,Object>();
			String url= ImgUtil.getImgUrl(galary.getGalaryCover());
			galaryMap.put("url", url);
			galaryMap.put("galaryId", galary.getGalaryId());
			galaryMap.put("subjectName", galary.getSubjectName());
			galaryMap.put("commentNumber", galary.getCommentNumber()==null?0:galary.getCommentNumber().intValue());
			galaryMap.put("viewNumber", galary.getViewNumber()==null?0:galary.getViewNumber().intValue());
			galaryMap.put("likeNumber", galary.getLikeNumber()==null?0:galary.getLikeNumber().intValue());
			list.add(galaryMap);
		}
		map.put("galarySize", galaryList.size());
		map.put("galary", list);
		//摄影师头像
		if(service!=null && service.getLpUser()!=null && StringUtils.isNotEmpty(service.getLpUser().getHeadImage())){
			String url=ImgUtil.getImgUrl(service.getLpUser().getHeadImage(), "webp", 200);
			map.put("headImage", url);
		}else{
			map.put("headImage", "");
		}
		//摄影师城
		if(service.getLpUser().getLpCity()!=null){
		 map.put("city", service.getLpUser().getLpCity().getCityName());
		}
		JSONObject json=null;
		try {
			json=JSONObject.fromObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public List<LpGalary> getfanslist(String serviceId,String pageStr){
		int page=1;
		String hql = "from LpGalary where lpService.serviceId='"+serviceId+"' and status=0 and galaryStatus=0";
		List<LpGalary> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 5, page);
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
