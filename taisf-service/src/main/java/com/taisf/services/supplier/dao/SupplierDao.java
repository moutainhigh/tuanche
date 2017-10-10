package com.taisf.services.supplier.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.supplier.entity.SupplierEntity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * <p>供应商的信息</p>
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
@Repository("supplier.supplierDao")
public class SupplierDao extends BaseDao {

    private String SQLID = "supplier.supplierDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(SupplierDao.class);
    
    /**
     * 获取供应商列表
     * @return
     */
    public List<SupplierEntity> getAllSupplierList(){
        return mybatisDaoContext.findAll(SQLID+"getAllSupplierList", SupplierEntity.class);
    }

    /**
     * 获取当前的供应商信息
     * @author afi
     * @param supplierCode
     * @return
     */
    public SupplierEntity getSupplierByCode(String supplierCode){
        return mybatisDaoContext.findOne(SQLID+"getSupplierByCode", SupplierEntity.class, supplierCode);
    }

    /**
     * 增加供应商基本信息
     * @author afi
     * @param record
     * @return
     */
    public int saveSupplier(SupplierEntity record){
        return mybatisDaoContext.save(SQLID + "saveSupplier", record);
    }
    /**
     * 修改供应商基本信息
     * @author afi
     * @param record
     * @return
     */
    public int updateSupplier(SupplierEntity record){
        return mybatisDaoContext.update(SQLID + "updateSupplier", record);
    }
}
