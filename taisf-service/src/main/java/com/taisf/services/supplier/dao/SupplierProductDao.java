package com.taisf.services.supplier.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.product.entity.ProductEntity;
import com.taisf.services.supplier.dto.SupplierProductRequest;
import com.taisf.services.supplier.entity.SupplierProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>供应商的财务信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/20.
 * @version 1.0
 * @since 1.0
 */
@Repository("supplier.supplierProductDao")
public class SupplierProductDao extends BaseDao {

    private String SQLID = "supplier.supplierProductDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SupplierProductDao.class);




    /**
     * 获取当前供应商的菜单信息
     * @author afi
     * @param supplierProductRequest
     * @return
     */
    public List<ProductEntity> getProductListBySupplierAndType(SupplierProductRequest supplierProductRequest){
        return mybatisDaoContext.findAll(SQLID+"getProductListBySupplierAndType", ProductEntity.class, supplierProductRequest);
    }


    /**
     * 获取当前的供应商菜单信息
     * @author afi
     * @param supplierCode
     * @return
     */
    public List<SupplierProductEntity> getSupplierProductByCode(String supplierCode){
        return mybatisDaoContext.findAll(SQLID+"getSupplierProductByCode", SupplierProductEntity.class, supplierCode);
    }

    /**
     * 增加供应商菜单信息
     * @author afi
     * @param record
     * @return
     */
    public int saveSupplierProduct(SupplierProductEntity record){
        return mybatisDaoContext.save(SQLID + "saveSupplierProduct", record);
    }
    /**
     * 修改供应商菜单信息
     * @author afi
     * @param record
     * @return
     */
    public int updateSupplierProduct(SupplierProductEntity record){
        return mybatisDaoContext.update(SQLID + "updateSupplierProduct", record);
    }
}
