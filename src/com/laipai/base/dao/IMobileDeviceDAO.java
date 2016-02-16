package com.laipai.base.dao;

import com.laipai.base.pojo.MobileDevice;


/**
 * 用户手机列表
 * 
 * @author ts
 * 
 */
public interface IMobileDeviceDAO extends IBaseDao {

	/**
	 * 以token得到手机信息
	 * 
	 * @param token
	 * @return
	 */
	public MobileDevice getMobileListByToken(String token);

	/**
	 * 以手机唯一ID得到设备
	 * 
	 * @param mobileId
	 * @return
	 */
	public MobileDevice getByMobileId(String userId, String mobileId);

	public MobileDevice getLastActivityDevice(Integer deviceId, String userId);

}