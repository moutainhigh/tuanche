package com.taisf.services.message.dto;

import com.jk.framework.base.entity.BaseEntity;

/**
 * Created by JK on 2017/8/23.
 */
public class BaseRequest extends BaseEntity {
    private Integer id;
    /**
     * 患者ID
     */
    private String customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
