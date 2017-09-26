package com.taisf.services.supplier.dto;

import com.jk.framework.base.entity.BaseEntity;

/**
 * <p>TODO</p>
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
public class SupplierProductRequest extends BaseEntity {


    /**
     * 供应商的code
     */
    private String supplierCode;

    /**
     * 商品分类
     */
    private Integer productClassify;

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getProductClassify() {
        return productClassify;
    }

    public void setProductClassify(Integer productClassify) {
        this.productClassify = productClassify;
    }
}
