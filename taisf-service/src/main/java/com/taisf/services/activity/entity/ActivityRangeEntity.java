package com.taisf.services.activity.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>TODO</p>
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
public class ActivityRangeEntity  extends BaseEntity {



    private Integer id;

    /**
     * 活动编号
     */
    private String actSn;

    /**
     * 区间
     */
    private Integer rangeLimit;


    /**
     * 值
     */
    private Integer rangeMoney;

    private Date createTime;


    public String getActSn() {
        return actSn;
    }

    public void setActSn(String actSn) {
        this.actSn = actSn;
    }

    public Integer getRangeLimit() {
        return rangeLimit;
    }

    public void setRangeLimit(Integer rangeLimit) {
        this.rangeLimit = rangeLimit;
    }

    public Integer getRangeMoney() {
        return rangeMoney;
    }

    public void setRangeMoney(Integer rangeMoney) {
        this.rangeMoney = rangeMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
