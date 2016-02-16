package com.laipai.cameraCheck.service;

import com.laipai.base.service.IBaseService;
import com.laipai.cameraCheck.pojo.LpInvite;

public interface IInviteService extends IBaseService{
	public static  final String IINVITESERVICE_NAME="com.laipai.cameraCheck.service.imple.InviteServiceImpl";

	LpInvite getInviteByCode(String invitecode);

	void updateInvite(LpInvite invite);

	LpInvite getInvite(String invitecode);
}
