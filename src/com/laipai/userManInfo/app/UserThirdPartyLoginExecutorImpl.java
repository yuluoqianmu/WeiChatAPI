  package com.laipai.userManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;
import com.laipai.util.EmojiFilter;

import net.sf.json.JSONObject;

/**
 * 用户第三方登录
 * 
 * @author zhh
 * 
 */
@NotLogin
@Service("UserThirdPartyLoginExecutorImpl")
public class UserThirdPartyLoginExecutorImpl implements RequestExecutor {
	
	private static final Logger logger = Logger.getLogger(UserThirdPartyLoginExecutorImpl.class);

	@Autowired
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Autowired
	private IBaseDao baseDao;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userService;
	public IUserManInfoService getUserService() {
		return userService;
	}
	public void setUserService(IUserManInfoService userService) {
		this.userService = userService;
	}
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		//获取服务器域名
		String serverName = json.getString("serverName");
		String headImage = JSONTools.getString(json, "headImage");
		String userName = JSONTools.getString(json, "userName"); //用户名即手机号(用户使用手机号注册，手机号即为userName)
		String nickName = JSONTools.getString(json, "nickName");
		String token = JSONTools.getString(json, "token"); // 用户手机设备token号
		String accountSource = JSONTools.getString(json, "accountSource");  //账号来源0：注册；1：微博；2：微信
		
		if (userName == null) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		String password = "111111"; //三方登录初始密码都设为111111
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		LpUser user = null;
		//根据手机号查询用户(用户登录时必须填手机号,密码)
		List<LpUser> userList = baseDao.queryListObjectByTopNum("from LpUser u where u.userName='"+userName+"'" , 1);
		if(userList!=null && !userList.isEmpty()){
			user = userList.get(0);
			user.setNickName(EmojiFilter.filterEmoji(nickName));
			user.setHeadImage(headImage);
			Timestamp time = DateUtils.dateToTimestamp(new Date());
			user.setLastActivityTime(time);
			baseDao.updateObject(user);
		}else{
			user = new LpUser();
			user.setUserStatus(0);
			user.setUserName(userName);
			user.setNickName(EmojiFilter.filterEmoji(nickName));
			user.setUserType(0);
			user.setHeadImage(headImage);
			user.setAccountSource(Integer.parseInt(accountSource));
			Timestamp time = DateUtils.dateToTimestamp(new Date());
			user.setLastActivityTime(time);
			user.setRegisterTime(time);
			baseDao.save(user);
		}
		
		login(user,token);
		JSONObject jsonUser = userToJSON(user,serverName);
		return jsonUser;
	}
	
	/**
	 * 保存图片到磁盘
	 * sb : 保存用户头像图片
	 * parameters : 临时保存的参数,从中可以取出文件对象
	 * */
//	private void uploadImgFile(StringBuffer sb,BaseRequestParameters parameters) {
//		//作品集上传目录
//		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG + LpUserBean.LP_USER_IMGURL;
//		//文件列表
//		Set<Map.Entry<String, FileItem>> set = parameters.getFileMap().entrySet();
//		
//		if(set!=null && set.size() > 0){
//			File files = new File(savePath);
//			if (!files.exists() || !files.isFile()) {
//				System.out.println("--------创建用户头像保存路径成功:"+savePath);
//				files.mkdir();
//			}
//			try {
//				for (Map.Entry<String, FileItem> f : set) {
//					String filename = f.getKey();
//					int index = filename.lastIndexOf(".");  //扩展名的位置
//					String fileType = "";
//		            if(index != -1){
//		            	fileType = filename.substring(index); //截取扩展名,获得文件类型,如:jpg,jpeg,png,gif
//		            }
//					InputStream in = f.getValue().getInputStream();
//					//为防止重名，需要对文件重命名，命名规则：userHeadImage_随机字符.扩展名
//					String realFileName = "userHeadImage" + "_" + UUID.randomUUID() + fileType;   
//					sb.append(savePath +"/"+ realFileName);
//					//输出文件到磁盘
//					Tools.writeFile(savePath +"/"+ realFileName, in);
//				}
//			}catch (IOException e1) {
//				e1.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * 修改用户登录信息
	 * user : 登录的用户对象
	 * device : 用户登录使用的设备(Android,iPhone,ipad,Android Pad)
	 * */
	private void login(LpUser user,String token) {
		user.setLastActivityTime(DateUtils.dateToTimestamp(new Date())); //用户最后一次活动时间
		user.setLoginStatus(0); //登录状态: 0已登录  1未登录
		user.setLastMobileToken(token);
		baseDao.updateObject(user);
	}
	
	private JSONObject userToJSON(LpUser user,String serverName){
		JSONObject json = new JSONObject();
		json.put("userId", user.getUserId());
		json.put("userName", user.getUserName());
		json.put("userPassword", user.getUserPassword());
		json.put("mobile", user.getMobile());
		json.put("userRemark", user.getUserRemark());
		json.put("userType", user.getUserType());
		json.put("userStatus", user.getUserStatus());
		if(StringUtils.isNotEmpty(user.getHeadImage())){

		  json.put("headImage", user.getHeadImage());
		
		}
		json.put("nickName",user.getNickName());
		

		return json;
	}
	
	/**
	* 将字符串转为图片
	* @param imgStr
	* @return
	*/
	private static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
		return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgFile;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		String password ="laipai2015server";
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		System.out.println(password);
		
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
