package com.taisf.api.user.controller;

import com.jk.framework.base.head.Header;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.jk.framework.cache.redis.api.RedisOperations;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.api.util.HeaderUtil;
import com.taisf.services.common.valenum.SmsTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/send")
public class CodeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);



    @Autowired
    private RedisOperations redisOperation;


    @RequestMapping(value ="code")
    public @ResponseBody
    ResponseDto sendSmsCode(HttpServletRequest request, HttpServletResponse response,Integer code,String userTel) {

        Header header = getHeader(request);
        if (Check.NuNObj(header)) {
            return new ResponseDto("头信息为空");
        }
        if(Check.NuNStr(header.getApplicationCode())
                || Check.NuNStr(header.getDeviceUuid())){
            return new ResponseDto("异常的头信息");
        }

        if (Check.NuNObj(code)) {
            return new ResponseDto("参数异常");
        }
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.getTypeByCode(code);
        if (Check.NuNObj(smsTypeEnum)){
            return new ResponseDto("异常的code");
        }

        String  key = HeaderUtil.getCodeStr(header,code);
        if (Check.NuNStr(key)){
            return new ResponseDto("参数异常");
        }
        String msgCode = "123456";
        redisOperation.setex(key, smsTypeEnum.getTime(), msgCode);
        return new ResponseDto();
    }

}
