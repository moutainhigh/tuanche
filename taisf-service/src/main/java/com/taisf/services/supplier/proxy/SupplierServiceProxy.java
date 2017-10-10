package com.taisf.services.supplier.proxy;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.supplier.api.SupplierService;
import com.taisf.services.supplier.entity.SupplierEntity;
import com.taisf.services.supplier.manager.SupplierManagerImpl;

@Component("supplier.supplierServiceProxy")
public class SupplierServiceProxy implements SupplierService{
	
	@Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;

	 /**
     * 获取供应商列表
     */
	@Override
	public DataTransferObject<List<SupplierEntity>> getAllSupplierList() {
		DataTransferObject<List<SupplierEntity>> dto = new DataTransferObject<List<SupplierEntity>>();
		List<SupplierEntity> supplierList = supplierManager.getAllSupplierList();
		dto.setData(supplierList);
        return dto;
	}

}
