package com.taisf.services.order.dao;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.utils.Check;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.order.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * <p> 订单的主表操作 </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderBaseDao")
public class OrderBaseDao extends BaseDao{
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderBaseDao.class);

	private String SQLID = "order.orderBaseDao.";


    /**
     * 获取当前订单的信息
     * @author afi
     * @param orderSn
     * @return
     */
    public OrderEntity getOrderBaseByOrderSn(String orderSn){
        if(Check.NuNStr(orderSn)){
        	LogUtil.error(logger,"orderSn is null on getOrderINfoByOrderSn");
            throw  new BusinessException("orderSn is null on getOrderINfoByOrderSn");
        }
        return mybatisDaoContext.findOne(SQLID + "getOrderBaseByOrderSn", OrderEntity.class, orderSn);
    }
    
    



    /**
     * 保存订单信息
     * @author afi
     * @param orderEntity
     * @return
     */
    public int saveOrderBase(OrderEntity orderEntity){
        if (Check.NuNObj(orderEntity)){
            orderEntity.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "saveOrderBase", orderEntity);
    }


    /**
     * 更新订单的信息
     * @author afi
     * @param orderEntity
     * @return
     */
    public int updateOrderBaseByOrderSn(OrderEntity orderEntity){
        if(Check.NuNObj(orderEntity)){
        	LogUtil.error(logger,"orderEntity is null on updateOrderBaseByOrderSn");
            throw new BusinessException("orderEntity is null on updateOrderBaseByOrderSn");
        }
        if(Check.NuNStr(orderEntity.getOrderSn())){
        	LogUtil.error(logger,"orderSn is null on updateOrderBaseByOrderSn");
            throw new BusinessException("orderSn is null on updateOrderBaseByOrderSn");
        }
        return mybatisDaoContext.update(SQLID + "updateOrderBaseByOrderSn", orderEntity);
    }
 
    
    



}
