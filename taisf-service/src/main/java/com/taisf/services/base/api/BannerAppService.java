package com.taisf.services.base.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.entity.BannerAppEntity;
import com.taisf.services.base.vo.BannerAppTreeNodeVO;

import java.util.List;

/**
 * @ClassName BannerAppService
 * @Description banner 服务
 * @author rxg
 * @Date 2017年8月25日 上午11:42:49
 * @version 1.0.0
 */
public interface BannerAppService {

	DataTransferObject<List<BannerAppTreeNodeVO>> listBannerApp(BannerAppRequest request);


	DataTransferObject<PagingResult<BannerAppEntity>> getBannerAppForPage(BannerAppRequest request);


	DataTransferObject<Void> saveBannerApp(BannerAppEntity entity);

	DataTransferObject<Void> updateBannerApp(BannerAppEntity entity);


	DataTransferObject<BannerAppEntity> getBannerAppByAppCode(String appCode);


}
