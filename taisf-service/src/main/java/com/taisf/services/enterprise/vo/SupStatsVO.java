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
public class SupStatsVO extends SupOrderStatsVO{

    private static final long serialVersionUID = 301231231201446703L;


    /**
     * 充值次数
     */
    private Integer rechargeNum;

    /**
     * 充值金额
     */
    private Integer rechargePrice;



    /**
     * 自主充值次数
     */
    private Integer orderRechargeNum;

    /**
     * 自主充值金额
     */
    private Integer orderRechargePrice;


    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Integer getRechargePrice() {
        return rechargePrice;
    }

    public void setRechargePrice(Integer rechargePrice) {
        this.rechargePrice = rechargePrice;
    }

    public Integer getOrderRechargeNum() {
        return orderRechargeNum;
    }

    public void setOrderRechargeNum(Integer orderRechargeNum) {
        this.orderRechargeNum = orderRechargeNum;
    }

    public Integer getOrderRechargePrice() {
        return orderRechargePrice;
    }

    public void setOrderRechargePrice(Integer orderRechargePrice) {
        this.orderRechargePrice = orderRechargePrice;
    }
}
