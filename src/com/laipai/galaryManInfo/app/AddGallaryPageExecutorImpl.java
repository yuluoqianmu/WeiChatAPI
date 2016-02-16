package com.laipai.galaryManInfo.app;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.serviceInfo.dto.AddGalaryPageServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.db.bean.LpMergedService;
import com.lyz.service.ServiceService;

/**
 * 
 * 打开作品集上传页面
 * 需要传给app服务的name,id
 * @author zhh
 */
@NotLogin
@Service("AddGallaryPageExecutorImpl")
public class AddGallaryPageExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		String userId = json.getString("userId");
		if(StringUtils.isEmpty(userId)){
			JSONTools.newAPIResult(1, "userId is null");
		}
		
//		List<LpService> list = baseDao.queryListObjectAll("from LpService where lpUser.userId='"+userId+"'");
		List<LpMergedService> list = ServiceService.getServiceList2(userId);
		if(list == null){
			JSONTools.newAPIResult(1, "service list is empty!");
		}
		List<AddGalaryPageServiceInfoBean> beanList = new ArrayList<AddGalaryPageServiceInfoBean>(); 
		for(int i=0;i<list.size();i++){
			AddGalaryPageServiceInfoBean bean = new AddGalaryPageServiceInfoBean();
			LpMergedService service = list.get(i);
			bean.setServiceId(service.getServiceId());
			bean.setServiceName((i+1)+"."+service.getServiceName());
			String priceUnit = "";
			if(service.getPriceUnit() != null){
				priceUnit = service.getPriceUnit();
			}
			bean.setPrice("￥"+((double)service.getPrice())/100+"/"+(priceUnit.contains("/")?priceUnit.trim().substring(1):priceUnit));
			beanList.add(bean);
		}
		AddGalaryPageServiceInfoBean bean = new AddGalaryPageServiceInfoBean();
		bean.setServiceId("private");
		bean.setServiceName((list.size()+1)+".私人订制");
		bean.setPrice("价格面议");
		beanList.add(bean);
		
		baseJson.put("serviceInfo", JSONConvertUtil.allObjectToJSON(beanList));
	    return baseJson;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
