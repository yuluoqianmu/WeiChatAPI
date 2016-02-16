package com.laipai.laiPaiClubInfo.action;



import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.dto.LpClubShowBean;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.opensymphony.xwork2.ModelDriven;
@Controller("laiPaiClubShareAction")
public class LaiPaiClubShareAction extends BaseAction implements ModelDriven<LpClubShowBean> {
	@Autowired
	private IBaseDao baseDao;
	
	private LpClubShowBean lpClubShowBean=new LpClubShowBean();
	@Override
	public LpClubShowBean getModel() {
		
		return lpClubShowBean;
	}
	
	public String shareClub(){
		String laipaiId=request.getParameter("laipaiId");
		VLpClub club = (VLpClub) baseDao.getObjectById(VLpClub.class,laipaiId);
		LpClubShowBean bean = new LpClubShowBean();
		BeanUtils.copyProperties(club, bean);
		bean.setCoverUrl(ImgUtil.getImgUrl(bean.getCoverUrl()));
//		bean.setContent(ImgUtil.getImgUrl(bean.getContent()));
		//评论数量
		int commentNumber=baseDao.getCount("from LpComment WHERE newsId='"+club.getLaipaiId()+"'");
		bean.setCommentNumber(commentNumber);
		request.setAttribute("laipaiBean", bean);
		return "success";
	}

}
