package com.taisf.services.base.dto;

import com.jk.framework.base.page.PageRequest;


public class BannerPositionRequest extends PageRequest {
	private static final long serialVersionUID = 1160933516615494479L;

	private  String appCode;
	private  String positionCode;

	private  String postionName;

	private  Integer isDel;

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPostionName() {
		return postionName;
	}

	public void setPostionName(String postionName) {
		this.postionName = postionName;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
}
