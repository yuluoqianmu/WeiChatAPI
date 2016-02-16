package com.laipai.operationManage.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description:运营后台管理（城市和风格）
 * @author:朱鑫
 * @time:2014-12-26 上午10:11:18
 */
@Controller("operationManager")
public class OperationManagerAction extends BaseAction implements ModelDriven<LpCity>{

	@Resource
	private IOperationManagerService iOperationManagerService;
	
	private LpCity lpCity;
	
	private LpStyle lpStyle;
	
	/**
	 * 
	 * @Description:城市列表
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-26 上午10:13:16
	 */
	public String cityList()
	{
//		int nowPage = 1;
//		if(request.getParameter("nowPage") != null)
//		{
//			nowPage = Integer.parseInt(request.getParameter("nowPage"));
//		}
//		int pageSize = 30;
//		List<LpCity> cityList = iOperationManagerService.getAllCity(nowPage, pageSize);
//		
//		int pageCount = (int) iOperationManagerService.getCount(pageSize, LpCity.class);
//		int countSum = (int)iOperationManagerService.countSum(LpCity.class);
//		int length = cityList.size();
//		request.setAttribute("countSum", countSum);
//		request.setAttribute("nowPage", nowPage);
//		request.setAttribute("pageCount", pageCount);
//		request.setAttribute("cityList", cityList);
//		request.setAttribute("length", length);
		List list=this.iOperationManagerService.findLpCityList(request, "from LpCity where isTrueDelete = 1 order by cityLocation ",20);
		request.setAttribute("cityList", list);
		return "success";
	}
	/**
	 * 
	 * @Description:删除该城市
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-26 下午1:20:32
	 */
	public String deleteCity()
	{
		String cityId = request.getParameter("cityId");
		iOperationManagerService.deleteCity(cityId);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:增加城市
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-29 上午8:46:14
	 */
	public String addCity()
	{
		iOperationManagerService.saveOrUpdate(lpCity);
		return "ISOK";
	}
	public String updateCityLocation()
	{
		String cityId = request.getParameter("cityId");
		int newLocation = Integer.parseInt(request.getParameter("newLocation").trim());
		iOperationManagerService.updateCityLocation(cityId, newLocation);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:修改城市上线状态
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-30 上午10:50:21
	 */
	public String changeStatus()
	{
		boolean status = Boolean.parseBoolean(request.getParameter("cityStatus"));
		String cityId = request.getParameter("cityId");
		try {
			iOperationManagerService.changeStatus(status, cityId);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ISOK";
	}
	/**
	 * 
	 * @Description:查询该城市所有摄影师
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-16 下午5:41:40
	 */
	public String findAllMan()
	{
		String cityId = request.getParameter("cityId");
		List<LpUser> userList = iOperationManagerService.getAllMan(cityId);
		if(userList.size() == 0)
		{
			userList = null;
		}
		request.setAttribute("userList", userList);
		
		return "find";
	}
	
	
	private JSONObject jsonObject = new JSONObject();
	
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	public String validateCity()
	{
		String cityName = request.getParameter("cityName");
		LpCity lpCity = iOperationManagerService.findTheCity(cityName);
		jsonObject.put("cunzai", false);
		if(lpCity != null)
		{
			jsonObject.put("cunzai", true);
		}
		return SUCCESS;
	}
	
	@Override
	public LpCity getModel() {
		if(lpCity == null)
		{
			lpCity = new LpCity();
		}
		return lpCity;
	}
}
