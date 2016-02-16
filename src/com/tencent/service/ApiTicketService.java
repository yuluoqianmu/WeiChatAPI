package com.tencent.service;

import com.google.gson.Gson;
import com.tencent.common.Configure;

/**
 * 微信API：获取ticket
 * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=BW-ne-4wUNg2oEhnMtwYOMf0jEi6vUo3Tkg0MDZdgRxZfdKWb5TP7CrGbMR_zK6c-GK5uzFfbjcNNvIqpGIWj6tgLomB4OcyjIzTppZnnsI&type=wx_card
 * @author luyongzhao
 *
 */
public class ApiTicketService extends BaseService {
	
	private static final Gson gson = new Gson();

	public ApiTicketService() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		super(Configure.API_TICKET);
		// TODO Auto-generated constructor stub
	}
	
	public Ticket request(String accessToken)throws Exception{
		
		String url = Configure.API_TICKET+"?access_token="+accessToken+"&type=jsapi";
		
		System.out.println(url);
		
		return gson.fromJson(this.sendGet(url), Ticket.class);
	}
	
	public static class Ticket{
		
		private int errcode;
		
		private String errmsg;
		
		private String ticket;
		
		private int expires_in;

		public int getErrcode() {
			return errcode;
		}

		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public int getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
		
		
	}

}
