package com.laipai.laiPaiClubInfo.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;

public interface ILaiPaiClubService {
	public List queryAllArticle(HttpServletRequest request) throws Exception;
	
	public void saveArticle(LpClubBean lpClubBean);

	public void updateArticleOnline(String laipaiId,String status);

	public void deleteArticle(String laiPaiId);

	public LpClub getArticleDetail(String laiPaiId);
	
	public VLpClub getArticleViewDetail(String laiPaiId);

	public List<LpComment> queryCommentList(HttpServletRequest request, String laiPaiId);
	
	public boolean uploadImage(File in, File out);

	public void addLpComment(String laiPaiId, String commentDetail, LpUser user);

	public void deleteLpComment(String commentId, String laiPaiId);

	public LpUser getUserByName(String commenUserName);
	
}
