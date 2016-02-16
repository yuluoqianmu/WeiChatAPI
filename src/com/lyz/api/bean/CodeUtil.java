package com.lyz.api.bean;
/**
 * 返回状态码
 * @author luyongzhao
 *
 */
public class CodeUtil {
	/*成功码*/
	public static int SUCCESS = 0;
	
	public static int SERVER_ERROR = 2;
	
	public static int CLIENT_ERROR = 1;
	
	public static int INVALID_UA = 401;
	/*错误的视图id*/
	public static int INVALID_VIEW_ID = 402;
	/*错误的分组名称*/
	public static int INVLID_GROUP_NAME = 403;
	/*错误的操作码类型*/
	public static int INVALID_OPER_TYPE = 404;
	/*错误的标签*/
	public static int INVALID_LABELS = 405;
	
	public static int INVALID_PAGE_SIZE = 406;
	
	public static int INVLIAD_PAGE_NUM = 407;
	/*card规则key为空*/
	public static int INVLIAD_CARD_RULE_KEY = 408;
	/*详情的sdk参数为空*/
	public static int INVLIAD_SINGLE_DATA_KEY = 409;
	/*客户端错误码*/
//	public static int INVALID_KEY = 4xx;	
	/*服务端错误码*/
//	public static int SERVER_ERROR = 5xx;
	
}
