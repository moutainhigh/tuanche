package com.taisf.services.user.entity;

import com.jk.framework.base.entity.BaseEntity;

import java.util.Date;

public class UserEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2378420290094051498L;

	private Integer id;

	/**
	 * 手机号
	 */
	private String userPhone;

	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 用户状态 1：可用 2：禁用
	 */
	private Integer userStatus;

	/**
	 * 用户类型 1：医生 2：医药代表
	 */
	private Integer userType;

	/**
	 * 用户状态： 1:注册成功 2：认证审核中 3：认证通过
	 */
	private Integer userBusinessStatus;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword == null ? null : userPassword.trim();
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserBusinessStatus() {
		return userBusinessStatus;
	}

	public void setUserBusinessStatus(Integer userBusinessStatus) {
		this.userBusinessStatus = userBusinessStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}