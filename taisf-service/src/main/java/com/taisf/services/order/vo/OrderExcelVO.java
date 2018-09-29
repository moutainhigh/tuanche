package com.taisf.services.order.vo;

import com.jk.framework.base.entity.BaseEntity;
import com.jk.framework.base.utils.Check;
import com.jk.framework.excel.annotation.*;
import com.taisf.services.common.valenum.OrderTypeEnum;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.Date;

/**
 * @author zhangzhengguang
 * @create 2018-02-28
 **/
public class OrderExcelVO extends BaseEntity {

    /** 用户名称 */
    @FieldMeta(name="姓名",order=1)
    private String userName;

    /** 用户电话 */
    @FieldMeta(name="电话",order=2)
    private String userTel;

    /**  收货地址 */
    @FieldMeta(name="地址",order=3)
    private String  address;

    /** 创建时间 */
    @TimeFormatPattern
    @FieldMeta(name="下单时间",order=4)
    private Date createTime;

    /**
     * 订单类型 1:普通 2:补单',
     */
    @FieldMeta(name="订单类型",order=5)
    private String orderType;

    /**
     * 菜品信息
     */
    @FieldMeta(name="菜品信息",order=6)
    private String orderProduct;

    /**
     * 菜品信息
     */
    @FieldMeta(name="订单金额",order=6)
    @MoneyPenny2Yuan
    private Integer sumMoney;

    @Ignore
    private String orderSn;


    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.getTypeByCode(orderType);
        if(!Check.NuNObj(orderTypeEnum)){
            this.orderType = orderTypeEnum.getName();
        }
    }

    public String getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        this.orderProduct = orderProduct;
    }
}
