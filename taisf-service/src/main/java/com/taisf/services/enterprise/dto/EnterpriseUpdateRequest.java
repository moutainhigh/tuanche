package com.taisf.services.enterprise.dto;

import com.taisf.services.enterprise.entity.EnterpriseEntity;

public class EnterpriseUpdateRequest extends EnterpriseEntity{

	private static final long serialVersionUID = 8601157678495331557L;
	
	/**
	 * 操作类型
	 */
	private Integer operateType;
	
	/**
     * 员工餐价格
     */
    private Integer empPrice;

    /**
     * 老板餐价格
     */
    private Integer bossPrice;

    /**
     * 是否午餐
     */
    private Integer forLunch;

    /**
     * 午餐开始时间
     */
    private String lunchStart;

    /**
     * 午餐结束时间
     */
    private String lunchEnd;

    /**
     * 是否晚餐
     */
    private Integer forDinner;

    /**
     * 晚餐开始时间
     */
    private String dinnerStart;

    /**
     * 晚餐开始时间
     */
    private String dinnerEnd;
    
    /**
     * 送餐地址
     */
    private String address;
    
    /**
     * 其他送餐地址
     */
    private String otherAddress;

	public Integer getEmpPrice() {
		return empPrice;
	}

	public void setEmpPrice(Integer empPrice) {
		this.empPrice = empPrice;
	}

	public Integer getBossPrice() {
		return bossPrice;
	}

	public void setBossPrice(Integer bossPrice) {
		this.bossPrice = bossPrice;
	}

	public Integer getForLunch() {
		return forLunch;
	}

	public void setForLunch(Integer forLunch) {
		this.forLunch = forLunch;
	}

	public String getLunchStart() {
		return lunchStart;
	}

	public void setLunchStart(String lunchStart) {
		this.lunchStart = lunchStart;
	}

	public String getLunchEnd() {
		return lunchEnd;
	}

	public void setLunchEnd(String lunchEnd) {
		this.lunchEnd = lunchEnd;
	}

	public Integer getForDinner() {
		return forDinner;
	}

	public void setForDinner(Integer forDinner) {
		this.forDinner = forDinner;
	}

	public String getDinnerStart() {
		return dinnerStart;
	}

	public void setDinnerStart(String dinnerStart) {
		this.dinnerStart = dinnerStart;
	}

	public String getDinnerEnd() {
		return dinnerEnd;
	}

	public void setDinnerEnd(String dinnerEnd) {
		this.dinnerEnd = dinnerEnd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOtherAddress() {
		return otherAddress;
	}

	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

}
