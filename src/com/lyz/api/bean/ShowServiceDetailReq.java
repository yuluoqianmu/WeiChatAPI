package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 显示服务详情接口请求参数
 * @author luyongzhao
 *
 */
public class ShowServiceDetailReq extends BaseReq {
	
	@ValidateNotNull(message="服务id")
	@ValidateSize(message="服务id",minSize="10",maxSize="50")
	private String serviceId = "1111111111081";
	
	/*摄影师id，当上传的服务id为空时，该字段必须赋值*/
	private String cameraId;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		
		if(serviceId==null || "".equals(serviceId)){
			
			return;
		}
		this.serviceId = serviceId;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	
	
}
