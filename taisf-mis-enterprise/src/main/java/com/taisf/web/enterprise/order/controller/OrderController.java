package com.taisf.web.enterprise.order.controller;

import com.jk.framework.base.entity.BaseEle;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.excel.utils.ExcelUtil;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.valenum.OrderTypeEnum;
import com.taisf.services.common.valenum.RecordPayTypeEnum;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.FinishOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.dto.OrderProductListRequest;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.manager.OrderManagerImpl;
import com.taisf.services.order.vo.OrderExcelVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderListVo;
import com.taisf.services.pay.entity.PayRecordEntity;
import com.taisf.services.pay.manager.PayManagerImpl;
import com.taisf.services.ups.entity.EmployeeEntity;
import com.taisf.web.enterprise.common.constant.LoginConstant;
import com.taisf.web.enterprise.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("order/")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderManagerImpl orderManagerImpl;


    @Autowired
    private PayManagerImpl payManager;



    @Autowired
    private OrderService orderService;

    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:订单列表列表
     **/
    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        List<BaseEle> list = OrderTypeEnum.trans2List();
        request.setAttribute("list",list);
        return "order/orderList";
    }




    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:订单详情商品列表
     **/

    @RequestMapping("showOrderProduct")
    public String showOrderProduct(HttpServletRequest request,String orderSn) {
        request.setAttribute("orderSn",orderSn);
        return "order/orderProductList";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:分页查询订单列表数据
     **/
    @RequestMapping("pageList")
    @ResponseBody
    public PageResult pageList(HttpServletRequest request, OrderInfoRequest orderInfoRequest) {
        PageResult pageResult = new PageResult();
        try {
            EmployeeEntity emp = (EmployeeEntity)request.getSession().getAttribute(LoginConstant.SESSION_KEY);
            orderInfoRequest.setBizCode(emp.getEmpBiz());
            PagingResult<OrderInfoVO> pagingResult = orderManagerImpl.pageListOrder(orderInfoRequest);
            if (!Check.NuNObj(pagingResult)) {
                for (OrderInfoVO vo : pagingResult.getList()) {
                    vo.setOrderTypeStr(OrderTypeEnum.transCode2Name(vo.getOrderType()));
                }
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(orderInfoRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:分页查询订单列表数据
     **/
    @RequestMapping("orderProductPageList")
    @ResponseBody
    public PageResult orderProductPageList(HttpServletRequest request, OrderProductListRequest orderProductListRequest) {
        PageResult pageResult = new PageResult();
        try {
            PagingResult<OrderProductEntity> pagingResult = orderManagerImpl.getOrderProductPageList(orderProductListRequest);
            if (!Check.NuNCollection(pagingResult.getList())) {
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }else{
                pageResult.setRows(new OrderProductEntity());
                pageResult.setTotal(1L);
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(orderProductListRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/10/17
     * @description:订单配送
     **/
    @RequestMapping("orderDispatching")
    public String orderDispatching(HttpServletRequest request) {
        return "order/orderDispatching";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/20
     * @description:配送记录页面
     **/
    @RequestMapping("toDistributionRecord")
    public String toDistributionRecord(HttpServletRequest request) {
        return "order/distributionRecordList";
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/10/20
     * @description:配送记录
     **/
    @RequestMapping("distributionRecord")
    @ResponseBody
    public PageResult distributionRecord(HttpServletRequest request, EnterpriseListRequest enterpriseListRequest) {
        PageResult pageResult = new PageResult();
        try {
            EmployeeEntity emp = (EmployeeEntity)request.getSession().getAttribute(LoginConstant.SESSION_KEY);
            enterpriseListRequest.setSupplierCode(emp.getEmpBiz());
            PagingResult<OrderListVo> pagingResult = orderManagerImpl.findPageLsit(enterpriseListRequest);
            if (!Check.NuNObj(pagingResult)) {
                pageResult.setRows(pagingResult.getList());
                pageResult.setTotal(pagingResult.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(enterpriseListRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/1/26
     * @description:查询订单详情
     **/
    @RequestMapping("getOrderBaseBySn")
    @ResponseBody
    public OrderInfoVO getOrderBaseBySn(HttpServletRequest request, String orderSn) {
        OrderInfoVO orderInfoVO;
        try {
            orderInfoVO = orderManagerImpl.getOrderInfoByOrderSn(orderSn);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "查询订单详情异常params:{}", JsonEntityTransform.Object2Json(orderSn));
            LogUtil.error(LOGGER, "查询订单详情异常error:{}", e);
            return null;
        }
        if (!Check.NuNObj(orderInfoVO)){
            RecordPayTypeEnum recordPayTypeEnum = null;
            PayRecordEntity record = payManager.getPayRecordByOrderSn(orderSn);
            if (!Check.NuNObj(record)){
                recordPayTypeEnum =  RecordPayTypeEnum.getTypeByCode(record.getPayType());
            }
            if (Check.NuNObj(recordPayTypeEnum)){
                orderInfoVO.setPayTypeStr("未支付");
            }else {
                orderInfoVO.setPayTypeStr(recordPayTypeEnum.getName());
            }
        }

        return orderInfoVO;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/1/26
     * @description:签收
     **/
    @RequestMapping("signIn")
    @ResponseBody
    public DataTransferObject<Void> signIn(HttpServletRequest request, FinishOrderRequest req) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObj(req)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            EmployeeEntity employeeEntity = (EmployeeEntity)request.getSession().getAttribute(LoginConstant.SESSION_KEY);
            req.setOpId(employeeEntity.getUserId());
            dto = orderService.finishOrder(req);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "签收异常params:{}", JsonEntityTransform.Object2Json(req));
            LogUtil.error(LOGGER, "签收异常error:{}", e);
            return null;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/2/28
     * @description:订单查询导出
     **/
    @RequestMapping("listOrderExcel")
    public void listOrderExcel(HttpServletRequest request, HttpServletResponse response,OrderInfoRequest orderInfoRequest) throws UnsupportedEncodingException {
        response.setContentType("octets/stream");
        String fileName = "订单查询";
        response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1") +".xls");
        try{
            EmployeeEntity emp = (EmployeeEntity)request.getSession().getAttribute(LoginConstant.SESSION_KEY);
            orderInfoRequest.setBizCode(emp.getEmpBiz());
            List<OrderExcelVO> orderExcelVOList = orderManagerImpl.listOrderExcel(orderInfoRequest);
            ExcelUtil.exportExcel(response.getOutputStream(), orderExcelVOList);
        }catch (Exception e){
            LogUtil.error(LOGGER, "订单查询导出excel异常:{}",e);
        }

    }


}
