package com.taisf.services.base.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.log.utils.LogUtil;
import com.jk.services.basedata.api.BannerPositionService;
import com.jk.services.basedata.entity.BannerPositionEntity;
import com.jk.services.basedata.request.BannerPositionRequest;
import com.jk.services.basedata.service.BannerPositionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("basedata.bannerPositionService")
public class BannerPositionProxy implements BannerPositionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerPositionProxy.class);
	
	@Autowired
	private BannerPositionServiceImpl bannerPositionServiceImpl;

	@Override
	public DataTransferObject<PagingResult<BannerPositionEntity>> PageBannerPositionList(BannerPositionRequest request) {
		DataTransferObject<PagingResult<BannerPositionEntity>> dto = new DataTransferObject<>();
        try{
			PagingResult<BannerPositionEntity> bannerPositionForPage = bannerPositionServiceImpl.getBannerPositionForPage(request);
			dto.setData(bannerPositionForPage);
        }catch (Exception e){
            dto.setErrCode(DataTransferObject.ERROR);
            LogUtil.error(LOGGER,"查询banner列表异常:{}",e);
        }
        return dto;
	}

	@Override
	public DataTransferObject<List<BannerPositionEntity>> getBannerPositionEntityList(BannerPositionRequest request) {

		DataTransferObject<List<BannerPositionEntity>> dto = new DataTransferObject<>();

		try {

			List<BannerPositionEntity> bannerPositionList = bannerPositionServiceImpl.getBannerPositionList(request);

			dto.setData(bannerPositionList);

			return dto;

		}catch (Exception e){

			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"查询banner位置列表异常:{}",e);

		}

		return dto;
	}

	@Override
	public DataTransferObject<Void> saveBannerPosition(BannerPositionEntity entity) {
		DataTransferObject<Void> dto = new DataTransferObject<>();
		try{
			bannerPositionServiceImpl.saveBannerPosition(entity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"saveBannerPosition异常:{}",e);
		}
		return dto;
	}

	@Override
	public DataTransferObject<Void> updateBannerPosition(BannerPositionEntity entity) {
		DataTransferObject<Void> dto = new DataTransferObject<>();
		try{
			bannerPositionServiceImpl.updateBannerPosition(entity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"updateBannerPosition异常:{}",e);
		}
		return dto;
	}


	@Override
	public DataTransferObject<BannerPositionEntity> getByAppCodeAndPositionCode(String appCode,String positionCode) {
		DataTransferObject<BannerPositionEntity> dto = new DataTransferObject<>();
		try{
			BannerPositionEntity bannerPositionEntity = bannerPositionServiceImpl.getByAppCodeAndPositionCode(appCode, positionCode);
			dto.setData(bannerPositionEntity);
		}catch (Exception e){
			dto.setErrCode(DataTransferObject.ERROR);
			LogUtil.error(LOGGER,"updateBannerPosition异常:{}",e);
		}
		return dto;
	}




}
