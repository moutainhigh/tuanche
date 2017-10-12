package com.taisf.services.supplier.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.valenum.ProductClassifyEnum;
import com.taisf.services.common.valenum.SupplierProductTypeEnum;
import com.taisf.services.product.entity.ProductEntity;
import com.taisf.services.supplier.dto.SupplierProductRequest;
import com.taisf.services.supplier.entity.SupplierPackageEntity;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import com.taisf.services.supplier.api.SupplierProductService;
import com.taisf.services.supplier.vo.ProductClassifyInfo;
import com.taisf.services.supplier.vo.ProductClassifyVO;
import com.taisf.services.supplier.vo.SupplierProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Component("supplier.supplierProductServiceProxy")
public class SupplierProductServiceProxy implements SupplierProductService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierProductServiceProxy.class);

    @Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;



    /**
     * 获取当前的列表信息
     * @param supplierCode
     * @return
     */
    public DataTransferObject<List<ProductClassifyInfo>> getSupplierClassifyProduct(String supplierCode){

        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(supplierCode)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        Map<String,List<SupplierProductVO>>  map =  this.getSupplierProductMap(supplierCode);
        List<ProductClassifyVO> list = new ArrayList<>();
        try {
            //便利当前的枚举信息
            for (ProductClassifyEnum c : ProductClassifyEnum.values()) {
                if (c.getSupplierProductTypeEnum().getCode() == SupplierProductTypeEnum.PRODUCT.getCode()){
                    dealProduct(map, list, c);
                }if (c.getSupplierProductTypeEnum().getCode() == SupplierProductTypeEnum.PACKAGE.getCode()){
                    List<SupplierProductVO> tmp=  this.dealPackage(supplierCode);
                    ProductClassifyInfo vo = new ProductClassifyInfo();
                    String key = c.getCode()+"";
                    vo.setProductClassify(key);
                    vo.setProductClassifyName(c.getName());
                    if (!Check.NuNCollection(tmp)){
                        vo.setList(tmp);
                    }
                    list.add(vo);
                }

            }
            dto.setData(list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【获取列表和商品信息】par:{},error:{}", JsonEntityTransform.Object2Json(supplierCode),e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取分类信息失败");
            return dto;
        }
        return dto;
    }

    /**
     * 处理商品列表
     * @param map
     * @param list
     * @param c
     */
    private void dealProduct(Map<String, List<SupplierProductVO>> map, List<ProductClassifyVO> list, ProductClassifyEnum c) {
        if (Check.NuNObjs(map,list,c)){
            return;
        }
        ProductClassifyInfo vo = new ProductClassifyInfo();
        String key = c.getCode()+"";
        vo.setProductClassify(key);
        vo.setProductClassifyName(c.getName());
        List<SupplierProductVO> tmp = map.get(key);
        if (!Check.NuNCollection(tmp)){
            vo.setList(tmp);
        }
        list.add(vo);
    }


    /**
     * 处理包
     * @param supplierCode
     */
    private List<SupplierProductVO> dealPackage(String supplierCode) {
        if (Check.NuNStr(supplierCode)){
            return null;
        }

        List<SupplierPackageEntity> list = supplierManager.getSupplierPackageByCode(supplierCode);
        if (Check.NuNCollection(list)){
            list = new ArrayList<>();
        }
        List<SupplierProductVO> voList = new ArrayList<>();
        for (SupplierPackageEntity packageEntity : list) {
            SupplierProductVO vo = new SupplierProductVO();
            vo.setId(packageEntity.getId());
            vo.setSupplierProductType(SupplierProductTypeEnum.PACKAGE.getCode());
            vo.setPriceSale(packageEntity.getPackagePrice());
            vo.setProductName(packageEntity.getTitle());
            vo.setProductPic(packageEntity.getPackagePic());
            voList.add(vo);
        }
        return voList;
    }


    /**
     * 获取当前的列表信息
     * @param supplierCode
     * @return
     */
    public DataTransferObject<List<ProductClassifyVO>> getSupplierProductClassify(String supplierCode){
        DataTransferObject dto = new DataTransferObject();
        List<ProductClassifyVO> list = new ArrayList<>();
        try {
            //便利当前的枚举信息
            for (ProductClassifyEnum c : ProductClassifyEnum.values()) {
                ProductClassifyVO vo = new ProductClassifyVO();
                vo.setProductClassify(c.getCode()+"");
                vo.setProductClassifyName(c.getName());
                list.add(vo);
            }
            dto.setData(list);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【获取配置信息】par:{},error:{}", JsonEntityTransform.Object2Json(supplierCode),e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取分类信息失败");
            return dto;
        }
        return dto;
    }

    /**
     * 获取当前供应商的商品了列表
     * @author  afi
     * @param supplierProductRequest
     * @return
     */
    @Override
    public DataTransferObject<List<SupplierProductVO>> getSupplierProductList(SupplierProductRequest supplierProductRequest){
        DataTransferObject dto = new DataTransferObject();
        try {

            ProductClassifyEnum productClassifyEnum = ProductClassifyEnum.getByCode(supplierProductRequest.getProductClassify());
            if (Check.NuNObj(productClassifyEnum)){
                dto.setErrorMsg("异常的商品分类");
                return dto;
            }
            SupplierProductTypeEnum supplierProductTypeEnum = productClassifyEnum.getSupplierProductTypeEnum();
            if (supplierProductTypeEnum.getCode() == SupplierProductTypeEnum.PRODUCT.getCode()){
                //处理普通的商品信息
                dealSupplierProduct(dto,supplierProductRequest);
            }else if (supplierProductTypeEnum.getCode() == SupplierProductTypeEnum.PACKAGE.getCode()){
                //处理打包推荐列表
                dealSupplierPackage(dto,supplierProductRequest);
            }else {
                dto.setErrorMsg("异常的分类");
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【获取商品列表】par:{},error:{}", JsonEntityTransform.Object2Json(supplierProductRequest),e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("获取商品列表失败");
            return dto;
        }
        return dto;
    }

    /**
     * 处理当前供应商的推荐商品信息
     * @author afi
     * @param dto
     * @param supplierProductRequest
     */
    private void dealSupplierPackage(DataTransferObject<List<SupplierProductVO>>  dto, SupplierProductRequest supplierProductRequest) {
        if (!dto.checkSuccess()){
            return;
        }
        List<SupplierPackageEntity> list = supplierManager.getSupplierPackageByCode(supplierProductRequest.getSupplierCode());
        if (Check.NuNCollection(list)){
            list = new ArrayList<>();
        }
        List<SupplierProductVO> voList = new ArrayList<>();
        for (SupplierPackageEntity packageEntity : list) {
            SupplierProductVO vo = new SupplierProductVO();
            vo.setId(packageEntity.getId());
            vo.setSupplierProductType(SupplierProductTypeEnum.PACKAGE.getCode());
            vo.setPriceSale(packageEntity.getPackagePrice());
            vo.setProductName(packageEntity.getTitle());
            vo.setProductPic(packageEntity.getPackagePic());
            voList.add(vo);
        }
        dto.setData(voList);
    }


    /**
     * 处理当前的普通商品列表
     * @param dto
     * @param supplierProductRequest
     */
    private void dealSupplierProduct(DataTransferObject<List<SupplierProductVO>>  dto, SupplierProductRequest supplierProductRequest) {
        if (!dto.checkSuccess()){
            return;
        }
        List<ProductEntity> list = supplierManager.getProductListBySupplierAndType(supplierProductRequest);
        if (Check.NuNCollection(list)){
            list = new ArrayList<>();
        }
        List<SupplierProductVO> voList = this.transProductList2VO(list,SupplierProductTypeEnum.PRODUCT);
        dto.setData(voList);
    }

    /**
     * 获取当前的分类
     * @author afi
     * @param supplierCode
     * @return
     */
    private Map<String,List<SupplierProductVO>>  getSupplierProductMap(String supplierCode){
        Map<String,List<SupplierProductVO>> rst = new HashMap<>();

        Map<String,List<ProductEntity>> map = new HashMap<>();
        SupplierProductRequest request =new SupplierProductRequest();
        request.setSupplierCode(supplierCode);
        List<ProductEntity> list = supplierManager.getProductListBySupplierAndType(request);
        if (Check.NuNCollection(list)){
            return rst;
        }
        for (ProductEntity productEntity : list) {
           Integer productClassify = productEntity.getProductClassify();
           if (Check.NuNObj(productClassify)){
               //直接过滤掉异常数据
               continue;
           }
           String key = productClassify +"";
           if (map.containsKey(key)){
               map.get(key).add(productEntity);
           }else {
               List<ProductEntity> tmp = new ArrayList<>();
               tmp.add(productEntity);
               map.put(key,tmp);
           }
        }
        for (String key : map.keySet()) {
            List<ProductEntity> proList = map.get(key);
            List<SupplierProductVO> voList = this.transProductList2VO(proList,SupplierProductTypeEnum.PRODUCT);
            rst.put(key,voList);
        }

        return rst;
    }

    /**
     * 转化当前的商品为对外展示的商品列表信息
     * @author afi
     * @param list
     * @param supplierProductTypeEnum
     * @return
     */
    private List<SupplierProductVO> transProductList2VO(List<ProductEntity> list,SupplierProductTypeEnum supplierProductTypeEnum){
        List<SupplierProductVO> voList = new ArrayList<>();
        if (Check.NuNCollection(list)){
            return voList;
        }
        if (Check.NuNObj(supplierProductTypeEnum)){
            supplierProductTypeEnum = SupplierProductTypeEnum.PRODUCT;
        }
        for (ProductEntity entity : list) {
            SupplierProductVO supplier = new SupplierProductVO();
            BeanUtils.copyProperties(entity,supplier);
            supplier.setSupplierProductType(supplierProductTypeEnum.getCode());
            voList.add(supplier);
        }
        return voList;

    }

}
