package com.taisf.services.enterprise.vo;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>企业订单的额统计新</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/20.
 * @version 1.0
 * @since 1.0
 */
public class EnterpriseOrderStatsVO  extends BaseEntity{

    private static final long serialVersionUID = 301231231201446703L;

    /**
     * 统计区间
     */
    private String time;

    private String enterpriseCode;

    private String enterpriseName;

    /**
     * 补单数量
     */
    private Integer extNum;

    /**
     * 非补单数量
     */
    private Integer noExtNum;

    /**
     * 全部数量
     */
    private Integer allNum;

    /**
     * 支付
     */
    private Integer payMoney;

    /**
     * 余额支付
     */
    private Integer payBalance;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getExtNum() {
        return extNum;
    }

    public void setExtNum(Integer extNum) {
        this.extNum = extNum;
    }

    public Integer getNoExtNum() {
        return noExtNum;
    }

    public void setNoExtNum(Integer noExtNum) {
        this.noExtNum = noExtNum;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Integer getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(Integer payBalance) {
        this.payBalance = payBalance;
    }
}
