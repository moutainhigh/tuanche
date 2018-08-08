package com.taisf.services.base.manager;

import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dao.BannerPositionDao;
import com.taisf.services.base.dto.BannerPositionRequest;
import com.taisf.services.base.entity.BannerPositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BannerPositionManagerImpl {

	@Autowired
	private BannerPositionDao bannerPositionDao;
	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:根据ID查询
	 **/
	public BannerPositionEntity getBannerPositionById(Integer id){
		return bannerPositionDao.getBannerPositionById(id);
	}



	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:保存
	 **/
	public int saveBannerPosition(BannerPositionEntity  entity){
		return bannerPositionDao.saveBannerPosition(entity);
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:更新
	 **/
	public int updateBannerPosition(BannerPositionEntity entity){
		return bannerPositionDao.updateBannerPosition(entity);
	}


	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:查询所有
	 **/
	public List<BannerPositionEntity> getBannerPositionList(BannerPositionRequest request){
		return bannerPositionDao.getBannerPositionList(request);
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:分页查询
	 **/
	public PagingResult<BannerPositionEntity> getBannerPositionForPage(BannerPositionRequest request){
		return bannerPositionDao.getBannerPositionForPage(request);
	}

	public BannerPositionEntity getByAppCodeAndPositionCode(String appCode,String positionCode){
		return bannerPositionDao.getByAppCodeAndPositionCode(appCode,positionCode);
	}
}
