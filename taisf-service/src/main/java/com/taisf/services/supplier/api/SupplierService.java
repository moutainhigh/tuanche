package com.taisf.services.supplier.api;

import java.util.List;

import com.jk.framework.base.entity.DataTransferObject;
import com.taisf.services.supplier.entity.SupplierEntity;

public interface SupplierService {
	
	/**
	 * 获取供应商列表
	 * @return
	 */
	DataTransferObject<List<SupplierEntity>> getAllSupplierList();

}
