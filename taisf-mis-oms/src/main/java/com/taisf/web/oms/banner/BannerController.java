package com.taisf.web.oms.banner;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.base.api.BannerAppService;
import com.taisf.services.base.api.BannerPositionService;
import com.taisf.services.base.api.BannerService;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.dto.BannerPositionRequest;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.base.entity.BannerPositionEntity;
import com.taisf.services.base.vo.BannerAppTreeNodeVO;
import com.taisf.web.oms.common.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BannerController.class);
    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerPositionService bannerPositionService;

    @Autowired
    private BannerAppService bannerAppService;

    /**
     * 跳转banner管理列表
     *
     * @param request
     */
    @RequestMapping("bannerList")
    public void bannerList(HttpServletRequest request) {
        request.setAttribute("bannerAppList",getAppList());
    }
    /**
     * 展示内容
     *
     * @param request
     * @param bannerReq
     * @return
     */
    @RequestMapping("showBannerList")
    @ResponseBody
    public PageResult showBannerList(HttpServletRequest request, BannerReq bannerReq) {
        if (Check.NuNObj(bannerReq)) {
            bannerReq = new BannerReq();
        }
        PageResult result = new PageResult();
        try {
            DataTransferObject<PagingResult<BannerEntity>> resultDto = bannerService.getBannerForPage(bannerReq);
            if (!resultDto.checkSuccess()) {
                return result;
            }
            PagingResult<BannerEntity> page = resultDto.getData();
            if (Check.NuNObj(page)) {
                return result;
            }
            result.setRows(page.getList());
            result.setTotal(page.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询首页列表异常:{}", e);
            return result;
        }
        return result;
    }
    /**
     * 上架下架修改状态
     *
     * @param request
     * @param id
     * @param oldStatus
     * @param newStatus
     */
    @RequestMapping("changeStatus")
    @ResponseBody
    public DataTransferObject<Void> changeStatus(HttpServletRequest request, Integer id, Integer oldStatus, Integer newStatus) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            bannerService.changeStatus(id, oldStatus, newStatus);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "修改状态异常:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        return dto;
    }

    /**
     * 保存banner
     *
     * @param request
     * @param bannerEntity
     * @return
     */
    @RequestMapping("saveBanner")
    @ResponseBody
    public DataTransferObject<Void> saveBanner(HttpServletRequest request, BannerEntity bannerEntity) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            dto = bannerService.saveBanner(bannerEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "保存banner异常:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        return dto;
    }
    /**
     * 跳转边界banner
     *
     * @param request
     */
    @RequestMapping("editBanner")
    public void editBanner(HttpServletRequest request, Integer id) {
        BannerEntity entity = bannerService.getBannerById(id).getData();
        entity.setStartTime(new Timestamp(entity.getStartTime().getTime()));
        entity.setEndTime(new Timestamp(entity.getEndTime().getTime()));
        request.setAttribute("entity", entity);
        request.setAttribute("bannerAppList",getAppList());
    }
    /**
     * 修改编辑banner
     *
     * @param request
     * @param bannerEntity
     * @return
     */
    @RequestMapping("updateBanner")
    @ResponseBody
    public DataTransferObject<Void> updateBanner(HttpServletRequest request, BannerEntity bannerEntity) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            dto = bannerService.updateBanner(bannerEntity);
        } catch (Exception e) {
            LogUtil.info(LOGGER, "编辑修改banner异常", JsonEntityTransform.Object2Json(bannerEntity));
            LogUtil.error(LOGGER, "修改banner异常:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        return dto;
    }

    /**根据appcode查询banner位置列表
     * @param request
     * @param bannerPositionRequest
     * @return
     */
    @RequestMapping("getBannerPositionEntityList")
    @ResponseBody
    public  DataTransferObject<List<BannerPositionEntity>> getBannerPositionEntityList(HttpServletRequest request, BannerPositionRequest bannerPositionRequest) {

        DataTransferObject<List<BannerPositionEntity>> dto = new DataTransferObject();

        try {

            if(Check.NuNStr(bannerPositionRequest.getAppCode())){

                dto.setData(null);

                return  dto;

            }

            bannerPositionRequest.setIsDel(0);

            dto = bannerPositionService.getBannerPositionEntityList(bannerPositionRequest);

        }catch (Exception e){

            LogUtil.error(LOGGER, "查询banner异常:{}", e);

            dto.setErrCode(DataTransferObject.ERROR);

        }

        return  dto;

    }

    /**
     * 跳转新增banner
     *
     * @param request
     */
    @RequestMapping("addBanner")
    public void addBanner(HttpServletRequest request) {

        request.setAttribute("bannerAppList",getAppList());

    }

    private List<BannerAppTreeNodeVO> getAppList() {

        DataTransferObject<List<BannerAppTreeNodeVO>> dto = new DataTransferObject();

        try {

            BannerAppRequest bannerAppRequest =new BannerAppRequest();

            bannerAppRequest.setIsDel(0);

            dto = bannerAppService.listBannerApp(bannerAppRequest);

            return  dto.getData();

        }catch (Exception e){

            LogUtil.error(LOGGER, "查询banner异常:{}", e);

        }

        return  null;

    }


}