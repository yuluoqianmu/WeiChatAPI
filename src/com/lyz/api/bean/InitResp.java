package com.lyz.api.bean;

/**
 * 初始化接口响应结果
 * @author luzi
 *
 */
public class InitResp extends BaseResp{
	
	/*生成md5校验值所需要的原始字符串，该值需要和时间戳拼接进行md5加密，比如laipai143845686->3e7e3a0e0f7df1908cf6b312973a1acd*/
	private String key;
	/*新版本更新提示*/
	private String changes;
	/*升级的版本号*/
	private String nVer;
	/*0-不升级；1-建议升级；2-强制升级*/
	private int upType;
	/*升级的下载地址*/
	private String url;
	/*userName+pwd（md5）->md5*/
	private String token;
	/*是否登录*/
	private boolean isLogin;
	/*0-普通用户，1-摄影师*/
	private int userType;
	/*首页数据更新时间，以后做修改，客户端会根据改值判断是否更新首页数据*/
	private long upTime = System.currentTimeMillis();
	/*客户端是否需要开启分享功能，解决第三方sdk出问题，导致客户端崩溃的问题*/
	private boolean openShare = true;
	
	public InitResp(){
		
	}
	
	public InitResp(int result){
		this.setResult(result);
	}
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public String getnVer() {
		return nVer;
	}

	public void setnVer(String nVer) {
		this.nVer = nVer;
	}

	public int getUpType() {
		return upType;
	}

	public void setUpType(int upType) {
		this.upType = upType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public long getUpTime() {
		return upTime;
	}

	public void setUpTime(long upTime) {
		this.upTime = upTime;
	}

	public boolean isOpenShare() {
		return openShare;
	}

	public void setOpenShare(boolean openShare) {
		this.openShare = openShare;
	}

	
	
}
