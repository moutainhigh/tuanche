package com.taisf.services.product.dao;

import com.jk.framework.base.utils.Check;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.product.entity.ProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>商品信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/13.
 * @version 1.0
 * @since 1.0
 */
@Repository("product.productDao")
public class ProductDao extends BaseDao {

    private String SQLID = "product.productDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ProductDao.class);


    /**
     * 根据id获取当前的菜单
     * @author afi
     * @param id
     * @return
     */
    public ProductEntity getProductById(Integer id){
        return mybatisDaoContext.findOneSlave(SQLID+"getProductById", ProductEntity.class, id);
    }







    /**
     * 根据id获取当前的菜单list
     * @author afi
     * @param list
     * @return
     */
    public List<ProductEntity> getProductByList(List<Integer> list){
        Map<String,Object> par = new HashMap<>();
        par.put("productIds",list);
        return mybatisDaoContext.findAll(SQLID+"getProductByList", ProductEntity.class, par);
    }


    /**
     * 修改商品信息
     * @author afi
     * @param record
     * @return
     */
    public int updateProduct(ProductEntity record){
       return mybatisDaoContext.update(SQLID + "updateProduct", record);
    }


    /**
     * 新增商品信息
     * @author afi
     * @param record
     * @return
     */
    public int saveProduct(ProductEntity record){
        if (Check.NuNObj(record.getCreateTime())){
            record.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "saveProduct", record);
    }



}
