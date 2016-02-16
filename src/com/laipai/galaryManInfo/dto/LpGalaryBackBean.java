package com.laipai.galaryManInfo.dto;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpGalary entity. @author MyEclipse Persistence Tools
 */
public class LpGalaryBackBean implements java.io.Serializable,Cloneable {
    
	
	
	
	// Fields
    private String subjectName;
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	private String galaryId;
	private LpUser lpUser;
	private LpService lpService;
	private String galaryDesc;
	private String galaryCover;
	private Timestamp creatTime;
	private Integer controlSource;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private Integer galaryStatus;

	private double galaryScores;

	private Integer status;


	private Timestamp modifyTime;
	private String modifyName;
    private LpGalaryExtend lpGalaryExtends;
    private Integer orderNumber;
    private Integer controlIndex;
    private Integer galaryIndex;

	public Integer getGalaryIndex() {
		return galaryIndex;
	}

	public void setGalaryIndex(Integer galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public Integer getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(Integer controlIndex) {
		this.controlIndex = controlIndex;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LpGalaryExtend getLpGalaryExtends() {
		return lpGalaryExtends;
	}

	public void setLpGalaryExtends(LpGalaryExtend lpGalaryExtends) {
		this.lpGalaryExtends = lpGalaryExtends;
	}



	private Set lpGalaryDetails = new HashSet(0);
	private Set lpComments = new HashSet(0);
	private Set lpLikes = new HashSet(0);
	private Set<LpStyle> lpStyle=new HashSet<LpStyle>();
	public Set<LpStyle> getLpStyle() {
		return lpStyle;
	}

	public void setLpStyle(Set<LpStyle> lpStyle) {
		this.lpStyle = lpStyle;
	}
	/** 
	 * @Fields set : 与专题是对对多的关系
	 */  
	private Set<Subject> set = new HashSet<Subject>();
	// Constructors

	
	// Property accessors

	public Set<Subject> getSet() {
		return set;
	}

	public void setSet(Set<Subject> set) {
		this.set = set;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public LpService getLpService() {
		return this.lpService;
	}

	public void setLpService(LpService lpService) {
		this.lpService = lpService;
	}

	public String getGalaryDesc() {
		return this.galaryDesc;
	}

	public void setGalaryDesc(String galaryDesc) {
		this.galaryDesc = galaryDesc;
	}

	public String getGalaryCover() {
		return this.galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public Timestamp getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getControlSource() {
		return controlSource;
	}

	public void setControlSource(Integer controlSource) {
		this.controlSource = controlSource;
	}

	public Integer getViewNumber() {
		return this.viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getLikeNumber() {
		return this.likeNumber;
	}

	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getGalaryStatus() {
		return this.galaryStatus;
	}

	public void setGalaryStatus(Integer galaryStatus) {
		this.galaryStatus = galaryStatus;
	}





	public double getGalaryScores() {
		return galaryScores;
	}

	public void setGalaryScores(double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyName() {
		return this.modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set getLpGalaryDetails() {
		return this.lpGalaryDetails;
	}

	public void setLpGalaryDetails(Set lpGalaryDetails) {
		this.lpGalaryDetails = lpGalaryDetails;
	}

	public Set getLpComments() {
		return this.lpComments;
	}

	public void setLpComments(Set lpComments) {
		this.lpComments = lpComments;
	}

	public Set getLpLikes() {
		return this.lpLikes;
	}

	public void setLpLikes(Set lpLikes) {
		this.lpLikes = lpLikes;
	}
	// Fields
				private File  imgsFile[];
				//文件类型
				private String  imgsFileContentType[];
				//文件名
				private String  imgsFileFileName[];

				public File[] getImgsFile() {
					return imgsFile;
				}



				public void setImgsFile(File[] imgsFile) {
					this.imgsFile = imgsFile;
				}



				public String[] getImgsFileContentType() {
					return imgsFileContentType;
				}



				public void setImgsFileContentType(String[] imgsFileContentType) {
					this.imgsFileContentType = imgsFileContentType;
				}



				public String[] getImgsFileFileName() {
					return imgsFileFileName;
				}



				public void setImgsFileFileName(String[] imgsFileFileName) {
					this.imgsFileFileName = imgsFileFileName;
				}

				
				
				
	@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;
					result = prime
							* result
							+ ((commentNumber == null) ? 0 : commentNumber
									.hashCode());
					result = prime
							* result
							+ ((controlIndex == null) ? 0 : controlIndex
									.hashCode());
					result = prime
							* result
							+ ((controlSource == null) ? 0 : controlSource
									.hashCode());
					result = prime * result
							+ ((creatTime == null) ? 0 : creatTime.hashCode());
					result = prime
							* result
							+ ((galaryCover == null) ? 0 : galaryCover
									.hashCode());
					result = prime
							* result
							+ ((galaryDesc == null) ? 0 : galaryDesc.hashCode());
					result = prime * result
							+ ((galaryId == null) ? 0 : galaryId.hashCode());
					result = prime
							* result
							+ ((galaryIndex == null) ? 0 : galaryIndex
									.hashCode());
					long temp;
					temp = Double.doubleToLongBits(galaryScores);
					result = prime * result + (int) (temp ^ (temp >>> 32));
					result = prime
							* result
							+ ((galaryStatus == null) ? 0 : galaryStatus
									.hashCode());
					result = prime * result + Arrays.hashCode(imgsFile);
					result = prime * result
							+ Arrays.hashCode(imgsFileContentType);
					result = prime * result + Arrays.hashCode(imgsFileFileName);
					result = prime
							* result
							+ ((likeNumber == null) ? 0 : likeNumber.hashCode());
					result = prime
							* result
							+ ((lpComments == null) ? 0 : lpComments.hashCode());
					result = prime
							* result
							+ ((lpGalaryDetails == null) ? 0 : lpGalaryDetails
									.hashCode());
					result = prime
							* result
							+ ((lpGalaryExtends == null) ? 0 : lpGalaryExtends
									.hashCode());
					result = prime * result
							+ ((lpLikes == null) ? 0 : lpLikes.hashCode());
					result = prime * result
							+ ((lpService == null) ? 0 : lpService.hashCode());
					result = prime * result
							+ ((lpStyle == null) ? 0 : lpStyle.hashCode());
					result = prime * result
							+ ((lpUser == null) ? 0 : lpUser.hashCode());
					result = prime
							* result
							+ ((modifyName == null) ? 0 : modifyName.hashCode());
					result = prime
							* result
							+ ((modifyTime == null) ? 0 : modifyTime.hashCode());
					result = prime
							* result
							+ ((orderNumber == null) ? 0 : orderNumber
									.hashCode());
					result = prime * result
							+ ((set == null) ? 0 : set.hashCode());
					result = prime * result
							+ ((status == null) ? 0 : status.hashCode());
					result = prime
							* result
							+ ((subjectName == null) ? 0 : subjectName
									.hashCode());
					result = prime
							* result
							+ ((viewNumber == null) ? 0 : viewNumber.hashCode());
					return result;
				}

				@Override
				public boolean equals(Object obj) {
					if (this == obj)
						return true;
					if (obj == null)
						return false;
					if (getClass() != obj.getClass())
						return false;
					LpGalaryBackBean other = (LpGalaryBackBean) obj;
					if (commentNumber == null) {
						if (other.commentNumber != null)
							return false;
					} else if (!commentNumber.equals(other.commentNumber))
						return false;
					if (controlIndex == null) {
						if (other.controlIndex != null)
							return false;
					} else if (!controlIndex.equals(other.controlIndex))
						return false;
					if (controlSource == null) {
						if (other.controlSource != null)
							return false;
					} else if (!controlSource.equals(other.controlSource))
						return false;
					if (creatTime == null) {
						if (other.creatTime != null)
							return false;
					} else if (!creatTime.equals(other.creatTime))
						return false;
					if (galaryCover == null) {
						if (other.galaryCover != null)
							return false;
					} else if (!galaryCover.equals(other.galaryCover))
						return false;
					if (galaryDesc == null) {
						if (other.galaryDesc != null)
							return false;
					} else if (!galaryDesc.equals(other.galaryDesc))
						return false;
					if (galaryId == null) {
						if (other.galaryId != null)
							return false;
					} else if (!galaryId.equals(other.galaryId))
						return false;
					if (galaryIndex == null) {
						if (other.galaryIndex != null)
							return false;
					} else if (!galaryIndex.equals(other.galaryIndex))
						return false;
					if (Double.doubleToLongBits(galaryScores) != Double
							.doubleToLongBits(other.galaryScores))
						return false;
					if (galaryStatus == null) {
						if (other.galaryStatus != null)
							return false;
					} else if (!galaryStatus.equals(other.galaryStatus))
						return false;
					if (likeNumber == null) {
						if (other.likeNumber != null)
							return false;
					} else if (!likeNumber.equals(other.likeNumber))
						return false;
					if (lpComments == null) {
						if (other.lpComments != null)
							return false;
					} else if (!lpComments.equals(other.lpComments))
						return false;
					if (lpGalaryDetails == null) {
						if (other.lpGalaryDetails != null)
							return false;
					} else if (!lpGalaryDetails.equals(other.lpGalaryDetails))
						return false;
					if (lpGalaryExtends == null) {
						if (other.lpGalaryExtends != null)
							return false;
					} else if (!lpGalaryExtends.equals(other.lpGalaryExtends))
						return false;
					if (lpLikes == null) {
						if (other.lpLikes != null)
							return false;
					} else if (!lpLikes.equals(other.lpLikes))
						return false;
					if (lpService == null) {
						if (other.lpService != null)
							return false;
					} else if (!lpService.equals(other.lpService))
						return false;
					if (lpStyle == null) {
						if (other.lpStyle != null)
							return false;
					} else if (!lpStyle.equals(other.lpStyle))
						return false;
					if (lpUser == null) {
						if (other.lpUser != null)
							return false;
					} else if (!lpUser.equals(other.lpUser))
						return false;
					if (modifyName == null) {
						if (other.modifyName != null)
							return false;
					} else if (!modifyName.equals(other.modifyName))
						return false;
					if (modifyTime == null) {
						if (other.modifyTime != null)
							return false;
					} else if (!modifyTime.equals(other.modifyTime))
						return false;
					if (orderNumber == null) {
						if (other.orderNumber != null)
							return false;
					} else if (!orderNumber.equals(other.orderNumber))
						return false;
					if (set == null) {
						if (other.set != null)
							return false;
					} else if (!set.equals(other.set))
						return false;
					if (status == null) {
						if (other.status != null)
							return false;
					} else if (!status.equals(other.status))
						return false;
					if (subjectName == null) {
						if (other.subjectName != null)
							return false;
					} else if (!subjectName.equals(other.subjectName))
						return false;
					if (viewNumber == null) {
						if (other.viewNumber != null)
							return false;
					} else if (!viewNumber.equals(other.viewNumber))
						return false;
					return true;
				}

	public Object clone(){ 
		LpGalaryBackBean o = null; 
		try{ 
		o = (LpGalaryBackBean)super.clone(); 
		}catch(CloneNotSupportedException e){ 
		e.printStackTrace(); 
		} 
		return o; 
		} 			
}