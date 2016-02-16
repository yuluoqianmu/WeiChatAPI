package com.lyz.api.bean;
/**
 * 微信开发者接口，微信回掉验证
 * @author luyongzhao
 *
 */
public class WxDevCheckReq extends BaseReq{
	
	/**覆盖父类属性，不做判断处理*/
	private String key = "alipay";
	private String ver = "1.0";
	private String id = "id";
	/*mac地址去除中间分隔符经过md5加密32位大写*/
	private String pyId = "pid";
	/*ios的id*/
	private String openudid = "openid";
	/*用户登录id*/
	private String uid = "uid";
	private String pt = "iphone";
	/*md5校验值，32位大写*/
	private String r = "28260C1909A306979BBA5F56F31742E9";
	private long t = System.currentTimeMillis();
	
	private String signature;
	
	private String timestamp;
	
	private String nonce;
	
	private String echostr;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

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

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public long getT() {
		return t;
	}

	public void setT(long t) {
		this.t = t;
	}
	
	
}
