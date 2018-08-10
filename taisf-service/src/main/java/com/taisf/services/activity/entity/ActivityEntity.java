package com.taisf.services.activity.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>活动</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2018/8/8.
 * @version 1.0
 * @since 1.0
 */
public class ActivityEntity  extends BaseEntity {

    /**
     * 编号
     */
    private Integer id;

    /**
     * 活动码
     */
    private String actSn;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 活动状态 1：未启用 2：已启用 3：已中止 4：已过期
     */
    private Integer actStatus;

    /**
     * 活动类型 1、充值返利
     */
    private Integer actType;


    /**
     * 活动描述
     */
    private String actDesc;

    /**
     * 活动开始时间
     */
    private Date actStartTime;

    /**
     * 活动结束时间
     */
    private Date actEndTime;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建时间
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Integer getActStatus() {
        return actStatus;
    }

    public void setActStatus(Integer actStatus) {
        this.actStatus = actStatus;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public String getActDesc() {
        return actDesc;
    }

    public void setActDesc(String actDesc) {
        this.actDesc = actDesc;
    }

    public Date getActStartTime() {
        return actStartTime;
    }

    public void setActStartTime(Date actStartTime) {
        this.actStartTime = actStartTime;
    }

    public Date getActEndTime() {
        return actEndTime;
    }

    public void setActEndTime(Date actEndTime) {
        this.actEndTime = actEndTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
