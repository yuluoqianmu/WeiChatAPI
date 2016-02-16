package com.laipai.app.common;

public class Global {

	public static final String DATA_ENCODE = "utf-8";
	public static final boolean DEBUG = true;

	public static final int PAGE_SIZE = 16;
	/** mobile请求分页单页数据量 **/
	public static final int MOBILE_PAGE_SIZE = 200;

	/** 2分钟活动过的用户视为在线 **/
	public static final long USER_ONLINE_TIME = 2 * 60 * 1000;

	/** 用户默认组ID **/
	public static final int USER_DEFAULT_GROUP_NAME_FRIEND_TYPE = 1;
	public static final int USER_DEFAULT_GROUP_NAME_HOT_TYPE = 2;

	/** 类型.0.用户,1.会诊室 **/
	public static final int FRIEND_ADD_SRC_USERE = 0;
	public static final int FRIEND_ADD_SRC_HOUSE = 1;

	/** 医生类型 **/
	public static final int USER_DOCTOR_TYPE = 1;
	/** 用户类型 **/
	public static final int USER_USER_TYPE = 0;

	/** 用户子类型：0.监护人 **/
	public static final int USER_SUB_TYPE_DEFAULT = 0;
	/** 用户子类型：1.child **/
	public static final int USER_SUB_TYPE_CHILD = 1;
	/** 用户子类型：2.孕妇 **/
	public static final int USER_SUB_TYPE_MOTHER = 2;

	// 医生工作时间
	/** 非工作时间 **/
	public static final int DOCTOR_WORK_TYPE_SLEEP = 0;
	/** 收费工作时间 **/
	public static final int DOCTOR_WORK_TYPE_WORK = 0;
	/** 爱心工作时间 **/
	public static final int DOCTOR_WORK_TYPE_FREE = 0;

	/** os类型 **/
	public static final int OS_TYPE_IOS = 1;
	public static final int OS_TYPE_ANDROID = 0;

	/** 用户用的app类型 user **/
	public static final int APP_TYPE_USER = 0;
	/** 用户用的app类型 doctor **/
	public static final int APP_TYPE_DOCTOR = 1;

	/** 子账号接收消息 **/
	public static final int SUB_USER_REQUEST_MESSAGE_YES = 0;
	/** 子账号不接收消息 **/
	public static final int SUB_USER_REQUEST_MESSAGE_NO = 1;

	public static final String SESSION_ADMIN_USER_LOGIN_KEY = "admin_user";

	// 发消息的用户来源:0.用户,1.群组,2.会诊室
	public static final int MESSAGE_USER_TYPE_USER = 0;
	public static final int MESSAGE_USER_TYPE_GROUP = 1;
	public static final int MESSAGE_USER_TYPE_HOUSE = 2;

	// 消息类型,0.好友,1.消息,2.请求添加对方为好友后对方处理了好友请求收到的推送
	public static final int MESSAGE_TYPE_FRIEND = 0;
	public static final int MESSAGE_TYPE_MESSAGE = 1;
	public static final int MESSAGE_TYPE_FRIEND_PROCESS = 2;

	/** key vlaue主键 **/
	/** 医生职称主键 **/
	public static final int KEY_VALUE_DOCTOR_BUSINESS = 12;
	// 文件上传根目录
	public static final String UPLOAD_FILE_ROOT_PATH = "/upload/";
	// 版本文件下载地址
	public static final String UPLOAD_FILE_ROOT_PATH_APP_VERSION = "version/";
	// 病例图片存放
	public static final String UPLOAD_FILE_ROOT_PATH_DISEASE_HISTORY = "disease_history/";
	// 用户聊天消息上传目录
	public static final String UPLOAD_FILE_MESSAGE_PATH = "msg/";
	// 审核信息上传目录
	public static final String UPLOAD_FILE_ROOT_PATH_DOCTOR_VALIDATE = "doctor_validate/";
	// 审核信息上传目录 UPLOAD_FILE_ROOT_PATH+UPLOAD_FILE_ROOT_PATH_USER_HISTORY
	public static final String UPLOAD_FILE_ROOT_PATH_USER_HISTORY = "user_history/";
	// 头像上传目录
	public static final String UPLOAD_FILE_ROOT_PATH_PHOTO = "user_photo/";
	// 上传文件个数
	public static final int UPLOAD_FILE_MAX = 5;
	// 菜单icon上传根目录
	public static final String UPLOAD_MENU_ICON_ROOT_PATH = "/upload/menu_icon/";
	// 用户上传目录

	// key value 的一级key name
	/** 权限例表上级KEY **/
	public static final int KEY_VALUE_ROLE = 1;
	/** 随访项目类型KEY **/
	public static final int KEY_VALUE_TEMPLATE_PROJECT = 110;
	/** 随访项目适用人群KEY **/
	public static final int KEY_VALUE_TEMPLATE_FITOBJECT = 140;
}
