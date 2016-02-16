package com.laipai.userManInfo.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFeedback;
import com.laipai.userManInfo.pojo.LpFeedbackView;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.Md5utils;
@Transactional
@Service(IUserManInfoService.SERVICE_NAME)
public class UserManInfoServiceImpl extends BaseServiceImpl implements IUserManInfoService{
	
	private static final Logger logger = Logger.getLogger(UserManInfoServiceImpl.class);
	
    @Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
	private IUserManInfoDao  userManInfoDao;
    @Autowired
	private IBaseDao baseDao;
    @Resource(name="orderDao")
    private OrderDao orderDao;
 
	/**
      * 查询所有用户
      */
	@Override
	public List<LpUser> queryAll() {
		//String sql="SELECT * FROM lp_user l ORDER BY l.register_time DESC";
		//List<LpUserBean> list= userDao.getListByHQL("from LpUser", null);	
		List<LpUser> list=userManInfoDao.queryAllUser();
		return list;
	}

	@Override
	public void saveOrUpdateser(LpUser userInfoBean) {
		//this.saveUser(userInfoBean);
		
		//获取用户ID
		String userID = userInfoBean.getUserId();
		if(StringUtils.isNotBlank(userID)){
			//2:使用用户ID，对用户表中更新数据（1条）
			baseDao.saveOrUpdate(userInfoBean);
		}
		else{
			//2：向用户表中插入数据
			baseDao.saveOrUpdate(userInfoBean);
		}
		
	}
    /**
     * 根据用户ID查询用户
     */
	@Override
	public LpUser queryUserById(String userId) {
		LpUser user= userManInfoDao.queryUserById(userId);
		return user;
	}
    /**
     * 根据用户ID查询用户
     */
	@Override
	public LpUser queryUserByName(String userName) {
		LpUser user= userManInfoDao.queryUserByName(userName);
		return user;
	}

	@Override
	public void deleteUserById(String userId) {
		LpUser user= userManInfoDao.queryUserById(userId);
		String headImg=user.getHeadImage();
		String IdImg=user.getIdCardImage();
		baseDao.delete(user);
		if(headImg!=null&&headImg.length()>0){
		this.deleteUserimg(headImg);
		}
		if(IdImg!=null&&IdImg.length()>0){
		this.deleteUserimg(IdImg);
		}
		List<LpOrder> list= baseDao.queryListObjectAll("from LpOrder where lpUser.userId='"+userId+"'");
		if(list!=null&&list.size()>0){
			for(LpOrder order:list){
				baseDao.delete(order);
				
			}
			
		}
		
	}
	/**
	 * add by LXD on$2014-12-22
	 * 查询普通用户，按注册时间降序排列,分页显示，
	 * 也可以通过用户账号、用户昵称、注册时间三个条件来查询用户，按注册时间降序排列分页显示
	 * */
	@Override
	public List queryAllBypage(HttpServletRequest request) {
		
		String userAccount = request.getParameter("userAccount");//用户账号
		String nickName = request.getParameter("nickName");//用户昵称
		String registTime = request.getParameter("registTime");//注册时间
		
		StringBuilder sb = new StringBuilder("from LpUser L where L.userType=0");
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
		//String hql = "from LpUser L where L.userType=0  order by L.registerTime desc";
		logger.debug(sb.toString());
		try {
			List list = querylistForPage(request, sb.toString(), 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<LpComment> queryComment(HttpServletRequest request,String userId) {
		String hql=" from LpComment lc where lc.lpUser.userId="+"'"+userId+"' and commentType=0 or commentType=4";
		try {
			List<LpComment> list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteCommentById(String commentId) {
		userManInfoDao.deleteCommentById(commentId);
		
	}

	@Override
	public List<LpFollow> queryFollow(HttpServletRequest request,String userId) {
		String hql=" from LpFollow lp where lp.lpUserByUserId.userId="+"'"+userId+"'";
		try {
			List<LpFollow> list=querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteFollowById(String followId) {
		userManInfoDao.deleteFollowById(followId);
	}

	@Override
	public List<LpLike> queryLike(HttpServletRequest request,String userId){
		String hql=" from LpLike ll where ll.lpUser.userId="+"'"+userId+"' and ll.likeStatus=0 and ll.likeType=0";
		try {
			List<LpLike> list=querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteLikeById(String likeId) {
		userManInfoDao.deleteLikeById(likeId);
	}

	@Override
	public List<LpGalary> queryGalary(HttpServletRequest request, String userId) {
		String hql=" from LpGalary where lpUser.userId='"+userId+"' and status=0 order by creatTime DESC";
		try {
			List<LpGalary> list=querylistForPage(request, hql, 6);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LpFollow> queryFans(HttpServletRequest request, String cameraId) {
		String hql=" from LpFollow lf where lf.lpUserByCameraId.userId='"+cameraId+"'";
		try {
			List<LpFollow> list=querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkUser(String userName) {
		String message = "";
		if(StringUtils.isNotBlank(userName)){
			LpUser user= this.queryUserByName(userName);
			//数据库中存在当前登录名
			if(user!=null ){
				message = "2";
			}
			//数据库中不存在当前登录名
			else{
				message = "3";
			}
		}
		else{
			message = "1";
		}
		return message;
	}

	@Override
	public List<String> getUserAccount(String account,String userType) {
		List<String> accountList=new ArrayList<String>();
		List<LpUser> list =new ArrayList<LpUser>();
		String sql="";
		//String sql="select * from lp_user L where user_type="+userType+" and L.user_name LIKE '%"+account+"%'";
		if(userType!=null&&!"".equals(userType)){
		sql="select * from lp_user  where user_name LIKE '%"+account+"%' and user_type=?";
		list=userManInfoDao.queryListByUserName(sql,userType);
		}else{
			
			sql="select * from lp_user  where user_name LIKE '%"+account+"%'";
			list=userManInfoDao.queryListByUserName(sql);
		}
		 
		if(list!=null&&list.size()>0){
			for(LpUser user:list){
				String userName=user.getUserName();
				accountList.add(userName);
				
			}
			
		}
		return accountList;
	}

	@Override
	public List<LpFeedbackView> queryFeedBackBypage(HttpServletRequest request) {
		/**
		 * add by LXD on$2014-12-22
		 * 分页查询文章列表
		 * */
		String hql = "from LpFeedbackView";
		try {
			List<LpFeedbackView> list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deleteUserimg(String imgPath){
/*		String winImg=imgPath.replace("/", "\\");*/
		File file=new File(imgPath);
		if(file.exists()){
			file.delete();
			
		}
		
	}

	@Override
	public List queryUserByNickName(String sql) {
		
		return userManInfoDao.queryListByNickName(sql);
	}

	@Override
	public int getCount(String hql) {
	 int number =baseDao.getCount(hql);
		return number;
	}

}
