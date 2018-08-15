package com.taisf.services.message.dto;

import java.util.Date;

/**
 * 查询列表时请求参数
 */
public class DateRequest extends BaseRequest {
    /**
     * 上次提醒时间
     */
    private Date lastTime;

    /**
     * 平台 1:IOS 2:Android
     */
    private Integer platform;

    /**
     * 1 我的亲友 2 系统消息 3 控糖资讯 4 文章互动
     */
    private Integer msgType;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}
