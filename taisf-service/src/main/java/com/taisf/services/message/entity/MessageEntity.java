package com.taisf.services.message.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

public class MessageEntity extends BaseEntity {
    private Integer id;

    /**
     * 主题: 健康资讯,促销活动,系统通知
     */
    private String subject;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 跳转方式:1跳转原生2:跳转H5
     */
    private Integer linkType;

    private String url;

    /**
     * 接收用户
     */
    private String receiveUser;

    /**
     * 发送方式 站内信,推送,短信
     */
    private Integer sendType;
    /**
     * 发送人name
     */
    private String sendUserName;
    /**
     * 发送人id
     */
    private Integer sendUserId;

    /**
     * 发送范围 1 全体用户 2 部分用户
     */
    private Integer sendScope;
    /**
     * 是否定时发送 0否 1 是
     */
    private Integer timingSend;
    /**
     * 平台1 iOS 2 Android 3 微信
     */
    private Integer platform;
    /**
     * 图片链接
     */
    private String imgUrl;
    /**
     * 消息状态 0未发送 1 已发送
     */
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTimingSend() {
        return timingSend;
    }

    public void setTimingSend(Integer timingSend) {
        this.timingSend = timingSend;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSendScope() {
        return sendScope;
    }

    public void setSendScope(Integer sendScope) {
        this.sendScope = sendScope;
    }

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
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
        this.subject = subject == null ? null : subject.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser == null ? null : receiveUser.trim();
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }
}