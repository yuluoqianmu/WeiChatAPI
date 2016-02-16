package com.lyz.api.bean;
/**
 * 关联实体
 * 用于获取一个实体类所属的标签实体信息
 * 例如，作品集的一个关联是摄影师，显示作品集列表时，需要同时展示其所属的摄影师的相关信息
 * @author luzi
 *
 */
public class RelationEntity {
	
	private String key;
	
	private String groupName;
	
	public RelationEntity(String key, String groupName){
		
		this.key = key;
		this.groupName = groupName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
