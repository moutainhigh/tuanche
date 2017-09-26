package com.taisf.services.supplier.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.product.manager.ProductManagerImpl;
import com.taisf.services.supplier.dto.SupplierProductRequest;
import com.taisf.services.supplier.service.SupplierProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>获取版本更新信息</p>
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
@Component("basedata.supplierProductServiceProxy")
public class SupplierProductServiceProxy implements SupplierProductService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierProductServiceProxy.class);

    @Resource(name = "basedata.productManagerImpl")
    private ProductManagerImpl productManager;



    /**
     * 获取当前供应商的商品了列表
     * @author  afi
     * @param supplierProductRequest
     * @return
     */
    @Override
    public DataTransferObject getSupplierProductList(SupplierProductRequest supplierProductRequest){
        DataTransferObject dto = new DataTransferObject();
        try {

        } catch (Exception e) {
            LogUtil.error(LOGGER, "【获取商品列表】par:{},error:{}", JsonEntityTransform.Object2Json(supplierProductRequest),e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取商品列表失败");
            return dto;
        }
        return dto;
    }


}
