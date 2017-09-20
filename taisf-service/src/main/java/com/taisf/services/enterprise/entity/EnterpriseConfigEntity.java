package com.taisf.services.enterprise.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;


/**
 * <p>
 * 企业的配置信息
 * </p>
 * <p/>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2017/9/20.
 * @version 1.0
 * @since 1.0
 */
public class EnterpriseConfigEntity extends BaseEntity {

    /** 序列化id */
    private static final long serialVersionUID = 7380668097871543955L;
    private Integer id;

    private String enterpriseCode;

    /**
     * 老板午餐
     */
    private Integer lunchBoss;

    /**
     * 老板晚餐价格
     */
    private Integer dinnerBoss;

    /**
     * 员工午餐价格
     */
    private Integer lunchEmp;

    /**
     * 员工晚餐价格
     */
    private Integer dinnerEmp;

    /**
     * 是否午餐
     */
    private Integer forLunch;

    /**
     * 午餐开始时间
     */
    private Date lunchStart;

    /**
     * 午餐结束时间
     */
    private Date lunchEnd;

    /**
     * 是否晚餐
     */
    private Integer forDinner;

    /**
     * 晚餐开始时间
     */
    private Date dinnerStart;

    /**
     * 晚餐开始时间
     */
    private Date dinnerEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode == null ? null : enterpriseCode.trim();
    }

    public Integer getLunchBoss() {
        return lunchBoss;
    }

    public void setLunchBoss(Integer lunchBoss) {
        this.lunchBoss = lunchBoss;
    }

    public Integer getDinnerBoss() {
        return dinnerBoss;
    }

    public void setDinnerBoss(Integer dinnerBoss) {
        this.dinnerBoss = dinnerBoss;
    }

    public Integer getLunchEmp() {
        return lunchEmp;
    }

    public void setLunchEmp(Integer lunchEmp) {
        this.lunchEmp = lunchEmp;
    }

    public Integer getDinnerEmp() {
        return dinnerEmp;
    }

    public void setDinnerEmp(Integer dinnerEmp) {
        this.dinnerEmp = dinnerEmp;
    }

    public Integer getForLunch() {
        return forLunch;
    }

    public void setForLunch(Integer forLunch) {
        this.forLunch = forLunch;
    }

    public Date getLunchStart() {
        return lunchStart;
    }

    public void setLunchStart(Date lunchStart) {
        this.lunchStart = lunchStart;
    }

    public Date getLunchEnd() {
        return lunchEnd;
    }

    public void setLunchEnd(Date lunchEnd) {
        this.lunchEnd = lunchEnd;
    }

    public Integer getForDinner() {
        return forDinner;
    }

    public void setForDinner(Integer forDinner) {
        this.forDinner = forDinner;
    }

    public Date getDinnerStart() {
        return dinnerStart;
    }

    public void setDinnerStart(Date dinnerStart) {
        this.dinnerStart = dinnerStart;
    }

    public Date getDinnerEnd() {
        return dinnerEnd;
    }

    public void setDinnerEnd(Date dinnerEnd) {
        this.dinnerEnd = dinnerEnd;
    }
}