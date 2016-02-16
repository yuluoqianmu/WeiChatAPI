package com.tencent.service;

import com.tencent.common.Configure;
import com.tencent.protocol.pre_pay_protocol.PrePayReqData;
/**
 * 下单请求，获取
 * @author luyongzhao
 *
 */
public class PrePayService extends BaseService {

	public PrePayService() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		super(Configure.PRE_PAY_API);
		// TODO Auto-generated constructor stub
	}
	
	public String request(PrePayReqData data) throws Exception {
		
		//--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(data);

        return responseString;
	}

}
