package com.taisf.services.base.vo;

import com.jk.framework.base.entity.BaseEntity;

public class BannerVo extends BaseEntity{

	/**
	 * @Field @serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
     * 标题
     */
    private String title;
    /**
     * 排序
     */
    private Integer rank;
    /**
     * 标的物URL
     */
    private String url;
    /**
     * 素材图片url
     */
    private String imageUrl;
    /**
     * 跳转类型 1 原生 2 h5
     */
    private Integer jumpType;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getJumpType() {
		return jumpType;
	}
	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}

}
