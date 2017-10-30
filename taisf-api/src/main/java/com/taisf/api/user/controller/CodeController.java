package com.taisf.api.user.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.*;
import com.jk.framework.cache.redis.api.RedisOperations;
import com.jk.framework.common.utils.CloseableHttpUtil;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.api.util.HeaderUtil;
import com.taisf.services.common.valenum.SmsTypeEnum;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    //短信接口地址
    private static final   String url = "http://112.90.92.102:16655/smsgwhttp/sms/submit";



    @Autowired
    private RedisOperations redisOperation;


    /**
     * 发送验证码
     * @param request
     * @param response
     * @param code
     * @param userTel
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="code")
    public @ResponseBody
    ResponseDto sendSmsCode(HttpServletRequest request, HttpServletResponse response,Integer code,String userTel,String sign,String random) throws Exception{

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
        Map<String,Object> parSign = new HashMap<>();
        parSign.put("code",code);
        parSign.put("userTel",userTel);
        parSign.put("random",random);
        parSign.put("sign",sign);
        boolean  signFlag = SignUtils.checkMapSign("Oj0mUTVY",parSign);
        if (!signFlag){
            return new ResponseDto("验签失败");
        }
        String  key = HeaderUtil.getCodeStr(header,code);
        if (Check.NuNStr(key)){
            return new ResponseDto("参数异常");
        }
        if (!checkMsgCode(userTel)){
            return new ResponseDto("超出条数限制");
        }
        String msgCode = getRandom(6);
        Map<String, String> par = new HashMap<>();
        par.put("spid","80012");
        par.put("password","Xg@2017chandi");
        par.put("ac","10691306077");
        par.put("mobiles",userTel);
        String content = "【馋滴网】验证码:" + msgCode +"，您正在使用短信验证。";
        par.put("content", content);
        redisOperation.setex(key, smsTypeEnum.getTime(), msgCode);
        String rst = CloseableHttpUtil.sendFormPost(url,par);
        LogUtil.info(LOGGER,rst);
        Document document = DocumentHelper.parseText(rst);
        Integer result = Integer.parseInt(XmlUtils.getDocumentNode(document, "/returnsms/result"));
        if (!Check.NuNObj(result) && result== 0){
            //成功
        }else {
            String msg = XmlUtils.getDocumentNode(document, "/returnsms/desc");
            return new ResponseDto(msg);
        }
        DataTransferObject dto = new DataTransferObject<>();
        return dto.trans2Res();
    }


    /**
     * 校验手机号是否正常发送
     * @author afi
     * @param userTel
     * @return
     */
    private boolean checkMsgCode(String userTel){
        boolean falg = true;
        String key = DateUtil.dateFormat(new Date()) + userTel;
        Long has =redisOperation.incr(key);
        if(has  > 10){
            falg = false;
        }
        return falg;
    }

    private  static  String getRandom(int length){
        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; ++i) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

}
