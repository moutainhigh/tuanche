package com.taisf.services.base.dto;

import com.jk.framework.base.page.PageRequest;

import java.util.List;

public class BannerReq extends PageRequest{

	/**
	 * @Field @serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @Field @position : 广告位所在位置  1 首页 2 商城 3 食物库 4 专家咨询
	 */
	private Integer position;
	
	/**
	 * @Field @platform : 系统平台 1 iOS 2 Android 3 微信
	 */
	private Integer platform;

	private  String appCode;
	
	private Integer state;
	
	private String title;
	
	private String positionCode;
	private List<String> positionList;
	
	private Integer source;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public List<String> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<String> positionList) {
		this.positionList = positionList;
	}

}
