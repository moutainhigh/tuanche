package com.taisf.services.message.dto;

import com.jk.framework.base.page.PageRequest;

import java.util.Date;

/**
 * @author zhangzhengguang
 * @create 2017-09-07
 **/
public class MessageRequest extends PageRequest {

    private Integer id;

    /**
     * 主题: 健康资讯,促销活动,系统通知
     */
    private String subject;
    private String title;

    /**
     * 发送方式 1.站内信,2.推送,3.短信
     */
    private Integer sendType;
    /**
     * 发送时间
     */
    private String startTime;

    private String endTime;

    private Integer platform;
    
    private Date lastTime;
    
    private Date now;
    
    private Integer msgType;
    

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

}
