package com.taisf.services.base.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dto.BannerPositionRequest;
import com.taisf.services.base.entity.BannerPositionEntity;

import java.util.List;

/**
 * @ClassName BannerAppService
 * @Description banner 服务
 * @author rxg
 * @Date 2017年8月25日 上午11:42:49
 * @version 1.0.0
 */
public interface BannerPositionService {

	DataTransferObject<PagingResult<BannerPositionEntity>> PageBannerPositionList(BannerPositionRequest request);


	DataTransferObject<List<BannerPositionEntity>> getBannerPositionEntityList(BannerPositionRequest request);


	DataTransferObject<Void> saveBannerPosition(BannerPositionEntity entity);

	DataTransferObject<Void> updateBannerPosition(BannerPositionEntity entity);



	DataTransferObject<BannerPositionEntity> getByAppCodeAndPositionCode(String appCode, String positionCode);

}
