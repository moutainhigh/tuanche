package com.taisf.services.order.vo;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>每日任务</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/11/8.
 * @version 1.0
 * @since 1.0
 */
public class DayTaskVO extends BaseEntity {

    private static final long serialVersionUID = 301231231201446703L;

    /**
     * 名称
     */
    private String productName;

    /**
     * 数量
     */
    private Integer  productNum;

    /**
     * 分类
     */
    private Integer  productClassify;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getProductClassify() {
        return productClassify;
    }

    public void setProductClassify(Integer productClassify) {
        this.productClassify = productClassify;
    }
}
