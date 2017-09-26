package com.taisf.services.product.manager;

import com.taisf.services.product.dao.ProductDao;
import com.taisf.services.product.entity.ProductEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>商品信息</p>
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
@Service("basedata.productManagerImpl")
public class ProductManagerImpl {

	@Resource(name = "product.productDao")
	private ProductDao productDao;

	/**
	 * 根据id获取当前的菜单list
	 * @author afi
	 * @param list
	 * @return
	 */
	public List<ProductEntity> getProductByList(List<Integer> list){
		//获取商品列表
		return productDao.getProductByList(list);
	}

}
