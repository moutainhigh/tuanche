package com.taisf.api.user.controller;

import com.jk.api.dependency.common.abs.AbstractController;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.CreateOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderDetailVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderSaveVO;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;
import com.taisf.services.user.vo.RegistInfoVO;
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
 * <p>用户先关接口</p>
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
@RequestMapping("/user")
public class UserController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private UserService userService;

    /**
     * 登录
     * @author afi
      * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto login(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        UserLoginRequest paramRequest = getEntity(request, UserLoginRequest.class);
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<String> dto =userService.login(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【登录】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }


    /**
     * 退出
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        UserLogoutRequest paramRequest = getEntity(request, UserLogoutRequest.class);
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<Void> dto =userService.logout(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【退出】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }



    /**
     * 注册
     * @author afi
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto regist(HttpServletRequest request, HttpServletResponse response) {
        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        //获取当前参数
        UserRegistRequest paramRequest = getEntity(request, UserRegistRequest.class);
        if (Check.NuNObj(paramRequest)) {
            return new ResponseDto("参数异常");
        }

        LogUtil.info(LOGGER, "传入参数:{}", JsonEntityTransform.Object2Json(paramRequest));
        try {

            DataTransferObject<RegistInfoVO> dto =userService.regist(paramRequest);
            return dto.trans2Res();
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【注册】错误,par:{}, e={}",JsonEntityTransform.Object2Json(paramRequest), e);
            return new ResponseDto("未知错误");
        }

    }

}
