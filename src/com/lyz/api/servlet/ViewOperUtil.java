package com.lyz.api.servlet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.lyz.api.entity.Galary;
import com.lyz.labelData.bean.BaseData;
import com.lyz.labelData.oper.ICardOper;
import com.lyz.labelData.oper.IViewOper;
import com.lyz.labelData.oper.impl.DefaultCardOperImpl;
import com.lyz.labelData.oper.impl.DefaultViewOperImpl;

public class ViewOperUtil {
	
	public static AtomicReference<ViewOperUtil> instance = new AtomicReference<ViewOperUtil>();
	
	private IViewOper viewOper = null;
	
	private ViewOperUtil(){
		
		/*初始化视图处理类*/
		Map<String,ICardOper<? extends BaseData>> groupName2CardOper = new HashMap<String, ICardOper<? extends BaseData>>();		
		/*加入所有card处理类*/
		groupName2CardOper.put("galary", new DefaultCardOperImpl<Galary>("galary"));
		
		/*实例化视图操作类。tip：命名为view的视图组中可以包含多个视图*/
		viewOper = new DefaultViewOperImpl("view", groupName2CardOper);
	}
	
	public static ViewOperUtil getInstance(){
		
		if(instance == null){
			
			instance.compareAndSet(null, new ViewOperUtil());
		}
		
		return instance.get();
	}
	
	
	public IViewOper getViewOper(){
		
		return this.viewOper;
	}
}
