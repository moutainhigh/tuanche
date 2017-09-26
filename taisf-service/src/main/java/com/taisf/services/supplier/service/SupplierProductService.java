package com.taisf.services.supplier.service;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.supplier.dto.SupplierProductRequest;

/**
 * <p>商品列表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/27.
 * @version 1.0
 * @since 1.0
 */
public interface SupplierProductService {

    /**
     * 获取当前供应商的商品了列表
     * @author  afi
     * @param supplierProductRequest
     * @return
     */
    DataTransferObject getSupplierProductList(SupplierProductRequest supplierProductRequest);
}
