package com.taisf.api.common.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jk.framework.base.head.Header;
import com.jk.framework.cache.redis.api.RedisOperations;

/**
 * 
 * @Description: TOKEN拦截器
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    
    @Autowired
	private RedisOperations redisOperations;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         final Header header = (Header) request.getAttribute(HeadersInterceptor.HEADER);

        request.setAttribute(HeadersInterceptor.USERID, "afi");
         return true;

//
//         String loginKey = RedisConstant.LOGIN_KEY + userToken;
//         ResponseDto dto = new ResponseDto("登录已失效", ReturnEnum.TOKEN_INVALID.getCode());
//         if(!redisOperations.exists(loginKey)){
//        	 StreamUtils.outMessage(JsonEntityTransform.Object2Json(dto), response);
//        	 return false;
//         }
//    	 String userStr = redisOperations.get(loginKey);
//         UserModelVO userModel = JsonEntityTransform.json2Object(userStr, UserModelVO.class);
//         if(userModel == null){
//        	 StreamUtils.outMessage(JsonEntityTransform.Object2Json(dto), response);
//        	 return false;
//         }
//        if(Check.NuNStr(header.getDeviceUuid())){
//            StreamUtils.outMessage(JsonEntityTransform.Object2Json( new ResponseDto("获取权限失败")), response);
//            return false;
//        }
//        if(!header.getDeviceUuid().equals(userModel.getDeviceUuid())){
//            StreamUtils.outMessage(JsonEntityTransform.Object2Json( new ResponseDto("获取权限失败")), response);
//            return false;
//        }
//        request.setAttribute(HeadersInterceptor.USERID, userModel.getUserId());
//         return true;
    }
}
