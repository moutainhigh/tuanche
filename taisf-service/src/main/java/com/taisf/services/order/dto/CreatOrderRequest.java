package com.taisf.services.order.dto;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>创建订单的参数</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/28.
 * @version 1.0
 * @since 1.0
 */
public class CreatOrderRequest extends BaseEntity{

    private static final long serialVersionUID = 301231201446703L;

    /**
     * 商家uid
     */
    private String businessUid;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 收货地址
     */
    private String addressFid;


    /**
     * 支付密码
     */
    private String pwd;


    public String getBusinessUid() {
        return businessUid;
    }

    public void setBusinessUid(String businessUid) {
        this.businessUid = businessUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getAddressFid() {
        return addressFid;
    }

    public void setAddressFid(String addressFid) {
        this.addressFid = addressFid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
