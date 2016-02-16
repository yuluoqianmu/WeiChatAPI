package com.laipai.cameraCheck.dao;

import org.springframework.stereotype.Repository;

import com.laipai.base.dao.IBaseDao;
import com.laipai.cameraCheck.pojo.LpInvite;
@Repository
public interface IInviteDao extends IBaseDao{
	public static final String IINVITEDAO_NAME="com.laipai.cameraCheck.dao.imple.InviteDaoImpl";

	LpInvite getInviteBycode(String sql,Object... values);
}
