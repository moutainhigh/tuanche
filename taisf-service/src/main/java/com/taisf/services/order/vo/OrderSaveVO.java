package com.taisf.services.order.vo;

import com.jk.framework.base.entity.BaseEntity;
import com.jk.framework.base.utils.SnUtil;
import com.taisf.services.order.entity.OrderEntity;
import com.taisf.services.order.entity.OrderMoneyEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.user.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>保存订单的vo</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/29.
 * @version 1.0
 * @since 1.0
 */
public class OrderSaveVO extends BaseEntity{

    private static final long serialVersionUID = 301231231201446703L;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * 当前余额
     */
    private  int drawBalance;

    /**
     * 用户信息
     */
    private UserEntity user;

    /**
     * 当前时间
     */
    private Date now = new Date();

    /**
     * 订单的基本信息
     */
    private transient OrderEntity orderBase = new OrderEntity();

    /**
     * 订单的金额信息
     */
    private OrderMoneyEntity orderMoney = new OrderMoneyEntity();

    /**
     * 订单的商品信息
     */
    private List<OrderProductEntity> list = new ArrayList<>();


    /**
     * 不惨价格
     */
    private transient Integer extPrice;


    /**
     * 构造函数
     */
    public OrderSaveVO(String orderSn) {
        this.orderSn = orderSn;
        orderBase.setOrderSn(orderSn);
        orderMoney.setOrderSn(orderSn);
    }

    /**
     * 构造函数
     */
    public OrderSaveVO() {
        String orderSn = SnUtil.getOrderSn();
        this.orderSn = orderSn;
        orderBase.setOrderSn(orderSn);
        orderMoney.setOrderSn(orderSn);
    }


    public OrderEntity getOrderBase() {
        return orderBase;
    }

    public void setOrderBase(OrderEntity orderBase) {
        this.orderBase = orderBase;
    }

    public OrderMoneyEntity getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(OrderMoneyEntity orderMoney) {
        this.orderMoney = orderMoney;
    }

    public List<OrderProductEntity> getList() {
        return list;
    }

    public void setList(List<OrderProductEntity> list) {
        this.list = list;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }


    public int getDrawBalance() {
        return drawBalance;
    }

    public void setDrawBalance(int drawBalance) {
        this.drawBalance = drawBalance;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public Integer getExtPrice() {
        return extPrice;
    }

    public void setExtPrice(Integer extPrice) {
        this.extPrice = extPrice;
    }
}
