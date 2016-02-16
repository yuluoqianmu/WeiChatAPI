package com.laipai.laiPaiClubInfo.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.Global;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.TokenUtil;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.dto.LpClubShowBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.LpClubExtend;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 增加来拍社文章点赞
 * 
 * @author zhh
 */
@NotLogin
@Service("AddArticleLikeExecutorImpl")
public class AddArticleLikeExecutorImpl implements RequestExecutor {

	@Autowired
	private ILaiPaiClubService laiPaiClubService;
	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		//获取服务器域名
		String serverName = json.getString("serverName");
		String laipaiId = json.getString("laipaiId");
		String userId = json.getString("userId");
		try {
			//查询是否已经点过赞
			int likeNum = baseDao.getCount("from LpLike where lpUser.userId='"+userId+"' and newsId='"+laipaiId+"'");
			
			if(likeNum ==0){ //点赞
				LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, userId);
				LpLike like = new LpLike();
				like.setLikeStatus(1);
				like.setLikeTime(DateUtils.dateToTimestamp(new Date()));
				like.setLikeType(1);
				like.setNewsId(laipaiId);
				like.setLpUser(user);
		 		//添加赞详情
				baseDao.save(like);
				//修改文章赞数量
				LpClub club = (LpClub) baseDao.getObjectById(LpClub.class, laipaiId);
				club.setLikeNumber(club.getLikeNumber()+1);
				baseDao.updateObject(club);
				//修改假数据
				if(club.getControlSource() ==1){
					List<LpClubExtend> list = baseDao.queryListObjectAll("from LpClubExtend where laipaiId='"+laipaiId+"'");
					if(list!=null && list.size()>0){
						LpClubExtend extend = list.get(0);
						extend.setLikeNumber(extend.getLikeNumber()+1);
						baseDao.updateObject(extend);
					}
				}
				return JSONTools.newAPIResult(0, "点赞成功");
			}else{ //取消点赞
				baseDao.executeSql("delete from lp_like where user_id = '"+userId+"' and news_id='"+laipaiId+"'");
				//修改文章赞数量
				LpClub club = (LpClub) baseDao.getObjectById(LpClub.class, laipaiId);
				club.setLikeNumber(club.getLikeNumber()-1);
				baseDao.updateObject(club);
				//修改假数据
				if(club.getControlSource() ==1){
					List<LpClubExtend> list = baseDao.queryListObjectAll("from LpClubExtend where laipaiId='"+laipaiId+"'");
					if(list!=null && list.size()>0){
						LpClubExtend extend = list.get(0);
						extend.setLikeNumber(extend.getLikeNumber()-1);
						baseDao.updateObject(extend);
					}
				}
				return JSONTools.newAPIResult(0, "取消点赞成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONTools.newAPIResult(1, "点赞失败");
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
