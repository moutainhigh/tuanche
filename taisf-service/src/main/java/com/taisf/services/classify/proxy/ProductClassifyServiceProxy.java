package com.taisf.services.classify.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.classify.api.ProductClassifyService;
import com.taisf.services.classify.entity.ProductClassifyEntity;
import com.taisf.services.classify.manager.ProductClassifyManagerImpl;
import com.taisf.services.classify.req.ProductClassifyListRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:zhangzhengguang
 * @date:2018/9/19
 * @description:
 **/
@Component("productClassifyServiceProxy")
public class ProductClassifyServiceProxy implements ProductClassifyService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClassifyServiceProxy.class);

    @Resource(name = "productClassifyManagerImpl")
    private ProductClassifyManagerImpl supplierWindowManagerImpl;


    @Override
    public DataTransferObject<Void> saveProductClassify(ProductClassifyEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            supplierWindowManagerImpl.saveProductClassify(entity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "saveProductClassify error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("保存失敗");
            return dto;
        }
        return dto;
    }

    @Override
    public DataTransferObject<ProductClassifyEntity> getProductClassifyById(Integer id) {
        DataTransferObject<ProductClassifyEntity> dto = new DataTransferObject();
        try {
            ProductClassifyEntity supplierWindowEntity = supplierWindowManagerImpl.getProductClassifyById(id);
            dto.setData(supplierWindowEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getProductClassifyById error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询失敗");
            return dto;
        }
        return dto;
    }

    @Override
    public DataTransferObject<Void> updateProductClassify(ProductClassifyEntity entity) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            supplierWindowManagerImpl.updateProductClassify(entity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "updateProductClassify error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("更新失敗");
            return dto;
        }
        return dto;
    }

    @Override
    public DataTransferObject<Void> deleteProductClassify(Integer id) {
        DataTransferObject<Void> dto = new DataTransferObject();
        try {
            supplierWindowManagerImpl.deleteProductClassify(id);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "deleteProductClassify error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("删除失敗");
            return dto;
        }
        return dto;
    }

    @Override
    public DataTransferObject<PagingResult<ProductClassifyEntity>> pageListProductClassify(ProductClassifyListRequest request) {
        DataTransferObject<PagingResult<ProductClassifyEntity>> dto = new DataTransferObject();
        try {
            PagingResult<ProductClassifyEntity> result = supplierWindowManagerImpl.pageListProductClassify(request);
            dto.setData(result);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "pageListProductClassify error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("分页查询失敗");
            return dto;
        }
        return dto;
    }

    @Override
    public DataTransferObject<List<ProductClassifyEntity>> findListProductClassify(ProductClassifyListRequest request) {
        DataTransferObject<List<ProductClassifyEntity>> dto = new DataTransferObject();
        try {
            List<ProductClassifyEntity> listProductClassify = supplierWindowManagerImpl.findListProductClassify(request);
            dto.setData(listProductClassify);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "findListProductClassify error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("查询所有失敗");
            return dto;
        }
        return dto;
    }
}
