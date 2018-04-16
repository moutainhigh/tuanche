package com.taisf.services.pay.dao;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.utils.Check;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.pay.entity.RechargeOrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>用户的充值</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/25.
 * @version 1.0
 * @since 1.0
 */
@Repository("pay.rechargeOrderDao")
public class RechargeOrderDao extends BaseDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RechargeOrderDao.class);

    private String SQLID = "pay.rechargeOrderDao.";

    /**
     * 获取当前的配置信息
     * @author afi
     * @param orderSn
     * @return
     */
    public RechargeOrderEntity getRechargeOrderByOrderSn(String orderSn){
        Map<String,Object> par = new HashMap<>();
        par.put("orderSn",orderSn);
        return mybatisDaoContext.findOne(SQLID + "getRechargeOrderByOrderSn",RechargeOrderEntity.class,par);
    }



    /**
     * 保存支付回调信息
     * @author afi
     * @param rechargeOrderEntity
     * @return
     */
    public int saveRechargeOrder(RechargeOrderEntity  rechargeOrderEntity){
        if (Check.NuNObj(rechargeOrderEntity.getCreateTime())){
            rechargeOrderEntity.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "saveRechargeOrder",rechargeOrderEntity);
    }


    /**
     * 修改支付记录
     * @author afi
     * @param recordEntity
     * @return
     */
    public int updateRechargeOrder(RechargeOrderEntity  recordEntity){
        if (Check.NuNObjs(recordEntity.getOrderSn())){
            throw new BusinessException("更新主键判断为空");
        }
        return mybatisDaoContext.update(SQLID + "updateRechargeOrder",recordEntity);
    }
}
