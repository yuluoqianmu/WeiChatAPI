package com.laipai.app.process.app;

import com.laipai.app.process.app.dataobj.BaseRequestParameters;


/**
 * 执行实现,根据请求返回对应结果
 * 
 * @author ts
 * @date 2014-11-13
 */
public interface RequestExecutor {

	/**
	 * json内容
	 * 
	 * @param json
	 * @param requestObjects
	 *            request对像
	 * @return json array/object<br>
	 *         java object,java object会自动转成jsonObject/array<br>
	 *         当返回json object的key中有 result 时，则认为是标准返回，会直接返回给client，不会有其它处理
	 */
	public Object execute(BaseRequestParameters parameters, Object... requestObjects);
	/**
	 * 获取缓存的key，不需要缓存的数据，直接返回null
	 * @param parameters
	 * @param requestObjects
	 * @return
	 */
	public String getCacheKey(BaseRequestParameters parameters, Object... requestObjects);

}
