package com.taisf.web.oms.banner;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.base.api.BannerAppService;
import com.taisf.services.base.api.BannerPositionService;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.dto.BannerPositionRequest;
import com.taisf.services.base.entity.BannerAppEntity;
import com.taisf.services.base.entity.BannerPositionEntity;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("bannerApp/")
public class BannerAppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BannerAppController.class);

    @Resource(name = "bannerAppService")
    private BannerAppService bannerAppService;

    @Resource(name = "bannerPositionService")
    private BannerPositionService bannerPositionService;


    @RequestMapping("listBannerApp")
    public void listBannerApp(HttpServletRequest request, BannerAppRequest req) {
    }

    @RequestMapping("showBannerAppPageList")
    @ResponseBody
    public PageResult showBannerAppPageList(BannerAppRequest request) {
        PageResult pageResult = new PageResult();
        try {
            DataTransferObject<PagingResult<BannerAppEntity>> dto = bannerAppService.getBannerAppForPage(request);
            PagingResult<BannerAppEntity> vos = dto.getData();
            if(!Check.NuNObj(vos)){
                pageResult.setRows(vos.getList());
                pageResult.setTotal(vos.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(request));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return pageResult;
    }

    @RequestMapping("showBannerPositionPageList")
    @ResponseBody
    public PageResult showBannerPositionPageList(BannerPositionRequest request) {
        PageResult pageResult = new PageResult();
        try {

            DataTransferObject<PagingResult<BannerPositionEntity>> dto = bannerPositionService.PageBannerPositionList(request);
            PagingResult<BannerPositionEntity> data = dto.getData();
            if(!Check.NuNObj(data)){
                pageResult.setRows(data.getList());
                pageResult.setTotal(data.getTotal());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(request));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:添加APP
     **/
    @RequestMapping("addBannerApp")
    @ResponseBody
    public DataTransferObject<Void> addBannerApp(BannerAppEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObj(entity)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        BannerAppEntity bannerAppEntity = bannerAppService.getBannerAppByAppCode(entity.getAppCode()).getData();
        if(!Check.NuNObj(bannerAppEntity)){
            dto.setErrorMsg("AppCode已存在");
            return dto;
        }

        try {
            dto = bannerAppService.saveBannerApp(entity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(entity));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:修改APP
     **/
    @RequestMapping("updateBannerApp")
    @ResponseBody
    public DataTransferObject<Void> updateBannerApp(BannerAppEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObj(entity)){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        try {
            dto = bannerAppService.updateBannerApp(entity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(entity));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return dto;
    }


    @RequestMapping("listBannerPosition")
    public void listBannerPosition(HttpServletRequest request,String appCode) {
        request.setAttribute("appCode",appCode);
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:添加位置
     **/
    @RequestMapping("addBannerPosition")
    @ResponseBody
    public DataTransferObject<Void> addBannerPosition(BannerPositionEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObj(entity)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        BannerPositionEntity bannerPositionEntity = bannerPositionService.getByAppCodeAndPositionCode(entity.getAppCode(), entity.getPositionCode()).getData();
        if(!Check.NuNObj(bannerPositionEntity)){
            dto.setErrorMsg("同一APP下不能重复PositionCode");
            return dto;
        }
        try {
            dto = bannerPositionService.saveBannerPosition(entity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(entity));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2018/7/3
     * @description:修改位置
     **/
    @RequestMapping("updateBannerPosition")
    @ResponseBody
    public DataTransferObject<Void> updateBannerPosition(BannerPositionEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if(Check.NuNObj(entity)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        DataTransferObject<BannerPositionEntity> resultDto = bannerPositionService.getByAppCodeAndPositionCode(entity.getAppCode(), entity.getPositionCode());
        if(!Check.NuNObj(resultDto.getData()) && !resultDto.getData().getId().equals(entity.getId())){
            dto.setErrorMsg("同一APP下不能重复PositionCode");
            return dto;
        }
        try {
            dto = bannerPositionService.updateBannerPosition(entity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(entity));
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return dto;
    }

}
