package com.taisf.services.test.order.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.common.valenum.OrderTypeEnum;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.CreateOrderRequest;
import com.taisf.services.order.dto.FinishOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.dto.RefundOrderRequest;
import com.taisf.services.order.vo.FaceVO;
import com.taisf.services.order.vo.OrderDetailVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderSaveInfo;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/9.
 * @version 1.0
 * @since 1.0
 */
public class OrderServiceProxyTest extends BaseTest {


    @Resource(name = "order.orderServiceProxy")
    private OrderService orderService;






    @Test
    public void getOrderInfoWaitingListTest() {

        DataTransferObject<List<OrderInfoVO>> classify = orderService.getOrderInfoWaitingList("11");
        System.out.println(JsonEntityTransform.Object2Json(classify));

    }



    @Test
    public void finishOrderTest() {
        FinishOrderRequest request = new FinishOrderRequest();
        request.setOpId("afi");
        request.setOrderSn("171016EWA23FJF114304");
        DataTransferObject<Void> classify = orderService.finishOrder(request);
        System.out.println(JsonEntityTransform.Object2Json(classify));

    }

    @Test
    public void faceOrderTest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setUserUid("baozi");
        createOrderRequest.setBusinessUid("jipin");
        createOrderRequest.setPwd("96e79218965eb72c92a549dd5a330112");
        createOrderRequest.setSource(3);
        createOrderRequest.setPrice(100);
        DataTransferObject<FaceVO> classify = orderService.faceOrder(createOrderRequest,true);
        System.out.println(JsonEntityTransform.Object2Json(classify));
    }



    @Test
    public void initOrderTest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setUserUid("afi");
        createOrderRequest.setBusinessUid("jipin");
        createOrderRequest.setOrderType(OrderTypeEnum.LUNCH_COMMON.getCode());
        createOrderRequest.setPwd("96e79218965eb72c92a549dd5a330112");
        createOrderRequest.setSource(1);
        createOrderRequest.setEnterpriseCode("qpg001");
        DataTransferObject<OrderSaveInfo> classify = orderService.initOrder(createOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));

    }


    @Test
    public void createOrderTest() {

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setUserUid("afi");
        createOrderRequest.setBusinessUid("jipin");
        createOrderRequest.setOrderType(OrderTypeEnum.LUNCH_COMMON.getCode());
        createOrderRequest.setPwd("96e79218965eb72c92a549dd5a330112");
        createOrderRequest.setSource(1);
        createOrderRequest.setAddressFid("2c91cb36616a0f9001616a0f90290000");
        createOrderRequest.setEnterpriseCode("qpg001");
        DataTransferObject<String> classify = orderService.createOrder(createOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));

    }



    @Test
    public void initExtOrderTest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setUserUid("afi");
        createOrderRequest.setBusinessUid("123");
        createOrderRequest.setOrderType(OrderTypeEnum.DINNER_EXT.getCode());
        createOrderRequest.setPwd("123");
        createOrderRequest.setSource(1);
        createOrderRequest.setAddressFid("111");
        DataTransferObject<OrderSaveInfo> classify = orderService.initExtOrder(createOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));

    }


    @Test
    public void createExtOrderTest() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setUserUid("afi");
        createOrderRequest.setBusinessUid("123");
        createOrderRequest.setOrderType(OrderTypeEnum.DINNER_EXT.getCode());
        createOrderRequest.setPwd("123");
        createOrderRequest.setSource(1);
        createOrderRequest.setAddressFid("111");
        DataTransferObject<String> classify = orderService.createExtOrder(createOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));
    }



    @Test
    public void getOrderInfoPageTest() {
        OrderInfoRequest createOrderRequest = new OrderInfoRequest();
        createOrderRequest.setUserUid("afi");
//        createOrderRequest.setBalanceFlag(true);
        DataTransferObject<PagingResult<OrderInfoVO>> classify = orderService.getOrderInfoPage(createOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));
    }


    @Test
    public void getOrderDetailBySnTest() {
        DataTransferObject<OrderDetailVO> classify = orderService.getOrderDetailBySn("171009OKT029M9215107");
        System.out.println(JsonEntityTransform.Object2Json(classify));
    }



    @Test
    public void refundOrderTest() {
        RefundOrderRequest refundOrderRequest = new RefundOrderRequest();
        refundOrderRequest.setOpId("baozi");
        refundOrderRequest.setOrderSn("TA1801052K3HADXU143515");
        DataTransferObject<String> classify = orderService.refundOrder(refundOrderRequest);
        System.out.println(JsonEntityTransform.Object2Json(classify));
    }

    @Test
    public void getOrderListPageByEnterprisCodeTest() {
        OrderInfoRequest orderInfoRequest = new OrderInfoRequest();
        orderInfoRequest.setEnterpriseCode("0001");
        //orderInfoRequest.setUserName("周明敬");
        //orderInfoRequest.setUserTel("18610407470");
        orderInfoRequest.setOrderStatus(1);
        DataTransferObject<PagingResult<OrderInfoVO>> dto = orderService.getOrderListPageByEnterprisCode(orderInfoRequest);
        System.out.println(JsonEntityTransform.Object2Json(dto));
    }


}
