package com.taisf.services.product.proxy;

import com.taisf.services.product.manager.ProductManagerImpl;
import com.taisf.services.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>获取版本更新信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/27.
 * @version 1.0
 * @since 1.0
 */
@Component("basedata.productServiceProxy")
public class ProductServiceProxy implements ProductService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceProxy.class);

    @Resource(name = "basedata.productManagerImpl")
    private ProductManagerImpl productManager;




}
