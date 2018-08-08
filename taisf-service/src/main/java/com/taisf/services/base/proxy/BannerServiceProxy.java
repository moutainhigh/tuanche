package com.taisf.services.base.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.base.api.BannerService;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.base.manager.BannerManagerImpl;
import com.taisf.services.base.vo.BannerRes;
import com.taisf.services.base.vo.BannerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BannerServiceProxy
 * @Description banner proxy
 * @author rxg
 * @Date 2017年8月25日 上午11:45:18
 * @version 1.0.0
 */
@Service("base.bannerServiceProxy")
public class BannerServiceProxy implements BannerService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerServiceProxy.class);
	
	@Autowired
	private BannerManagerImpl bannerService;

	@Override
	public DataTransferObject<List<BannerEntity>> listBanner(BannerReq request) {
		DataTransferObject<List<BannerEntity>> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNObjs(request.getPlatform(),request.getPosition(),request.getAppCode())){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try{
        	request.setPositionCode(String.valueOf(request.getPosition()));
        	List<BannerEntity> listBanner = bannerService.listBanner(request);
            dto.setData(listBanner);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"查询banner列表异常:{}",e);
        }
        return dto;
	}
	
	@Override
	public DataTransferObject<List<BannerRes>> list(BannerReq request) {
		DataTransferObject<List<BannerRes>> dto = new DataTransferObject<>();
		if (Check.NuNObj(request)){
			dto.setErrorMsg("参数异常");
			return dto;
		}
		if (Check.NuNObjs(request.getPlatform(),request.getAppCode())){
			dto.setErrorMsg("参数异常");
			return dto;
		}
		if (Check.NuNCollection(request.getPositionList())){
			dto.setErrorMsg("参数异常");
			return dto;
		}
		try{
			List<BannerRes> res = new ArrayList<>();
			List<String> positionList = request.getPositionList();
			for (String position : positionList) {
				request.setPositionCode(position);
				List<BannerVo> listBanner = bannerService.list(request);
				BannerRes bannerRes = new BannerRes();
				bannerRes.setPositionCode(position);
				bannerRes.setBannerList(listBanner);
				res.add(bannerRes);
			}
			dto.setData(res);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"查询banner列表异常:{}",e);
		}
		return dto;
	}

    /***
     * @description: 分页查询banner
     * @author: zll
     * @date: 2018/6/14
     */
    @Override
    public DataTransferObject<PagingResult<BannerEntity>> getBannerForPage(BannerReq request) {
        DataTransferObject<PagingResult<BannerEntity>> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try{
            PagingResult<BannerEntity> page = bannerService.getBannerForPage(request);
            dto.setData(page);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"查询banner分页异常:{}",e);
        }
        return dto;
    }

    /**
     * 修改状态 上架或者下架
     * @param id
     * @param oldStatus
     * @param newStatus
     * @return
     */
    @Override
    public DataTransferObject<Void> changeStatus(Integer id, Integer oldStatus, Integer newStatus) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(id)){
            dto.setErrorMsg("参数异常");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        if (Check.NuNObjs(oldStatus)){
            dto.setErrorMsg("参数异常");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        if (Check.NuNObjs(newStatus)){
            dto.setErrorMsg("参数异常");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        try{
            BannerEntity entity = bannerService.getBannerByIdAndStatus(id, oldStatus);
            if(Check.NuNObj(entity)) {
                dto.setErrorMsg("传入id查询不到banner");
                dto.setErrCode(DataTransferObject.ERROR);
                return dto;
            }
            bannerService.changeStatus(id, oldStatus, newStatus);

        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("修改状态异常");
            LogUtil.error(LOGGER,"修改状态异常:{}",e);
        }
        return dto;
    }

    /**
     * 保存banner
     * @param bannerEntity
     * @return
     */
    @Override
    public DataTransferObject<Void> saveBanner(BannerEntity bannerEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();

        try{
            bannerService.saveBanner(bannerEntity);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("保存banner异常");
            LogUtil.error(LOGGER,"保存banner异常:{}",e);
        }
        return dto;
    }

    /**
     * 编辑banner
     * @param bannerEntity
     * @return
     */
    @Override
    public DataTransferObject<Void> updateBanner(BannerEntity bannerEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();

        try{
            bannerService.updateBanner(bannerEntity);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("编辑banner异常");
            LogUtil.error(LOGGER,"编辑banner异常:{}",e);
        }
        return dto;
    }

    @Override
    public DataTransferObject<BannerEntity> getBannerById(Integer id) {
        DataTransferObject<BannerEntity> dto = new DataTransferObject<>();
        if (Check.NuNObjs(id)){
            dto.setErrorMsg("参数异常");
            dto.setErrCode(DataTransferObject.ERROR);
            return dto;
        }
        try{
            BannerEntity entity = bannerService.getBannerById(id);
            dto.setData(entity);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("根據id查詢banner异常");
            LogUtil.error(LOGGER,"根據id查詢banner异常:{}",e);
        }
        return dto;
    }

}
