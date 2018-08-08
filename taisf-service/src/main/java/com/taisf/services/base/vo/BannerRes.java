package com.taisf.services.base.vo;

import com.jk.framework.base.entity.BaseEntity;

import java.util.List;

public class BannerRes extends BaseEntity{

	/**
	 * @Field @serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<BannerVo> bannerList;
	
	private String positionCode;

	public List<BannerVo> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<BannerVo> bannerList) {
		this.bannerList = bannerList;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	} 

}
