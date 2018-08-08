package com.taisf.services.base.manager;

import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dao.BannerDao;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.base.vo.BannerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName BannerServiceImpl
 * @Description banner service
 * @author rxg
 * @Date 2017年8月25日 上午11:49:34
 * @version 1.0.0
 */
@Component
public class BannerManagerImpl {

	@Autowired
	private BannerDao bannerDao;
	
	/**
	 * @Description 查询banner列表
	 * @param request
	 * @return
	 */
	public List<BannerEntity> listBanner(BannerReq request) {
		return bannerDao.getBannerList(request);
	}

	/**
	 * 上架或者下架 修改状态
	 * @param id
	 * @param oldStatus
	 * @param newStatus
	 * @return
	 */
	public void changeStatus(Integer id, Integer oldStatus, Integer newStatus){
		bannerDao.changeStatus(id, oldStatus, newStatus);
	}

    /**
     * 根據id查詢banner
     * @param id
     */
    public BannerEntity getBannerById(Integer id){
        return bannerDao.getBannerById(id);
    }

	/**
	 * 根据id查询banner
	 * @param id
	 */
	public BannerEntity getBannerByIdAndStatus(Integer id, Integer oldStatus){
		return bannerDao.getBannerByIdAndStatus(id, oldStatus);
	}

    /**
     * 保存banner
     * @param bannerEntity
     */
	public void saveBanner(BannerEntity bannerEntity){
	    bannerDao.saveBanner(bannerEntity);
    }

    /**
     * 编辑banner
     * @param bannerEntity
     */
    public void updateBanner(BannerEntity bannerEntity){
        bannerDao.updateBanner(bannerEntity);
    }

	/***
	 * @description: 分页查询banner
	 * @author: zll
	 * @date: 2018/6/14
	 */
	public PagingResult<BannerEntity> getBannerForPage(BannerReq request){
		return bannerDao.getBannerForPage(request);
	}

	public List<BannerVo> list(BannerReq request) {
		return bannerDao.listBanner(request);
	}
}
