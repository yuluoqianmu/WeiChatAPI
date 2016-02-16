package com.laipai.app.process.app.dataobj;

import java.io.Serializable;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import com.laipai.base.pojo.MobileDevice;


public class BaseRequestParameters implements Serializable {

	private static final long serialVersionUID = -7269369320127063136L;

	private int appType;
	private int apiVersion;
	private int osType;
	private int requestId;
	/*默认jpg，如果赋值为webp，则使用webp格式*/
	private String picType;

	private String mobileId;

	private String token;
	private int page;
	private int pageSize;

	private Map<String, FileItem> fileMap;
	
	private MobileDevice mobileDevice;// 请求的设备对像

	private JSONObject json;// 所有request参数
	/*客户端版本*/
	private String version;

	public BaseRequestParameters(JSONObject json, Map<String, FileItem> fileMap) {
		this.json = json;
		this.fileMap = fileMap;
	}
	
	public Map<String, FileItem> getFileMap() {
		return fileMap;
	}

	public JSONObject getJson() {
		return json;
	}

	public int getAppType() {
		return appType;
	}

	public void setAppType(int appType) {
		this.appType = appType;
	}

	public int getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(int apiVersion) {
		this.apiVersion = apiVersion;
	}

	public int getOsType() {
		return osType;
	}

	public void setOsType(int osType) {
		this.osType = osType;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public MobileDevice getMobileDevice() {
		return mobileDevice;
	}

	public void setMobileDevice(MobileDevice mobileDevice) {
		this.mobileDevice = mobileDevice;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	

}
