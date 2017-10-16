package com.taisf.services.recharge.dto;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>单独充值</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/16.
 * @version 1.0
 * @since 1.0
 */
public class BalanceMoneyOneRequest  extends BaseEntity {


    private static final long serialVersionUID = 301231231201446703L;

    private String enterpriseCode;

    /**
     * 电话
     */
    private String userPhone;


    /**
     * 金额
     */
    private Integer moneyYuan = 0;


    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getMoneyYuan() {
        return moneyYuan;
    }

    public void setMoneyYuan(Integer moneyYuan) {
        this.moneyYuan = moneyYuan;
    }
}
