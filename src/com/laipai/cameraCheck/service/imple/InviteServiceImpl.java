package com.laipai.cameraCheck.service.imple;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.cameraCheck.dao.IInviteDao;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.cameraCheck.service.IInviteService;
@Transactional
@Service(IInviteService.IINVITESERVICE_NAME)
public class InviteServiceImpl extends BaseServiceImpl implements IInviteService {
    @Resource(name=IInviteDao.IINVITEDAO_NAME)
	private IInviteDao  inviteDao;
	
	/**
     * 校验验证码
     */
	@Override
	public LpInvite getInviteByCode(String invitecode) {
		String sql="SELECT * FROM lp_invite l WHERE l.invite_code=? AND l.is_send=?";
		LpInvite invite= inviteDao.getInviteBycode(sql, invitecode,1);
		return invite;
	}

	@Override
	public void updateInvite(LpInvite invite) {
		inviteDao.updateObject(invite);
		
	}

	@Override
	public LpInvite getInvite(String invitecode) {
		String sql="SELECT * FROM lp_invite l WHERE l.invite_code=? AND l.is_send=? and l.invite_status=?";
		LpInvite invite= inviteDao.getInviteBycode(sql,invitecode,1,0);
		return invite;
	}
  
}
