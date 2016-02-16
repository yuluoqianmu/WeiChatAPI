package com.laipai.base.dao.imple;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;

@Repository("mobileDeviceDAO")
public class MobileDeviceDAOImpl extends BaseDaoImple implements IMobileDeviceDAO {

	public String getEntityName() {
		return MobileDevice.class.getName();
	}

	public MobileDevice getMobileListByToken(String token) {
		List list = queryListObjectByTopNum("from MobileDevice where  token='"+token+"'",1);
		MobileDevice drivce = (MobileDevice) list.get(0);
		return drivce;
	}

	public MobileDevice getByMobileId(String userId, String mobileId) {
		List list = queryListObjectByTopNum("from MobileDevice where  userId='"+userId+"' and mobileId='"+mobileId+"'",1);
		MobileDevice drivce = (MobileDevice) list.get(0);
		return drivce;
	}

	public MobileDevice getLastActivityDevice(Integer deviceId, String userId) {
		MobileDevice device = (MobileDevice) getObjectById(MobileDevice.class ,deviceId+"");;
		if (device != null){
			 return device;
		}
		List<MobileDevice> list = queryListObjectByTopNum("from MobileDevice where  userId='"+userId+"' and isExit=0 order by lastActivityTime desc", 1);
		if (list!=null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
