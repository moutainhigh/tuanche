package com.taisf.api.index.controller;

import com.jk.framework.base.rst.ResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/09.
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {



    @RequestMapping(value ="/")
    public @ResponseBody
    ResponseDto index(HttpServletRequest request, HttpServletResponse response, String par) {
        return new ResponseDto("api ok");
    }

}
