package com.taisf.services.base.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.log.utils.LogUtil;

import com.taisf.services.base.api.BannerAppService;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.entity.BannerAppEntity;
import com.taisf.services.base.manager.BannerAppManagerImpl;
import com.taisf.services.base.manager.BannerManagerImpl;
import com.taisf.services.base.vo.BannerAppTreeNodeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("basedata.bannerAppServiceProxy")
public class BannerAppProxy implements BannerAppService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerAppProxy.class);
	
	@Autowired
	private BannerAppManagerImpl bannerAppServiceImpl;

	@Override
	public DataTransferObject<List<BannerAppTreeNodeVO>> listBannerApp(BannerAppRequest request) {
		DataTransferObject<List<BannerAppTreeNodeVO>> dto = new DataTransferObject<>();
        try{
        	List<BannerAppTreeNodeVO> listBanner = bannerAppServiceImpl.getBannerAppList(request);
            dto.setData(listBanner);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"查询banner列表异常:{}",e);
        }
        return dto;
	}


	@Override
	public DataTransferObject<PagingResult<BannerAppEntity>> getBannerAppForPage(BannerAppRequest request) {
		DataTransferObject<PagingResult<BannerAppEntity>> dto = new DataTransferObject<>();
        try{
			PagingResult<BannerAppEntity> result = bannerAppServiceImpl.getBannerAppForPage(request);
			dto.setData(result);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"查询banner列表异常:{}",e);
        }
        return dto;
	}

	@Override
	public DataTransferObject<Void> saveBannerApp(BannerAppEntity entity) {
		DataTransferObject<Void> dto = new DataTransferObject<>();
        try{
			bannerAppServiceImpl.saveBannerApp(entity);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"saveBannerApp异常:{}",e);
        }
        return dto;
	}

	@Override
	public DataTransferObject<Void> updateBannerApp(BannerAppEntity entity) {
		DataTransferObject<Void> dto = new DataTransferObject<>();
		try{
			bannerAppServiceImpl.updateBannerApp(entity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"saveBannerApp异常:{}",e);
		}
		return dto;
	}

	@Override
	public DataTransferObject<BannerAppEntity> getBannerAppByAppCode(String appCode) {
		DataTransferObject<BannerAppEntity> dto = new DataTransferObject<>();
		try{
			BannerAppEntity bannerAppEntity = bannerAppServiceImpl.getBannerAppByAppCode(appCode);
			dto.setData(bannerAppEntity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"saveBannerApp异常:{}",e);
		}
		return dto;
	}


}
