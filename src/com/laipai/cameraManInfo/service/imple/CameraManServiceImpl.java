package com.laipai.cameraManInfo.service.imple;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.operationManage.dao.IOperationManageDao;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpLoginHis;
import com.laipai.userManInfo.pojo.LpUser;
@Transactional
@Service(ICameraManService.SERVICE_NAME)
public class CameraManServiceImpl extends BaseServiceImpl implements ICameraManService {
    @Resource(name=IOperationManageDao.DAO_NAME)
	private IOperationManageDao operationManageDao;
    @Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
    private IUserManInfoDao cameraManDao;
	
	/**
     * 获取city列表
     */
	@Override
	public List getallcity() {
		List list= operationManageDao.queryAllCity();
		return list;
	}
   /**
    * 获取系统风格列表
    */
	@Override
	public List getSysStyle() {
       List list=operationManageDao.queryAllStyle();
		return list;
	}
	/**
	 * 保存摄影师
	 */
@Override
public void saveCameraman(LpUser user, String cityId, String[] styleId) {
	if(cityId!=null){
	LpCity city= (LpCity) operationManageDao.getObjectById(LpCity.class,cityId);
	
	if(city!=null){
		user.setLpCity(city);		
	}
	}
	if(styleId!=null&&styleId.length>0){
    Set<LpStyle> styles=new HashSet<LpStyle>();
	for(int i=0;i<styleId.length;i++){
		LpStyle style=(LpStyle) operationManageDao.getObjectById(LpStyle.class, styleId[i]);
		styles.add(style);
	}
	user.setLpStyle(styles);
	}
	cameraManDao.saveOrUpdate(user);
}   
/**
 * 查询摄影师，按注册时间降序排列,分页显示，
* 也可以通过用户账号、用户昵称、注册时间三个条件来查询摄影师，按注册时间降序排列分页显示
 */
	@Override
	public List<LpUserBean> queyallBypage(HttpServletRequest request) {
		
		String userAccount = request.getParameter("userAccount");//用户账号
		String nickName = request.getParameter("nickName");//用户昵称
		String registTime = request.getParameter("registTime");//注册时间
		
		StringBuilder sb = new StringBuilder("from LpUser L where L.userType=1");
		if(!"".equals(userAccount)&&userAccount!=null){
			sb.append(" and L.userName like '"+userAccount+"%'");
		}
		if(!"".equals(nickName)&&nickName!=null){
			sb.append(" and L.nickName like '%"+nickName+"%'");
		}
		if(!"".equals(registTime)&&registTime!=null){
			sb.append(" and L.registerTime like '%"+registTime+"%'");
		}
		sb.append(" order by L.registerTime desc");
		//String hql = "from LpUser L where L.userType=1 order by L.registerTime desc";
		try {
			List list = querylistForPage(request, sb.toString(), 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 根据用户Id查询用户的风格
	 */
@Override
public List<LpStyle> getstyleByuser(String userId) {
	List list= cameraManDao.getstyleByuser(userId);
	return list;
}
	@Override
	public List<LpStyle> getallStyle() {
		List list= operationManageDao.queryAllEnableStyle();
		return list;
	}
	@Override
	public void deleteUserById(String userId) {
		cameraManDao.deleteUserById(userId);
		List<LpOrder> list= cameraManDao.queryListObjectAll("from LpOrder where lpUser.userId='"+userId+"'");
		if(list!=null&&list.size()>0){
			for(LpOrder order:list){
				cameraManDao.delete(order);
				
			}
			
		}
		
	}
	@Override
	public List<LpLoginHis> getLoginHis(String userId, int pageSize, int page) {
		
		try {
			return queryListObjectAllForPage("from LpLoginHis where user_id = '" + userId + "' order by loginTime desc", pageSize, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
