package com.lyz.service;

import java.util.ArrayList;
import java.util.List;

import com.lyz.api.bean.ShowStyleListResp.Style;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpStyle;
import com.lyz.db.dao.BaseDaoImpl;

/**
 * 拍摄风格
 * @author luyongzhao
 *
 */
public class StyleService {
	
	private static final IBaseDao<LpStyle> styleDao = new BaseDaoImpl<LpStyle>();
	

	/**
	 * 获取拍摄风格列表
	 * @return
	 */
	public static List<Style> getStyleList(){
		
		String sql = "select style_id,style_name from lp_style where is_online=1 and is_true_delete=1";
		
		List<LpStyle> styleList = styleDao.queryObjects(sql, LpStyle.class);
		
		if(styleList == null){
			return new ArrayList<Style>();
		}
		
		List<Style> list = new ArrayList<Style>();
		for(LpStyle s : styleList){
			list.add(new Style(s.getStyleId(),s.getStyleName()));
		}
		
		return list;
	}
}
