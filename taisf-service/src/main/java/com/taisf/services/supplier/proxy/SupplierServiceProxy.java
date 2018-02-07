package com.taisf.services.supplier.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.taisf.services.supplier.api.SupplierService;
import com.taisf.services.supplier.dao.SupplierDao;
import com.taisf.services.supplier.dto.SupplierRequest;
import com.taisf.services.supplier.entity.SupplierEntity;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("supplier.supplierServiceProxy")
public class SupplierServiceProxy implements SupplierService{
	
	@Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;

	@Autowired
    private SupplierDao supplierDao;

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

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/14
	 * @description:当前销售维护的供应商列表
	 **/
	@Override
	public DataTransferObject<PagingResult<SupplierEntity>> supplierPageList(SupplierRequest request) {
		DataTransferObject<PagingResult<SupplierEntity>> dto = new DataTransferObject<>();
		PagingResult<SupplierEntity> pagingResult = supplierDao.supplierPageList(request);
		dto.setData(pagingResult);
        return dto;
	}


	/**
	 * 获取供应商信息
	 * @param supplierCode
	 * @return
	 */
	@Override
	public DataTransferObject<SupplierEntity> getSupplierInfo(String  supplierCode) {
		DataTransferObject<SupplierEntity> dto = new DataTransferObject<>();
		SupplierEntity supplierEntity = supplierDao.getSupplierByCode(supplierCode);
		if (Check.NuNObj(supplierEntity)){
			dto.setErrorMsg("当前企业不存在");
			return dto;
		}
		dto.setData(supplierEntity);
		return dto;
	}

}
