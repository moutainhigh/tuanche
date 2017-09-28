package com.taisf.services.supplier.manager;

import com.taisf.services.product.entity.ProductEntity;
import com.taisf.services.supplier.dao.SupplierPackageDao;
import com.taisf.services.supplier.dao.SupplierProductDao;
import com.taisf.services.supplier.dto.SupplierProductRequest;
import com.taisf.services.supplier.entity.SupplierPackageEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>供应商的商品信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/23.
 * @version 1.0
 * @since 1.0
 */
@Service("basedata.supplierProductManagerImpl")
public class SupplierProductManagerImpl {

	@Resource(name = "supplier.supplierProductDao")
	private SupplierProductDao supplierProductDao;


	@Resource(name = "supplier.supplierPackageDao")
	private SupplierPackageDao supplierPackageDao;




	/**
	 * 根据供应商获取其菜单信息
	 * @author afi
	 * @param supplierProductRequest
	 * @return
	 */
	public List<ProductEntity> getProductListBySupplierAndType(SupplierProductRequest supplierProductRequest){
		//获取商品列表
		return supplierProductDao.getProductListBySupplierAndType(supplierProductRequest);
	}


	/**
	 * 获取当前的供应商打包信息
	 * @author afi
	 * @param supplierCode
	 * @return
	 */
	public List<SupplierPackageEntity> getSupplierPackageByCode(String supplierCode){
		//获取当前的推荐列表
		return supplierPackageDao.getSupplierPackageByCode(supplierCode);
	}





}
