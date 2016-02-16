package com.laipai.subject.pojo;

/**
 * VLpSubjectDetailAppId entity. @author MyEclipse Persistence Tools
 */

public class VLpSubjectDetailApp implements java.io.Serializable {
	// Fields

	private String galaryId;
	private String galaryCover;
	private Integer viewNumber;
	private String likeNumber;
	private Integer commentNumber;
	private String subjectName;
	private Integer status;
	private Integer indexControl;
	private Double galaryScores;
	private String subjectId;
	private Integer subjectGalaryLocation;
	private String cameramanId;
	/*兼容ios的1.0版本，同cameramanId*/
	private String userId;
	private String nickName;
	private String headImage;
	private Integer userType;
	private Long price;
	private Integer isLike;
    private String styleName;
    private String cityName;
	// Constructors

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	/** default constructor */
	public VLpSubjectDetailApp() {
	}
	// Property accessors

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getGalaryCover() {
		return this.galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public Integer getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getLikeNumber() {
		return this.likeNumber;
	}

	public void setLikeNumber(String likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIndexControl() {
		return this.indexControl;
	}

	public void setIndexControl(Integer indexControl) {
		this.indexControl = indexControl;
	}

	public Double getGalaryScores() {
		return this.galaryScores;
	}

	public void setGalaryScores(Double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSubjectGalaryLocation() {
		return this.subjectGalaryLocation;
	}

	public void setSubjectGalaryLocation(Integer subjectGalaryLocation) {
		this.subjectGalaryLocation = subjectGalaryLocation;
	}

	public String getCameramanId() {
		return this.cameramanId;
	}

	public void setCameramanId(String cameramanId) {
		this.cameramanId = cameramanId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getIsLike() {
		return isLike;
	}
	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	

}