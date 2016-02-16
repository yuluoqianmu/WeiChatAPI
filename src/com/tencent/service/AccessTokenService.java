package com.tencent.service;

import com.google.gson.Gson;
import com.tencent.common.Configure;

/**
 * 微信开放api:获取accessToken服务
 * @author luyongzhao
 *
 */
public class AccessTokenService extends BaseService {
	
	private static final Gson gson = new Gson();

	public AccessTokenService() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		super(Configure.ACCESS_TOKEN);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param appId
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public Token request(String appId, String secret)throws Exception{
		
		String url = Configure.ACCESS_TOKEN+"?grant_type=client_credential&"+"appid="+appId+"&secret="+secret;
		
		return gson.fromJson(this.sendGet(url), Token.class);
	}
	
	public static class Token{
		
		private String access_token;
		
		private int expires_in;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public int getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		
		
	}

}
