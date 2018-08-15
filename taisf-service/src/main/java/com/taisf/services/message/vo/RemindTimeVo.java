package com.taisf.services.message.vo;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * @Author: afi
 * @Description:
 * @Date: 2017/11/15.
 */
public class RemindTimeVo extends BaseEntity{
    private Date remindTime;

    private Integer msgType;

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }
}
