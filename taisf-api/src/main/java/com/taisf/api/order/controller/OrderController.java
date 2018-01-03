package com.taisf.api.order.controller;

import com.jk.framework.base.constant.YesNoEnum;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.CreateOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderDetailVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderSaveInfo;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>订单相关</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private OrderService ordersService;

    @Autowired
    private UserService userService;

    /**
     * 初始化下单
     * @author afi
      * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/initOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto initOrder(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        CreateOrderRequest paramRequest = getEntity(request, CreateOrderRequest.class);
        paramRequest.setSource(header.getSource());
        paramRequest.setUserUid(getUserId(request));
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<OrderSaveInfo> dto =ordersService.initOrder(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【订单初始化】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }

    /**
     * 初始化补单
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/initExtOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto initExtOrder(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        CreateOrderRequest paramRequest = getEntity(request, CreateOrderRequest.class);
        paramRequest.setSource(header.getSource());
        paramRequest.setUserUid(getUserId(request));
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<OrderSaveInfo> dto =ordersService.initExtOrder(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【补单初始化】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }


    /**
     * 下单逻辑
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createOrder(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        CreateOrderRequest paramRequest = getEntity(request, CreateOrderRequest.class);
        paramRequest.setSource(header.getSource());
        paramRequest.setUserUid(getUserId(request));
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<String> dto =ordersService.createOrder(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【下单】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }



    /**
     * 补单逻辑
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/createExtOrder", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createExtOrder(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        CreateOrderRequest paramRequest = getEntity(request, CreateOrderRequest.class);
        paramRequest.setSource(header.getSource());
        paramRequest.setUserUid(getUserId(request));
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<String> dto =ordersService.createExtOrder(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【下单】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }
    }


    /**
     * 分页查询列表信息
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto listPage(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        OrderInfoRequest paramRequest = getEntity(request, OrderInfoRequest.class);
        paramRequest.setUserUid(getUserId(request));
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<PagingResult<OrderInfoVO>> dto =ordersService.getOrderInfoPage(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【下单】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }
    }



    /**
     * 分页查询列表信息
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody    public ResponseDto info(HttpServletRequest request, HttpServletResponse response,String orderSn) {

        if (Check.NuNStr(orderSn)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(orderSn));
        try {

            DataTransferObject<OrderDetailVO> dto =ordersService.getOrderDetailBySn(orderSn);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【订单详情】错误,par:{}, e={}",JsonEntityTransform.Object2Json(orderSn), e);
            return new ResponseDto("未知错误");
        }
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/1/3
     * @description:数据统计,根据企业code分页查询订单
     **/
    @RequestMapping(value = "/getEnterpriseOrderStats", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getEnterpriseOrderStats(HttpServletRequest request, HttpServletResponse response) {
        DataTransferObject<PagingResult<OrderInfoVO>> dto = new DataTransferObject<>();
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        String userId = getUserId(request);
        OrderInfoRequest paramRequest = getEntity(request, OrderInfoRequest.class);
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }
        if (Check.NuNObjs(userId)) {
            return new ResponseDto("参数异常");
        }
        paramRequest.setUserUid(userId);
        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {
            //校验当前用户是否是管理员
            UserEntity userEntity = userService.getUserByUid(userId).getData();
            if (Check.NuNObj(userEntity)){
                return new ResponseDto("异常的账户信息");
            }
            if(userEntity.getIsAdmin().equals(YesNoEnum.NO.getCode())){
                //不是管理员 返回空
                return dto.trans2Res();
            }
            paramRequest.setEnterpriseCode(userEntity.getEnterpriseCode());
            if (Check.NuNStr(paramRequest.getEnterpriseCode())){
                dto.setErrorMsg("异常的企业信息");
                return dto.trans2Res();
            }
            return ordersService.getOrderListPageByEnterprisCode(paramRequest).trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "数据统计,根据企业code分页查询订单异常:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }
    }
}
