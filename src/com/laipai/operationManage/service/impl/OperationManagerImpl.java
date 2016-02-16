package com.laipai.operationManage.service.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.galaryManInfo.dto.VLpNewintroduceBean;
import com.laipai.galaryManInfo.pojo.LpNewintroduce;
import com.laipai.galaryManInfo.pojo.VLpNewintroduce;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.LpClubExtend;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.operationManage.dao.IOperationManageDao;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.pojo.VLpStyleListBack;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpFeedbackView;
import com.laipai.userManInfo.pojo.LpUser;
@Transactional
@Service("iOperationManagerService")
public class OperationManagerImpl extends BaseServiceImpl implements IOperationManagerService{
	@Resource
	private IOperationManageDao iOperationManageDao;
	@Autowired
	private IBaseDao baseDao ;
	/**
	 * 分页
	 */
	@Override
	public List<LpCity> getAllCity(int nowPage, int pageSize) {
		return iOperationManageDao.getAllCity(nowPage, pageSize);
	}
	@Override
	public void deleteCity(String cityId) {
		iOperationManageDao.deleteCity(cityId);
	}
	@Override
	public void saveOrUpdate(LpCity lpCity) {
		iOperationManageDao.saveOrUpdateCity(lpCity);
	}
	@Override
	public void changeStatus(boolean status, String cityId) {
		iOperationManageDao.changeStatus(status, cityId);
	}
	@Override
	public List<LpCity> getAllCity() {
		return iOperationManageDao.queryAllCity();
	}
	@Override
	public long getCount(int pageSize, Class clazz) {
		
		return iOperationManageDao.getCount(pageSize,clazz);
	}
	@Override
	public long countSum(Class clazz) {
		return iOperationManageDao.countSum(clazz);
	}
	@Override
	public List<LpStyle> getAllStyle(int nowPage, int pageSize) {
		return iOperationManageDao.getAllStyle(nowPage, pageSize);
	}
	@Override
	public void deleteStyle(String styleId) {
		iOperationManageDao.deleteStyle(styleId);
	}
	@Override
	public void addStyle(LpStyle lpStyle) {
		iOperationManageDao.addStyle(lpStyle);
	}
	@Override
	public LpStyle findTheStyle(String styleId) {
		return iOperationManageDao.findTheStyle(styleId);
	}
	@Override
	public void updateStyle(LpStyle lpStyle) {
		iOperationManageDao.updateStyle(lpStyle);
	}
	@Override
	public LpCity findTheCity(String cityName) {
		return iOperationManageDao.findTheCity(cityName);
	}
	@Override
	public List<LpStyle> getallEnableStyle() {
		
		return iOperationManageDao.queryAllEnableStyle();
	}
	@Override
	public List<LpCity> getAllOnlineCity() {
		String sql=" SELECT * FROM lp_city WHERE is_true_delete=1  and city_status=1 order by city_location";
		List<LpCity> list= iOperationManageDao.getAllOnlineCity(sql);
		return list;
	}
	@Override
	public LpCity getCityById(String cityId) {
		LpCity city= (LpCity) baseDao.getObjectById(LpCity.class, cityId);
		return city;
		
	}
	@Override
	public List<LpUser> getAllMan(String cityId) {
		return iOperationManageDao.getAllMan(cityId);
	}
	@Override
	public List<LpInvite> queryCodeBypage(HttpServletRequest request) {
		/**
		 * add by LXD on$2014-12-22
		 * 分页查询文章列表
		 * */
		String hql = "from LpInvite order by createtime DESC";
		try {
			List<LpInvite> list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public LpInvite getIniBycode(String code) {
		String hql = "from LpInvite where inviteCode='"+code+"'";
		try {
		  List list=baseDao.queryListObjectAll(hql);
		  if(list!=null&&list.size()>0){
			  
			  return (LpInvite) list.get(0);
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void saveInvite(Set<String> newSet) {
     Iterator it = newSet.iterator();
		try{
		while(it.hasNext()){
			
			LpInvite invite =new LpInvite();
			String code=it.next().toString();
			LpInvite Queryinvite=getIniBycode(code);
			if(Queryinvite==null){
			invite.setCreatetime(new Timestamp(new java.util.Date().getTime()));
			invite.setInviteCode(code);
			invite.setIsSend(0);
			invite.setInviteStatus(0);
			baseDao.save(invite);
			}
		}
		}catch(Exception e){
			
			e.printStackTrace();	
		}
	}

	@Override
	public void updateCityLocation(String cityId, int newLocation) {
		iOperationManageDao.updateCityLocation(cityId, newLocation);
	}
	@Override
	public void updateStyleLocation(String styleId, int newLocation) {
		iOperationManageDao.updateStyleLocation(styleId, newLocation);
	}
	@Override
	public void changeStyleStatus(String styleId, int status) {
		iOperationManageDao.changeStyleStatus(styleId, status);
	}
	@Override
	public List<LpUser> findAllStyleMan(String styleId) {
		return iOperationManageDao.findAllStyleMan(styleId);
	}

	/**
	 * add by zhanhh on$2014-12-22
	 * 分页查询首页推广列表
	 * */
	public List<VLpNewintroduce> queryHomePageIntroduce(HttpServletRequest request) throws Exception{
		String hql = "from VLpNewintroduce where status!=2";
		try {
			//hql分页查询
			List list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateArticleOnline(String introduceId, String status){
		LpNewintroduce intro = (LpNewintroduce) baseDao.getObjectById(LpNewintroduce.class, introduceId);
		intro.setStatus(Integer.parseInt(status));
		if("1".equals(status)){//上线
			baseDao.executeSql("update lp_newintroduce set status=0 where status=1");
			intro.setOnLineTime(DateUtils.parseToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}else{
			intro.setOffLineTime(DateUtils.parseToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
		baseDao.updateObject(intro);
	}
	
	public boolean hasOtherOnline() throws Exception{
		int check = baseDao.getCount("from LpNewintroduce where status=1");
		if(check ==0){
			return false;
		}
		return true;
	}
	
	/**
	 * 删除文章
	 * */
	public void deleteArticle(String introduceId){
		try {
			LpNewintroduce club = null;
			String[] arryId = introduceId.split(",");
			for(int i=0;i<arryId.length;i++){
				club = (LpNewintroduce) baseDao.getObjectById(LpNewintroduce.class, arryId[i]);
				if(club !=null){
					club.setStatus(2);
					baseDao.updateObject(club);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看详情
	 * */
	public VLpNewintroduce getArticleViewDetail(String laiPaiId){
		VLpNewintroduce club = new VLpNewintroduce();
		try {
			if(StringUtils.isNotEmpty(laiPaiId)){
				club = (VLpNewintroduce) baseDao.getObjectById(VLpNewintroduce.class,laiPaiId);
//				club.setImgUrl(club.getImgUrl().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
//				club.setContent(club.getContent().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
				club.setImgUrl(ImgUtil.getImgUrl(club.getImgUrl()));
				club.setContent(ImgUtil.getImgUrl(club.getContent()));
			}
			return club;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveArticle(VLpNewintroduceBean introduceBean){
		try {
			LpNewintroduce club = new LpNewintroduce();
			if(StringUtils.isNotEmpty(introduceBean.getIntroduceId())){
				club = (LpNewintroduce) baseDao.getObjectById(LpNewintroduce.class, introduceBean.getIntroduceId());
				club.setTitle(introduceBean.getTitle());
				if(StringUtils.isNotEmpty(introduceBean.getImgUrl())){
					club.setImgUrl(introduceBean.getImgUrl());
				}
				String newcontent=introduceBean.getContent();
				if(newcontent!=null && newcontent.contains("src")){
//					String[] arr =  newcontent.split("src");
//					newcontent = arr[0]+" width=\"100%\" src"+arr[1];
					newcontent = newcontent.replace("src=", "width=\"100%\" src=");
				}	
				
				club.setContent(newcontent);
				baseDao.saveOrUpdate(club);
			}else{//新建文章
				club.setStatus(0); // 状态  0下线  1上线(文章刚新建时，默认下线)
				club.setCreateTime(DateUtils.parseToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
				//保存文章标题,封面
				club.setTitle(introduceBean.getTitle());
				if(StringUtils.isNotEmpty(introduceBean.getImgUrl())){
					club.setImgUrl(introduceBean.getImgUrl());
				}
				
				String newcontent=introduceBean.getContent();
				if(newcontent!=null && newcontent.contains("src")){
//					String[] arr =  newcontent.split("src");
//					newcontent = arr[0]+" width=\"100%\" src"+arr[1];
					newcontent = newcontent.replace("src=", "width=\"100%\" src=");
				}				
				club.setContent(newcontent.replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL));
				Serializable laipaiId = baseDao.saveObjectReturnId(club);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<LpCity> findLpCityList(HttpServletRequest request, String hql,
			int pageSize) {
		 List<LpCity> list=null;
		try {
			list=this.querylistForPage(request, hql, pageSize);
			for (LpCity lpCity : list)
			{
				Integer manCount =0;
				try {
					Object o = this.baseDao.getSession().createSQLQuery("select count(user_id) from lp_user where user_type = 1 and city_id = '"+lpCity.getCityId()+"'").uniqueResult();
					manCount = Integer.valueOf(o.toString());	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				lpCity.setCammerManNumber(manCount);
				//this.baseDao.getSession().update(lpCity);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<VLpStyleListBack> getAllStyleByView(HttpServletRequest request){
		String hql = "from VLpStyleListBack where isTrueDelete !=0 order by styleLocation";
		try {
			//hql分页查询
			List list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String s = "abcdef";
		int index = s.indexOf("c");
		System.out.println(index);
	}
	@Override
	public List<LpInvite> queryCodeBypage(HttpServletRequest request,
			String status) {
		//status:0已发未用  1:已发已用  2:未发未用
		int checkStatu = Integer.parseInt(status);
		String hql = "";
		if(checkStatu==0)
		{
			 hql = "from LpInvite where isSend=1 and inviteStatus=0 order by createtime desc";
		}
		else if(checkStatu==1)
		{
			 hql = "from LpInvite where isSend=1 and inviteStatus=1 order by createtime desc";
		}
		else
		{
			 hql = "from LpInvite where isSend=0 and inviteStatus=0 order by createtime desc";
		}
		try {
			List list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int getSendUnused() {
		Object o = iOperationManageDao.getSession().createSQLQuery("select count(*) from lp_invite where is_send=1 and invite_status=0" ).uniqueResult();
		Integer Count = Integer.valueOf(o.toString());
		return Count;
	}
	@Override
	public int getSendUsed() {
		Object o = iOperationManageDao.getSession().createSQLQuery("select count(*) from lp_invite where is_send=1 and invite_status=1" ).uniqueResult();
		Integer Count = Integer.valueOf(o.toString());
		return Count;
	}
	@Override
	public int getUnsendUnused() {
		Object o = iOperationManageDao.getSession().createSQLQuery("select count(*) from lp_invite where is_send=0 and invite_status=0" ).uniqueResult();
		Integer Count = Integer.valueOf(o.toString());
		return Count;
	}
	
}
