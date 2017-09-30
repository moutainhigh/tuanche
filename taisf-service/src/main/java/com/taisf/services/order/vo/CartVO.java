package com.taisf.services.order.vo;

import com.taisf.services.order.entity.CartEntity;

/**
 * <p>购物车展示</p>
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
public class CartVO extends CartEntity{

    /**
     * 商品名称
     */
    private String productName;


    /**
     * 商品价格
     */
    private Integer productPrice;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }
}
