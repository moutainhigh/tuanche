package com.taisf.services.order.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.order.dto.CreatOrderRequest;
import com.taisf.services.order.vo.InitOrderVO;
import com.taisf.services.order.vo.OrderSaveVO;

/**
 * <p>订单相关接口</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/28.
 * @version 1.0
 * @since 1.0
 */
public interface OrderService {


    /**
     * 初始化下单
     * @param creatOrderRequest
     * @return
     */
    DataTransferObject<OrderSaveVO> initOrder(CreatOrderRequest creatOrderRequest);


    /**
     * 下单
     *
     * @param creatOrderRequest
     * @return
     */
    DataTransferObject<String> createOrder(CreatOrderRequest creatOrderRequest);

}
