package com.tencent;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.lyz.api.cache.ICache;
import com.lyz.api.cache.OcsCache;
import com.lyz.util.MD5Generator;
import com.tencent.service.AccessTokenService;
import com.tencent.service.AccessTokenService.Token;
import com.tencent.service.ApiTicketService;
import com.tencent.service.ApiTicketService.Ticket;
/**
 * 生成微信开放api签名
 * @author luyongzhao
 *
 */
public class Sign {
	
	private static final Gson gson = new Gson();
		
	private static final Logger logger = Logger.getLogger(Sign.class);
	/*缓存*/
	private static final ICache cache = OcsCache.getInstance();
	
	public static String getTokenWithCache(String appId, String secret,String url){
		
		String accessTokenKey = "weixin_access_token_"+appId+"_"+MD5Generator.MD5(url);
		
		try {
			String strToken = cache.getData(accessTokenKey);
			/*缓存中不存在，则请求微信接口*/
			if(strToken == null){
				logger.info("to get access token from weixin interface...");
				Token token = new AccessTokenService().request(appId, secret);
				strToken = token.getAccess_token();
				if(strToken==null || "".equals(strToken.trim())){
					
					logger.error("fail to get accessToken:"+gson.toJson(token));
				}else{
					/*设定缓存时间为1小时*/
					cache.setData(accessTokenKey, strToken, token.getExpires_in()/2);
				}			
			}else{
				logger.info("get access token from cache...");
			}
						
			return strToken;
		} catch (Exception e) {
			logger.error("fail to get access token!",e);
			return null;
		} 
	}
	
	public static String getTicketWithCache(String appId, String accessToken, String url){
		
		String ticketKey = "weixin_ticket_appId_"+appId+"_"+MD5Generator.MD5(url);
		
		try {
			String strTicket = cache.getData(ticketKey);
			
			if(strTicket == null){
				
				logger.info("to get access ticket from weixin interface...");
				/*获取ticket*/
				Ticket ticket = new ApiTicketService().request(accessToken);
				strTicket = ticket.getTicket();
				if(strTicket==null || "".equals(strTicket.trim())){
					logger.error("fail to get ticket from weixin interface...");
				}else{
					cache.setData(ticketKey, strTicket, ticket.getExpires_in()/2);
				}
			}else{
				
				logger.info("get weixin ticket from cache...");
			}
			
			return strTicket;
		} catch (Exception e) {
			logger.error("fail to get weixin ticket!",e);
			return null;
		}
	}
	
	public static SignParam signWithCache(String appId, String secret, String url){
		
		
		String token = getTokenWithCache(appId, secret,url);
		
		String ticket = getTicketWithCache(appId, token,url);
		
		/*加密*/
		Map<String, String> ret = sign(ticket, url);
		
		/*返回组装对象*/
		return new SignParam(appId, ret.get("nonceStr"), ret.get("timestamp"), ret.get("signature"));
	}
	
	public static SignParam sign(String appId, String secret, String url){
		
		try {
			/*获取accessToken*/
			Token token = new AccessTokenService().request(appId, secret);
			logger.info(gson.toJson(token));
			/*获取ticket*/
			Ticket ticket = new ApiTicketService().request(token.getAccess_token());
			
			logger.info(gson.toJson(ticket));
			/*加密*/
			Map<String, String> ret = sign(ticket.getTicket(), url);
			
			/*返回组装对象*/
			return new SignParam(appId, ret.get("nonceStr"), ret.get("timestamp"), ret.get("signature"));
		} catch (Exception e) {
			
			logger.error("fail to get weixin api sign",e);
			return null;
		}
	}
	
    public static void main(String[] args) {
//        String jsapi_ticket = "E0o2-at6NcC2OsJiQTlwlGyWx0OMELM3HpuezFCtc0ks2ntv84DsFQFe-q235LD_nAEly6IjgxAJrL6PS53MOQ";
//
//        // 注意 URL 一定要动态获取，不能 hardcode
//        String url = "http://test.ilaipai.com/LaiPai/jsp/activity/ApplyClub.jsp";
//        Map<String, String> ret = sign(jsapi_ticket, url);
//        for (Map.Entry entry : ret.entrySet()) {
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
    	
//    	SignParam param = Sign.sign("wxab941c35de5d64d3", "a6bca72bac4a2d468ea5bcb838b6a4ba", "http://test.ilaipai.com/LaiPai/jsp/activity/ApplyClub.jsp?from=singlemessage&isappinstalled=0");
    	SignParam param = Sign.signWithCache("wxab941c35de5d64d3", "a6bca72bac4a2d468ea5bcb838b6a4ba", "http://test.ilaipai.com/LaiPai/jsp/activity/ApplyClub.jsp?from=singlemessage&isappinstalled=0");
    	
    	System.out.println("timestamp:"+param.getTimestamp());
    	System.out.println("nonceStr:"+param.getNonceStr());
    	System.out.println("signature:"+param.getSign());
    	
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
   
}
