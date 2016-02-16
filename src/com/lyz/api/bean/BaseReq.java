package com.lyz.api.bean;

import com.lyz.util.BaseTypeUtil;
import com.lyz.util.MD5Generator;
import com.lyz.validate.AnnotationValidable;
import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotEmpty;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;

/**
 * 基础请求类，所有的请求都要继承该请求
 * 注意：android的id优先级为imei>mac加密；ios优先级为idfa>mac加密>openudid
 * @author luyongzhao
 *
 */
public abstract class BaseReq implements AnnotationValidable{
	
	/*服务端生成的key，发往不同渠道的包会有不同的key*/	
	@ValidateNotNull(message="key")
	@ValidateSize(minSize="1",maxSize="100",message="key")
	private String key;
	
	@ValidateNotNull(message="客户端版本")
	@ValidateSize(minSize="1",maxSize="100",message="客户端版本")
	private String ver;
	
	/*设备id，android为imei，ios为idfa*/
	@ValidateNotNull(message="设备id")
	@ValidateSize(minSize="1",maxSize="100",message="设备id")
	private String id;
	/*mac地址去除中间分隔符经过md5加密32位大写*/
	private String pyId;
	/*ios的id*/
	private String openudid;
	/*用户登录id*/
	private String uid;
	
	@ValidateNotNull(message="客户端类型")
	@ValidateSize(minSize="1",maxSize="100",message="客户端类型")
	@ValidateStringIn(value="iphone,ipad,androidphone,andoirdpad,winpad,winphone",message="客户端类型")
	private String pt;
	/*md5校验值，32位大写*/
	@ValidateNotNull(message="校验值")
	@ValidateSize(minSize="32",maxSize="32",message="校验值")
	private String r;
	@ValidateLong(min=1000,message="时间戳")
	private long t;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	/**
	 * md5校验
	 * @param val 需要md5加密的串
	 * @return
	 */
	public boolean isValidMd5(String val){
		
		if(val==null || "".equals(val.trim())){
			return false;
		}
		String md5Val = MD5Generator.MD5(val);
		return md5Val.equals(r);
	}
	
	
	
	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}
	
	

	public String getPyId() {
		return pyId;
	}

	public void setPyId(String pyId) {
		this.pyId = pyId;
	}

	public String getOpenudid() {
		return openudid;
	}

	public void setOpenudid(String openudid) {
		this.openudid = openudid;
	}

	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		return builder.append(BaseTypeUtil.getTodayTime("yyyy-MM-dd HH:mm:ss")).append("\t")
		.append(key).append("\t")
		.append(id).append("\t")
		.append(ver).toString();
	}
	
	
}
