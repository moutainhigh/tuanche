package com.taisf.services.pay.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.pay.dto.RechargeOrderRequest;
import com.taisf.services.pay.entity.RechargeOrderEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2018/4/16.
 * @version 1.0
 * @since 1.0
 */
public interface RechargeOrderService {


    /**
     * 充值的下单逻辑
     * @author  afi
     * @param rechargeOrderRequest
     * @return
     */
    DataTransferObject<String> createRechargeOrder(RechargeOrderRequest rechargeOrderRequest);

    /**
     * 获取充值信息
     * @author  afi
     * @param orderSn
     * @return
     */
    DataTransferObject<RechargeOrderEntity> getRechargeOrderByOrderSn(String orderSn);


    /**
     * 处理回调
     * @author  afi
     * @param rechargeOrderEntity
     * @return
     */
    DataTransferObject<Void> dealRechargeOrder4PayReturn(RechargeOrderEntity rechargeOrderEntity);
}
