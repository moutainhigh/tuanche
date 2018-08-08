package com.taisf.services.base.manager;

import com.jk.framework.base.page.PagingResult;
import com.taisf.services.base.dao.BannerAppDao;
import com.taisf.services.base.dto.BannerAppRequest;
import com.taisf.services.base.entity.BannerAppEntity;
import com.taisf.services.base.vo.BannerAppTreeNodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BannerAppManagerImpl {

	@Autowired
	private BannerAppDao bannerAppDao;
	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:根据ID查询
	 **/
	public BannerAppEntity getBannerAppById(Integer id){
		return bannerAppDao.getBannerAppById(id);
	}



	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:保存
	 **/
	public int saveBannerApp(BannerAppEntity  entity){
		return bannerAppDao.saveBannerApp(entity);
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:更新
	 **/
	public int updateBannerApp(BannerAppEntity entity){
		return bannerAppDao.updateBannerApp(entity);
	}


	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:查询所有
	 **/
	public List<BannerAppTreeNodeVO> getBannerAppList(BannerAppRequest request){
		return bannerAppDao.getBannerAppList(request);
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2018/7/3
	 * @description:分页查询
	 **/
	public PagingResult<BannerAppEntity> getBannerAppForPage(BannerAppRequest request){
		return bannerAppDao.getBannerAppForPage(request);
	}

	public BannerAppEntity getBannerAppByAppCode(String appCode){
		return bannerAppDao.getBannerAppByAppCode(appCode);
	}
}
