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
	 * 用户id
	 */
	private String userUid;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 员工号
	 */
	private String userCode;

	/**
	 * 企业编码
	 */
	private String enterpriseCode;

	/**
	 * 企业名称
	 */
	private String enterpriseName;


	/**
	 * 手机号
	 */
	private String userPhone;

	/**
	 * 密码
	 */
	private transient String userPassword;

	/**
	 * 用户状态 1：可用 2：禁用
	 */
	private Integer userStatus;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/**
	 * 用户角色
	 */
	private Integer userRole;


	/**
	 * 餐属性
	 */
	private Integer productSource;



	/**
	 * 用户状态： 1:注册成功 2：认证审核中 3：认证通过
	 */
	private Integer userBusinessStatus;

	private Date createTime;

	private Date updateTime;
	private Integer amount;
	private Integer accountStatus;

	private String QrCode;

	public String getQrCode() {
		return QrCode;
	}

	public void setQrCode(String qrCode) {
		QrCode = qrCode;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEnterpriseCode() {
		return enterpriseCode;
	}

	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public Integer getProductSource() {
		return productSource;
	}

	public void setProductSource(Integer productSource) {
		this.productSource = productSource;
	}


}