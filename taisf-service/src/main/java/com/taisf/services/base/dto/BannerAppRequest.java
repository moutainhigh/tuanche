package com.taisf.services.base.dto;

import com.jk.framework.base.page.PageRequest;


public class BannerAppRequest extends PageRequest {
	private static final long serialVersionUID = 1160933516615494479L;

	private String appCode;

	private String appName;

	private Integer isDel;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
