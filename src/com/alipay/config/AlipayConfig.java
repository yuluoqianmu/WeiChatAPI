package com.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088812005656434";
	
	public static final String seller = "developer@ilaipai.com";

	// 商户的私钥
	public static String private_key = "MIICXAIBAAKBgQClGTnVPcSWrrGVxPTE080Djsq+WU9he9QrTngijsr0hOLrAt5+L7iHZP2POiYYm0YsWRCEiOOHHrsm+nN6rNzyE0zvGvsU6qY4YoLM/agZE1gcTBH/yNOLUmSmX2m4+d585ZMSNvMyoPNRahWFhiCz5ZhTByLahEZweo8jduTqcQIDAQABAoGAUXJ3aG53bEq0SwmGIw/F5QptlKgklBftnIuykoHVN/nSpFOfBatvIkQ9Hop2Ps9jN/+cOXX6oS8U7u7BR3S97ld1D+dc1H4GP3hMOHLjwHHTicVs43zsqK2jQRSkdgGBxfUVw9NqcMVyOdRAUj/8TYZ2rqVlSxq0prDW7mX6rNECQQDWoysYOAirA2eQCq7JJtEsC4Ubi2GDkcvE6s1zZs++XmHw+RKIkk/rTTw/J5mA5H59jiX4SyIcwmG4lwQKPOdVAkEAxOohB3uy8w3hW9W2P132qMUllLtorKZyIpMDZ0CUejSgVkH1SISYP6lFgP926MwDV5WUxsnYB3yy1y1IcN4+rQJANTThm/FYBLylUYm0ZlzfAWIhrwBQPgNIVho2r+LOoMxYzxmPRJewhA3zX1x7qv35dTplozVM0YBGJTIaaEdMuQJABPnFi+LcSrf3EdL8n+1H5kvU/0UdB//MsoL0ew9usQfTZVqVUBfJoRIt52yNHKTLENN+xucixk2oVzeVtQRooQJBAK0f5Z0on7pmVonRH/e7ERDMNc80GaXy7jRj5ppFrsas+X/tsqEkECkbho8dUoV0ekNb0k+PB4s9I7TgohWQL3w=";
	//客户端使用的私钥
	public static String private_key_client = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKUZOdU9xJausZXE9MTTzQOOyr5ZT2F71CtOeCKOyvSE4usC3n4vuIdk/Y86JhibRixZEISI44ceuyb6c3qs3PITTO8a+xTqpjhigsz9qBkTWBxMEf/I04tSZKZfabj53nzlkxI28zKg81FqFYWGILPlmFMHItqERnB6jyN25OpxAgMBAAECgYBRcndobndsSrRLCYYjD8XlCm2UqCSUF+2ci7KSgdU3+dKkU58Fq28iRD0einY+z2M3/5w5dfqhLxTu7sFHdL3uV3UP51zUfgY/eEw4cuPAcdOJxWzjfOyoraNBFKR2AYHF9RXD02pwxXI51EBSP/xNhnaupWVLGrSmsNbuZfqs0QJBANajKxg4CKsDZ5AKrskm0SwLhRuLYYORy8TqzXNmz75eYfD5EoiST+tNPD8nmYDkfn2OJfhLIhzCYbiXBAo851UCQQDE6iEHe7LzDeFb1bY/XfaoxSWUu2ispnIikwNnQJR6NKBWQfVIhJg/qUWA/3bozANXlZTGydgHfLLXLUhw3j6tAkA1NOGb8VgEvKVRibRmXN8BYiGvAFA+A0hWGjav4s6gzFjPGY9El7CEDfNfXHuq/fl1OmWjNUzRgEYlMhpoR0y5AkAE+cWL4txKt/cR0vyf7UfmS9T/RR0H/8yygvR7D26xB9NlWpVQF8mhEi3nbI0cpMsQ037G5yLGTahXN5W1BGihAkEArR/lnSifumZWidEf97sREMw1zzQZpfLuNGPmmkWuxqz5f+2yoSQQKRuGjx1ShXR6Q1vST48Hiz0jtOCiFZAvfA==";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/mnt/logs/alipay";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	/*支付宝地址*/
	public static String callBackUrl = "http://api.ilaipai.com/LaiPai/alipay";

}
