package com.laipai.galaryManInfo.dto;

import java.sql.Timestamp;


/**
 * VLpMessageId entity. @author MyEclipse Persistence Tools
 */

public class VLpMessageBean  implements java.io.Serializable {
    // Fields    

     private String messageId;
     private Integer messageType;
     private Integer messageStatus;
     private String recieveUserId;
     private String fromUserId;
     private String fromUserName;
     private String messageContent;
     private String messageTime;
     private String galaryId;
     private String galaryCover;
     private String laipaiId;
     private String tile;
     
    /** default constructor */
    public VLpMessageBean() {
    }
    // Property accessors

    public String getMessageId() {
        return this.messageId;
    }
    
    public String getRecieveUserId() {
		return recieveUserId;
	}

	public void setRecieveUserId(String recieveUserId) {
		this.recieveUserId = recieveUserId;
	}

	public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageType() {
        return this.messageType;
    }
    
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageStatus() {
        return this.messageStatus;
    }
    
    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getFromUserId() {
        return this.fromUserId;
    }
    
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return this.fromUserName;
    }
    
    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessageContent() {
        return this.messageContent;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

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

    public String getLaipaiId() {
        return this.laipaiId;
    }
    
    public void setLaipaiId(String laipaiId) {
        this.laipaiId = laipaiId;
    }

    public String getTile() {
        return this.tile;
    }
    
    public void setTile(String tile) {
        this.tile = tile;
    }

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}

}