package com.taisf.services.enterprise.vo;

import com.jk.framework.base.entity.BaseEntity;
import com.taisf.services.enterprise.entity.EnterpriseAddressEntity;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.entity.EnterpriseContactsEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.entity.EnterpriseFinanceEntity;

public class EnterpriseVO extends BaseEntity {

	private static final long serialVersionUID = -1668510788153905796L;

	/**
     * 基本信息
     */
    private EnterpriseEntity enterpriseEntity;

    /**
     * 配置信息
     */
    private EnterpriseConfigEntity configEntity;
    
    private EnterpriseContactsEntity contactsEntity;
    
    private EnterpriseAddressEntity addressEntity;
    
    private EnterpriseFinanceEntity financeEntity;

	public EnterpriseEntity getEnterpriseEntity() {
		return enterpriseEntity;
	}

	public void setEnterpriseEntity(EnterpriseEntity enterpriseEntity) {
		this.enterpriseEntity = enterpriseEntity;
	}

	public EnterpriseConfigEntity getConfigEntity() {
		return configEntity;
	}

	public void setConfigEntity(EnterpriseConfigEntity configEntity) {
		this.configEntity = configEntity;
	}

	public EnterpriseContactsEntity getContactsEntity() {
		return contactsEntity;
	}

	public void setContactsEntity(EnterpriseContactsEntity contactsEntity) {
		this.contactsEntity = contactsEntity;
	}

	public EnterpriseAddressEntity getAddressEntity() {
		return addressEntity;
	}

	public void setAddressEntity(EnterpriseAddressEntity addressEntity) {
		this.addressEntity = addressEntity;
	}

	public EnterpriseFinanceEntity getFinanceEntity() {
		return financeEntity;
	}

	public void setFinanceEntity(EnterpriseFinanceEntity financeEntity) {
		this.financeEntity = financeEntity;
	}

}
