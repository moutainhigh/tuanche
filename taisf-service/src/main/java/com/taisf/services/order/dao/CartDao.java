package com.taisf.services.order.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.order.entity.CartEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>购物车</p>
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
@Repository("order.cartDao")
public class CartDao extends BaseDao {

    private String SQLID = "order.cartDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CartDao.class);


    /**
     * 获取当前的订单商品信息
     * @author afi
     * @param userUid
     * @param businessUid
     * @return
     */
    public List<CartEntity> getCartByUserId(String userUid,String businessUid){
        Map<String,Object> par = new HashMap<>();
        par.put("userUid",userUid);
        par.put("businessUid",businessUid);
        return mybatisDaoContext.findAll(SQLID+"getCartByUserId", CartEntity.class, par);
    }

    /**
     * 增加购物车
     * @author afi
     * @param record
     * @return
     */
    public int saveCart(CartEntity record){
        return mybatisDaoContext.save(SQLID + "saveCart", record);
    }
    /**
     * 修改购物车
     * @author afi
     * @param record
     * @return
     */
    public int updateCart(CartEntity record){
        return mybatisDaoContext.update(SQLID + "updateCart", record);
    }

}
