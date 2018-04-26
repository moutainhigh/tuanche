package com.taisf.services.push.jiguang;

import com.taisf.services.push.constant.PushBaseConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>极光相关的推送消息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/1.
 * @version 1.0
 * @since 1.0
 */
@Component("pushJiguangConstant")
public class PushJiguangConstant extends PushBaseConstant {


    @Value("${jiguang.appSecret}")
    protected String JIGUANG_APPSECRET;

    @Value("${jiguang.appkey}")
    protected String JIGUANG_APPKEY;




    //获取认证Token的URL
    protected static  String TOKEN_URL = "https://login.vmall.com/oauth2/token";

    //应用级消息下发API
    protected static  String API_URL = "https://api.push.hicloud.com/pushsend.do";



}
