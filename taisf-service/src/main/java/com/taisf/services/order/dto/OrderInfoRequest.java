package com.taisf.services.order.dto;

import com.jk.framework.base.page.PageRequest;

/**
 * <p>订单列表查询</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/25.
 * @version 1.0
 * @since 1.0
 */
public class OrderInfoRequest extends PageRequest {

    private static final long serialVersionUID = 301231231201446703L;

    /**
     * 用户uid
     */
    private String userUid;

    /**
     * 骑士id
     */
    private String knightUid;




    private String enterpriseCode;


    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 预订人电话
     */
    private String userTel;

    /**
     * 开始时间
     */
    private String openTime;
    /**
     * 截止时间
     */
    private String tillTime;

    private String bizCode;

    private Integer isSelf;

    private String supplierName;

    private Integer orderType;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

    public String getKnightUid() {
        return knightUid;
    }

    public void setKnightUid(String knightUid) {
        this.knightUid = knightUid;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getTillTime() {
        return tillTime;
    }

    public void setTillTime(String tillTime) {
        this.tillTime = tillTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

}
