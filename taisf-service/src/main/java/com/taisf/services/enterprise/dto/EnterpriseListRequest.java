package com.taisf.services.enterprise.dto;

import java.util.Date;

import com.jk.framework.base.page.PageRequest;

public class EnterpriseListRequest extends PageRequest {

	private static final long serialVersionUID = -8308160586793003064L;

	private String enterpriseName;

	private Date openTime;

	private Date tillTime;

	private Integer enterpriseType;
	
	private String manger;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getTillTime() {
		return tillTime;
	}

	public void setTillTime(Date tillTime) {
		this.tillTime = tillTime;
	}

	public Integer getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(Integer enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getManger() {
		return manger;
	}

	public void setManger(String manger) {
		this.manger = manger;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
