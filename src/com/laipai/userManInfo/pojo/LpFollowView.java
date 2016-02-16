package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;


/**
 * LpFollowViewId entity. @author MyEclipse Persistence Tools
 */

public class LpFollowView  implements java.io.Serializable {


    // Fields    

     private String followId;
     private Timestamp followTime;
     private String userId;
     private String userName;
     private String userNickName;
     private Integer userType;
     private String userHeadImage;
     private String cameraId;
     private String cameraName;
     private String cameraNickName;
     private Integer cameraType;
     private String cameraHeadImage;


    // Constructors

    public String getUserHeadImage() {
		return userHeadImage;
	}

	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}


	public String getCameraHeadImage() {
		return cameraHeadImage;
	}

	public void setCameraHeadImage(String cameraHeadImage) {
		this.cameraHeadImage = cameraHeadImage;
	}

	public Integer getCameraType() {
		return cameraType;
	}

	public void setCameraType(Integer cameraType) {
		this.cameraType = cameraType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getCameraNickName() {
		return cameraNickName;
	}

	public void setCameraNickName(String cameraNickName) {
		this.cameraNickName = cameraNickName;
	}

	/** default constructor */
    public LpFollowView() {
    }

	/** minimal constructor */
    public LpFollowView(String followId, String userId, String cameraId) {
        this.followId = followId;
        this.userId = userId;
        this.cameraId = cameraId;
    }
    
    /** full constructor */
    public LpFollowView(String followId, Timestamp followTime, String userId, String userName, String cameraId, String cameraName) {
        this.followId = followId;
        this.followTime = followTime;
        this.userId = userId;
        this.userName = userName;
        this.cameraId = cameraId;
        this.cameraName = cameraName;
    }

   
    // Property accessors

    public String getFollowId() {
        return this.followId;
    }
    
    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public Timestamp getFollowTime() {
        return this.followTime;
    }
    
    public void setFollowTime(Timestamp followTime) {
        this.followTime = followTime;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCameraId() {
        return this.cameraId;
    }
    
    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return this.cameraName;
    }
    
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LpFollowView) ) return false;
		 LpFollowView castOther = ( LpFollowView ) other; 
         
		 return ( (this.getFollowId()==castOther.getFollowId()) || ( this.getFollowId()!=null && castOther.getFollowId()!=null && this.getFollowId().equals(castOther.getFollowId()) ) )
 && ( (this.getFollowTime()==castOther.getFollowTime()) || ( this.getFollowTime()!=null && castOther.getFollowTime()!=null && this.getFollowTime().equals(castOther.getFollowTime()) ) )
 && ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getUserName()==castOther.getUserName()) || ( this.getUserName()!=null && castOther.getUserName()!=null && this.getUserName().equals(castOther.getUserName()) ) )
 && ( (this.getCameraId()==castOther.getCameraId()) || ( this.getCameraId()!=null && castOther.getCameraId()!=null && this.getCameraId().equals(castOther.getCameraId()) ) )
 && ( (this.getCameraName()==castOther.getCameraName()) || ( this.getCameraName()!=null && castOther.getCameraName()!=null && this.getCameraName().equals(castOther.getCameraName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFollowId() == null ? 0 : this.getFollowId().hashCode() );
         result = 37 * result + ( getFollowTime() == null ? 0 : this.getFollowTime().hashCode() );
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getUserName() == null ? 0 : this.getUserName().hashCode() );
         result = 37 * result + ( getCameraId() == null ? 0 : this.getCameraId().hashCode() );
         result = 37 * result + ( getCameraName() == null ? 0 : this.getCameraName().hashCode() );
         return result;
   }   





}