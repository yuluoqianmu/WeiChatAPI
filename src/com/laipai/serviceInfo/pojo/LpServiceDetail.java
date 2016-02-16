package com.laipai.serviceInfo.pojo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.operationManage.pojo.LpStyle;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * LpServiceDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpServiceDetail implements java.io.Serializable {

	// Fields

	private String detailId;
	//private LpStyle lpStyle;
	//private Set<LpStyle> lpStyle=new HashSet<LpStyle>();
	private List<LpStyle> lpStyle=new ArrayList<LpStyle>();
	private LpService lpService;
	private Long price;
	private String priceUnit;
	private String shootTime;
	private Integer pictureNum;
	private Integer truingNum;
	private String clothes;
	private String facepaint;
	private String instructions;

	// Constructors

	/** default constructor */
	public LpServiceDetail() {
		
		this.detailId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeServiceDetail)+"";
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	/** full constructor */
//	public LpServiceDetail(LpStyle lpStyle, LpService lpService, Long price,
//			Integer shootTime, Integer pictureNum, Integer truingNum,
//			String clothes, String facepaint, String instructions) {
//		this.lpStyle = lpStyle;
//		this.lpService = lpService;
//		this.price = price;
//		this.shootTime = shootTime;
//		this.pictureNum = pictureNum;
//		this.truingNum = truingNum;
//		this.clothes = clothes;
//		this.facepaint = facepaint;
//		this.instructions = instructions;
//	}

	// Property accessors

	public String getDetailId() {
		return this.detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

//	
//	public Set<LpStyle> getLpStyle() {
//		return lpStyle;
//	}
//
//	public void setLpStyle(Set<LpStyle> lpStyle) {
//		this.lpStyle = lpStyle;
//	}

	public LpService getLpService() {
		return this.lpService;
	}

	public List<LpStyle> getLpStyle() {
		return lpStyle;
	}

	public void setLpStyle(List<LpStyle> lpStyle) {
		this.lpStyle = lpStyle;
	}

	public void setLpService(LpService lpService) {
		this.lpService = lpService;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}


	public String getShootTime() {
		return shootTime;
	}

	public void setShootTime(String shootTime) {
		this.shootTime = shootTime;
	}

	public Integer getPictureNum() {
		return this.pictureNum;
	}

	public void setPictureNum(Integer pictureNum) {
		this.pictureNum = pictureNum;
	}

	public Integer getTruingNum() {
		return this.truingNum;
	}

	public void setTruingNum(Integer truingNum) {
		this.truingNum = truingNum;
	}

	public String getClothes() {
		return this.clothes;
	}

	public void setClothes(String clothes) {
		this.clothes = clothes;
	}

	public String getFacepaint() {
		return this.facepaint;
	}

	public void setFacepaint(String facepaint) {
		this.facepaint = facepaint;
	}

	public String getInstructions() {
		return this.instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

}