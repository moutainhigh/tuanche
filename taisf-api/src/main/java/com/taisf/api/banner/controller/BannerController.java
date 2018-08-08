package com.taisf.api.banner.controller;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.rst.ResponseDto;
import com.jk.framework.base.utils.Check;
import com.taisf.api.common.abs.AbstractController;
import com.taisf.services.base.api.BannerService;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.vo.BannerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author:zhangzhengguang
 * @date:2017/10/26
 * @description:banner
 **/
@Controller
@RequestMapping("banner")
public class BannerController extends AbstractController {

    @Autowired
    private BannerService bannerService;


    
    /**
     * @Description 查询banner信息（新版本）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseDto list(HttpServletRequest request, HttpServletResponse response) {
    	BannerReq req = getEntity(request, BannerReq.class);
    	if(Check.NuNObj(req)){
    		return new ResponseDto("参数异常",DataTransferObject.ERROR);
    	}
    	if(Check.NuNObj(req.getPositionCode())){
    		return new ResponseDto("参数异常",DataTransferObject.ERROR);
    	}
    	String[] split = req.getPositionCode().split("\\,");
    	req.setPositionList(Arrays.asList(split));
    	Header header = getHeader(request);
    	if(!Check.NuNObj(header)) {
    		req.setAppCode(header.getApplicationCode());
    		req.setPlatform(header.getSource());
    	}
    	DataTransferObject<List<BannerRes>> result = bannerService.list(req);
    	return result.trans2Res();
    }


}	

