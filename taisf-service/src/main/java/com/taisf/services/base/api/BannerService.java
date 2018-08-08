package com.taisf.services.base.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.base.vo.BannerRes;

import java.util.List;

/**
 * @ClassName BannerService
 * @Description banner 服务
 * @author rxg
 * @Date 2017年8月25日 上午11:42:49
 * @version 1.0.0
 */
public interface BannerService {

	/**
	 * @Description 获取banner列表
	 * @param request
	 * @return
	 */
	DataTransferObject<List<BannerEntity>> listBanner(BannerReq request);

	/***
	 * @description: 分页查询banner列表
	 * @author: zll
	 * @date: 2018/6/14
	 */
	DataTransferObject<PagingResult<BannerEntity>> getBannerForPage(BannerReq request);

	/**
	 * 上架或者下架修改状态
	 * @param id
	 * @param oldStatus
	 * @param newStatus
	 * @return
	 */
	DataTransferObject<Void> changeStatus(Integer id, Integer oldStatus, Integer newStatus);

	/**
	 * 保存banner
	 * @param bannerEntity
	 * @return
	 */
	DataTransferObject<Void> saveBanner(BannerEntity bannerEntity);

	/**
	 * 编辑banner
	 * @param bannerEntity
	 * @return
	 */
	DataTransferObject<Void> updateBanner(BannerEntity bannerEntity);

    /**
     * 根據id查詢banner
     * @param id
     * @return
     */
    DataTransferObject<BannerEntity> getBannerById(Integer id);

	/**
	 * @Description 查询banner列表
	 * @param request
	 * @return
	 */
	DataTransferObject<List<BannerRes>> list(BannerReq request);
}
