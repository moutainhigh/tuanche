package com.taisf.services.base.proxy;

import com.taisf.services.base.entity.EmployeeSupplierEntity;
import com.taisf.services.base.service.EmployeeSupplierService;
import com.taisf.services.supplier.dao.EmployeeSupplierDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("base.employeeSupplierServiceProxy")
public class EmployeeSupplierServiceProxy implements EmployeeSupplierService {

	@Resource(name = "supplier.employeeSupplierDao")
	private EmployeeSupplierDao employeeSupplierDao;


    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:根据userId查询供餐商code
     **/
    public EmployeeSupplierEntity getByUserId(String userId){
        return employeeSupplierDao.getByUserId(userId);
    }

    public int saveEmployeeSupplier(EmployeeSupplierEntity entity){
        return employeeSupplierDao.saveEmployeeSupplier(entity);
    }


}
