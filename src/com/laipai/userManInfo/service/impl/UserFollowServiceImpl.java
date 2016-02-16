package com.laipai.userManInfo.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.DateUtils;
import com.laipai.userManInfo.dao.IUserFollowDao;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserFollowService;
import com.laipai.userManInfo.service.IUserManInfoService;

@Service("userFollowService")
public class UserFollowServiceImpl extends BaseServiceImpl implements IUserFollowService  {

	public static Logger logger = Logger.getLogger(UserFollowServiceImpl.class);
	
	 @Autowired
	    private IBaseDao baseDao; 
	
	@Autowired
	private IUserFollowDao userFollowDao;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	
	@Override
	public boolean deleteFollowByIds(String[] followIds, LpUser operator) {
		return userFollowDao.deleteFollowByIds(followIds, operator);
	}

	@Override
	public boolean saveFollow(String followId, String userId, String cameraId, String followTime, LpUser operator) {
		if(followId == null || followId.trim().length() == 0)
		{
			followId = UUID.randomUUID().toString();
		}
		
		LpFollow f = new LpFollow();
		f.setFollowId(followId);
		
		LpUser user = new LpUser();
		user.setUserId(userId);
		f.setLpUserByUserId(user);

		LpUser cameraUser = new LpUser();
		cameraUser.setUserId(cameraId);
		f.setLpUserByCameraId(cameraUser);
		String followTime_ = followTime;
		if(followTime_ != null)
		{
			Timestamp t = DateUtils.stringToTimestamp(followTime_, null);
			f.setFollowTime(t);
		}
		else
		{
			f.setFollowTime(new Timestamp(System.currentTimeMillis()));
		}
		
		userFollowDao.save(f);
		
		return true;
	}

	@Override
	public LpFollowView getFollowViewById(String followId) {
		
		logger.info("followId = " + followId);
		
		List<LpFollowView> fs = userFollowDao.queryListObjectAll("from LpFollowView where follow_id = '" + followId + "'");
		
		logger.info("followViews = " + fs);
		
		return (fs!=null&&fs.size() > 0)?fs.get(0):null; 
	}
    
	/**
	 * 
	 */
	@Override
	public List<LpFollowView> getFollowByuserId(String userId) {
		return userFollowDao.getFollowbyUserId(userId);
		
	}
	
	public boolean saveFollow2(String userId,String cameraId){
		
		LpUser user= userManService.queryUserById(userId);
		LpUser camera= userManService.queryUserById(cameraId);
		LpFollow follow =new LpFollow();
		follow.setLpUserByUserId(user);
		follow.setLpUserByCameraId(camera);
		follow.setFollowTime(new Timestamp(new java.util.Date().getTime()));
		baseDao.save(follow);
		
		return true;
	}

	@Override
	public List<LpFollow> checkFollow(String userId, String cameraId) {
		String sql="select * from lp_follow l where l.user_id='"+userId+"' and l.camera_id='"+cameraId+"'";
		List<LpFollow> list=baseDao.querySqlObject(sql);
		return list;
	}

	@Override
	public void deleteFollow(String followId) {
		LpFollow follow= (LpFollow) baseDao.getObjectById(LpFollow.class, followId);
		
		baseDao.delete(follow);
	}
	
	
}
