package com.laipai.operationManage.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.pojo.VLpStyleListBack;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description:风格管理
 * @author:朱鑫
 * @time:2014-12-30 上午9:32:42
 */
@Controller("styleManager")
public class StyleManagerAction extends BaseAction implements ModelDriven<LpStyle>{

	@Resource
	private IOperationManagerService iOperationManagerService;
	
	private LpStyle lpStyle;
	@Override
	public LpStyle getModel() {
		if(lpStyle == null)
		{
			lpStyle = new LpStyle();
		}
		return lpStyle;
	}
	/**
	 * 
	 * @Description:查询所有风格
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-29 下午6:01:51
	 */
	public String styleList()
	{
//		int nowPage = 1;
//		int pageSize = 30;
//		if(request.getParameter("nowPage") != null)
//		{
//			nowPage = Integer.parseInt(request.getParameter("nowPage"));
//		}
//		List<LpStyle> list = iOperationManagerService.getAllStyle(nowPage, pageSize);
//		int length = list.size();
//		int pageCount = (int) iOperationManagerService.getCount(pageSize,LpStyle.class);
//		int countSum = (int)iOperationManagerService.countSum(LpStyle.class);
//		request.setAttribute("length", length);
//		request.setAttribute("countSum", countSum);
//		request.setAttribute("nowPage", nowPage);
//		request.setAttribute("pageCount", pageCount);
		
		List<VLpStyleListBack> list = iOperationManagerService.getAllStyleByView(request);
		request.setAttribute("list", list);
		return "success";
	}
	public String updateStyleLocation()
	{
		String styleId = request.getParameter("styleId");
		int newLocation = Integer.parseInt(request.getParameter("newLocation").trim());
		System.out.println(styleId + "----" + newLocation);
		iOperationManagerService.updateStyleLocation(styleId, newLocation);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:删除风格
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-29 下午6:02:39
	 */
	public String deleteStyle()
	{
		String styleId = request.getParameter("styleId");
		iOperationManagerService.deleteStyle(styleId);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:新增风格
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-29 下午6:03:27
	 */
	public String addStyle()
	{
		iOperationManagerService.addStyle(lpStyle);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:根据Id查找风格
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-30 上午9:33:35
	 */
	public String findStyleById()
	{
		String styleId = request.getParameter("styleId");
		LpStyle lpStyle = iOperationManagerService.findTheStyle(styleId);
		request.setAttribute("lpStyle", lpStyle);
		return "FIND";
	}
	
	public String updateStyle()
	{
		iOperationManagerService.updateStyle(lpStyle);
		return "ISOK";
	}
	
	public String updateStyleStatus()
	{
		String styleId = request.getParameter("styleId");
		int styleStatus = Integer.parseInt(request.getParameter("styleStatus").trim());
		iOperationManagerService.changeStyleStatus(styleId, styleStatus);
		return "ISOK";
	}
	
	public String findAllMan()
	{
		String styleId = request.getParameter("styleId");
		List<LpUser> userList = iOperationManagerService.findAllStyleMan(styleId);
		for (LpUser lpUser : userList) {
			System.out.println(lpUser.getNickName());
		}
		if(userList.size() == 0)
		{
			userList = null;
		}
		request.setAttribute("userList", userList);
		return "find2";
	}
}
