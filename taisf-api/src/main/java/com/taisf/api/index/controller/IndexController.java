package com.taisf.api.index.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.services.user.api.IndexService;
import com.taisf.services.user.vo.IndexVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>抽象service</p>
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
public class IndexController extends AbstractController {


    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);




    @RequestMapping("")
    public @ResponseBody
    ResponseDto getIndex(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseDto("api ok");
    }



}
