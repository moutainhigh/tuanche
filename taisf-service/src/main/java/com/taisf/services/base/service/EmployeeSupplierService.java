package com.taisf.services.base.service;

import com.taisf.services.base.entity.EmployeeSupplierEntity;

public interface EmployeeSupplierService {

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:根据userId查询供餐商code
     **/
    EmployeeSupplierEntity getByUserId(String userId);

    int saveEmployeeSupplier(EmployeeSupplierEntity entity);
}
