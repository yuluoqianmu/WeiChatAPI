package com.laipai.userManInfo.dao;

import java.io.Serializable;
import java.util.List;

import com.laipai.base.dao.IBaseDao;
import com.laipai.userManInfo.pojo.LpComment;

public interface ICommentDao extends IBaseDao {
	public static final String COMENTDAO_NAME = "com.laipai.userManInfo.dao.impl.CommentDaoImpl";
	List<LpComment> getCommentBySql(String sqlString, Object... values);
	void saveComment(LpComment comment);
	void deleteById(String commentID);
	
	
}
