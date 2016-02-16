package com.lyz.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpUser;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;
import com.lyz.util.MD5Generator;

/**
 * 用户相关的信息查询
 * @author luzi
 *
 */
public class UserService {
	
	private static final Logger logger = Logger.getLogger(UserService.class);
	
	private static final IBaseDao<LpUser> userDao = new BaseDaoImpl<LpUser>();
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public static LpUser getUserInfo(String userId){
		
		if(userId == null || "".equals(userId.trim())){
			logger.warn("userId should be not empty:"+userId);
			return null;
		}
		
		String sql = "select nick_name,user_name,user_password,user_type,login_status,last_mobile_token,mobile,city_id,head_image from lp_user where user_id=?";
		
		return userDao.queryObject(sql, LpUser.class,userId);
	}
	/**
	 * 登陆，判断用户输入用户名和密码是否正确，不正确返回false，正确更新用户登陆状态
	 * @param userName
	 * @param password
	 * @return
	 */
	public static LpUser login(String userName,String password,String token){
		
		String sql = "select user_id,user_password,user_type,login_status from lp_user where user_name='"+userName+"'";
		
		LpUser user = userDao.queryObject(sql, LpUser.class);
		/*用户不存在*/
		if(user == null){
			return null;
		}
		/*用户密码错误*/
		if(!password.equals(user.getUserPassword())){
			return null;
		}
		/*如果已经登录，则不需要更新登录状态*/
		if(user.getLoginStatus() == 0){
			return user;
		}
		/*验证通过，修改用户登录状态为已登录*/
		String upSql = "update lp_user set login_status=0,last_mobile_token='"+token+"' where user_name='"+userName+"'";
		int count = userDao.upsertObject(upSql);
		if(count == 0){
			logger.error("fail to update user login status!userName="+userName);
			return null;
		}
		
		return user;
	}
	/**
	 * 保存注册用户的信息
	 * @param userName
	 * @param password
	 * @return 用户id
	 */
	public static String saveRegisterUser(String userName, String password, String nickName){
		
		LpUser user = new LpUser();
		String uid = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeUser)+"";
		user.setUserId(uid);
		user.setUserName(userName);
		user.setUserPassword(password);
		user.setNickName(nickName);
		/*普通用户*/
		user.setUserType(0);
		String sql = userDao.getSqlForInsert(user, "lp_user");
		
		int count = userDao.upsertObject(sql);
		
		if(count > 0){
			return uid;
		}
		
		return null;
	}
	/**
	 * 修改密码
	 * @param userName
	 * @param password
	 * @return
	 */
	public static int modifyPwd(String userName, String password){
		
		String sql = "update lp_user set user_password='"+password+"' where user_name='"+userName+"'";
		
		return userDao.upsertObject(sql);
		
	}
	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public static int modifyPwdByUserId(String userId, String password){
		
		String sql = "update lp_user set user_password='"+password+"' where user_id='"+userId+"'";
		
		return userDao.upsertObject(sql);
	}
	/**
	 * 该用户是否存在
	 * @param userName
	 * @return
	 */
	public static boolean exist(String userName){
		
		String sql = "select count(*) from lp_user where user_name='"+userName+"'";
		
		int count = userDao.count(sql);
		
		return count > 0;
	}
	
	
	/**
	 * 获取token
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public static String getToken(String userName, String pwd){
		
		return MD5Generator.MD5(userName+pwd);
	}
	
	public static void main(String args[]){
		
		LpUser user = UserService.getUserInfo("8a2a76634c5af695014c5b3f9f570043");
		System.out.println(user.getNickName());
	}
}
