package com.taisf.services.order.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.order.dto.CreateOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderDetailVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderSaveInfo;
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
     * @param createOrderRequest
     * @return
     */
    DataTransferObject<OrderSaveInfo> initOrder(CreateOrderRequest createOrderRequest);


    /**
     * 初始化补单
     * @param createOrderRequest
     * @return
     */
    DataTransferObject<OrderSaveInfo> initExtOrder(CreateOrderRequest createOrderRequest);


    /**
     * 下单
     *
     * @param createOrderRequest
     * @return
     */
    DataTransferObject<String> createOrder(CreateOrderRequest createOrderRequest);


    /**
     * 下单[补单]
     * @author afi
     * @param createOrderRequest
     * @return
     */
    DataTransferObject<String> createExtOrder(CreateOrderRequest createOrderRequest);



    /**
     * 获取订单的详细
     * @param orderSn
     * @return
     */
    DataTransferObject<OrderDetailVO>  getOrderDetailBySn(String orderSn);

    /**
     * 获取当前订单的信息
     * @author afi
     * @param orderInfoRequest
     * @return
     */
    DataTransferObject<PagingResult<OrderInfoVO>> getOrderInfoPage(OrderInfoRequest orderInfoRequest);


}
