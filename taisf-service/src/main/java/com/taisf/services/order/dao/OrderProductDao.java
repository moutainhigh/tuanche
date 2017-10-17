package com.taisf.services.order.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.order.dto.OrderProductListRequest;
import com.taisf.services.order.entity.OrderProductEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
@Repository("order.orderProductDao")
public class OrderProductDao extends BaseDao {

    private String SQLID = "order.orderProductDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(OrderProductDao.class);


    /**
     * 获取当前的订单商品信息
     * @author afi
     * @param orderSn
     * @return
     */
    public List<OrderProductEntity> getOrderProductByOrderSn(String orderSn){
        return mybatisDaoContext.findAll(SQLID+"getOrderProductByOrderSn", OrderProductEntity.class, orderSn);
    }


    /**
     * 新增商品信息-批量
     * @author afi
     * @param list
     * @return
     */
    public int batchSaveOrderProduct(List<OrderProductEntity> list){
        return mybatisDaoContext.batchSave(SQLID + "saveOrderProduct", list);
    }


    /**
     * 新增商品信息
     * @author afi
     * @param record
     * @return
     */
    public int saveOrderProduct(OrderProductEntity record){
        return mybatisDaoContext.save(SQLID + "saveOrderProduct", record);
    }
    /**
     * @author:zhangzhengguang
     * @date:2017/10/17
     * @description:分页查询订单下商品信息
     **/
    public PagingResult<OrderProductEntity> getOrderProductPageList(OrderProductListRequest orderProductListRequest){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setPage(orderProductListRequest.getPage());
        pageBounds.setLimit(orderProductListRequest.getLimit());
        return mybatisDaoContext.findForPage(SQLID+"getOrderProductByOrderSn", OrderProductEntity.class,orderProductListRequest.getOrderSn(), pageBounds);
    }
}
