package com.taisf.services.order.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.util.MoneyDealUtil;
import com.taisf.services.common.valenum.SupplierProductTypeEnum;
import com.taisf.services.order.api.CartService;
import com.taisf.services.order.dto.CartAddRequest;
import com.taisf.services.order.dto.CartBaseRequest;
import com.taisf.services.order.entity.CartEntity;
import com.taisf.services.order.manager.CartManagerImpl;
import com.taisf.services.order.vo.CartEleVO;
import com.taisf.services.order.vo.CartVO;
import com.taisf.services.order.vo.CartInfoVO;
import com.taisf.services.product.entity.ProductEntity;
import com.taisf.services.product.manager.ProductManagerImpl;
import com.taisf.services.supplier.entity.SupplierPackageEntity;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
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
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/28.
 * @version 1.0
 * @since 1.0
 */
@Component("order.cartServiceProxy")
public class CartServiceProxy implements CartService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceProxy.class);

    @Resource(name = "order.cartManagerImpl")
    private CartManagerImpl cartManager;

    @Resource(name = "product.productManagerImpl")
    private ProductManagerImpl productManager;


    @Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierProductManager;


    /**
     * 获取购物车列表信息
     * @param businessUid
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<Void> cartClean(String userUid, String businessUid){
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNStr(businessUid)
                || Check.NuNStr(userUid)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        cartManager.delUserCart(userUid,businessUid);
        return dto;
    }





    /**
     * 获取购物车列表信息
     * @param businessUid
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<CartInfoVO> cartInfo(String userUid, String businessUid){
        DataTransferObject<CartInfoVO> dto = new DataTransferObject<>();

        if (Check.NuNStr(businessUid)
                || Check.NuNStr(userUid)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        CartInfoVO vo = new CartInfoVO();
        vo.setBusinessUid(businessUid);
        vo.setUserUid(userUid);

        List<CartEntity> list = cartManager.getCartByUserId(userUid,businessUid);
        if (list == null){
            list = new ArrayList<>();
        }
        dto.setData(vo);
        //礼包列表
        List<CartEleVO> proList = new ArrayList<>();
        //药品列表
        List<CartEleVO> packageList = new ArrayList<>();
        for (CartEntity cartEntity : list) {
            //获取购物车类型
            SupplierProductTypeEnum supplierProductTypeEnum = SupplierProductTypeEnum.getByCode(cartEntity.getSupplierProductType());
            if(supplierProductTypeEnum.getCode() == supplierProductTypeEnum.PACKAGE.getCode() ){
                CartEleVO ele = new CartEleVO();
                ele.setProductCode(cartEntity.getProductCode());
                ele.setProductNum(cartEntity.getProductNum());
                packageList.add(ele);
            }else if(supplierProductTypeEnum.getCode() == supplierProductTypeEnum.PRODUCT.getCode() ){
                CartEleVO ele = new CartEleVO();
                ele.setProductCode(cartEntity.getProductCode());
                ele.setProductNum(cartEntity.getProductNum());
                proList.add(ele);
            }
        }
        if (!Check.NuNCollection(packageList)){
            //处理礼包
            dealCartPackage(dto,packageList);
        }
        if (!Check.NuNCollection(proList)){
            //处理商品
            dealCartProduct(dto,proList);
        }
        dto.setData(vo);
        return dto;
    }

    /**
     * 填充礼包的逻辑
     * @param dto
     * @param packageList
     */
    private void dealCartPackage(DataTransferObject<CartInfoVO> dto,List<CartEleVO> packageList){
        if (!dto.checkSuccess()){
            return;
        }
        if (Check.NuNCollection(packageList)){
            return;
        }
        Map<String,Integer> numMap = new HashMap<>();
        List<Integer> pList = new ArrayList<>();
        for (CartEleVO eleVO : packageList) {
            pList.add(eleVO.getProductCode());
            numMap.put(eleVO.getProductCode()+"",eleVO.getProductNum());
        }
        List<SupplierPackageEntity> list = supplierProductManager.getSupplierPackageByList(pList);
        if (Check.NuNCollection(list)){
            return;
        }
        CartInfoVO vo = dto.getData();
        for (SupplierPackageEntity productEntity : list) {
            int num = ValueUtil.getintValue(numMap.get(productEntity.getId()+""));
            CartVO cartVO = new CartVO();
            cartVO.setProductName(productEntity.getTitle());
            cartVO.setBusinessUid(vo.getBusinessUid());
            cartVO.setUserUid(vo.getUserUid());
            cartVO.setProductCode(productEntity.getId());
            cartVO.setProductPrice(productEntity.getPackagePrice());
            cartVO.setProductNum(num);
            vo.getList().add(cartVO);
            vo.setPrice(MoneyDealUtil.overlayMoney(vo.getPrice(),productEntity.getPackagePrice(),num));
        }
    }



    /**
     * 填充普通药品逻辑
     * @param dto
     * @param proList
     */
    private void dealCartProduct(DataTransferObject<CartInfoVO> dto,List<CartEleVO> proList){
        if (!dto.checkSuccess()){
            return;
        }
        if (Check.NuNCollection(proList)){
            return;
        }
        Map<String,Integer> numMap = new HashMap<>();
        List<Integer> pList = new ArrayList<>();
        for (CartEleVO eleVO : proList) {
            pList.add(eleVO.getProductCode());
            numMap.put(eleVO.getProductCode()+"",eleVO.getProductNum());
        }
        List<ProductEntity> list = productManager.getProductByList(pList);
        if (Check.NuNCollection(list)){
            return;
        }
        CartInfoVO vo = dto.getData();
        for (ProductEntity productEntity : list) {
            int num = ValueUtil.getintValue(numMap.get(productEntity.getId()+""));
            CartVO cartVO = new CartVO();
            cartVO.setProductName(productEntity.getProductName());
            cartVO.setBusinessUid(vo.getBusinessUid());
            cartVO.setUserUid(vo.getUserUid());
            cartVO.setProductCode(productEntity.getId());
            cartVO.setProductPrice(productEntity.getPriceSale());
            cartVO.setProductNum(num);
            vo.getList().add(cartVO);
            vo.setPrice(MoneyDealUtil.overlayMoney(vo.getPrice(), productEntity.getPriceSale(), num));
        }
    }




    /**
     * 添加购物车
     * @param cartAddRequest
     * @return
     */
    @Override
    public DataTransferObject<Void> addCart(CartAddRequest cartAddRequest) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        //1. 校验基本参数
        this.checkCartBasePar(dto,cartAddRequest);
        if (!dto.checkSuccess()){
            return dto;
        }
        if (Check.NuNObj(cartAddRequest.getProductNum())){
            cartAddRequest.setProductNum(1);
        }
        CartEntity has = cartManager.getCartByProduct(cartAddRequest);
        if (Check.NuNObj(has)){
            CartEntity record = new CartEntity();
            BeanUtils.copyProperties(cartAddRequest,record);
            //当前购物车中不存在
            cartManager.saveCart(record);
        }else {
            //当前购物车中已经存在
            has.setProductNum(has.getProductNum() + cartAddRequest.getProductNum());
            cartManager.saveCart(has);
        }
        return dto;
    }

    /**
     * 移除购物车
     * @param cartBaseRequest
     * @return
     */
    @Override
    public DataTransferObject<Void> delCart(CartBaseRequest cartBaseRequest) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        //1. 校验基本参数
        this.checkCartBasePar(dto,cartBaseRequest);
        if (!dto.checkSuccess()){
            return dto;
        }

        CartEntity has = cartManager.getCartByProduct(cartBaseRequest);
        if (Check.NuNObj(has)){
            dto.setErrorMsg("当前商品不存在,请勿重复删除");
            return dto;
        }else {
            cartManager.delCart(has.getId());
        }
        return dto;
    }

    /**
     * 校验当前购物的基本参数信息
     * @param dto
     * @param cartBaseRequest
     */
    private void checkCartBasePar(DataTransferObject dto,CartBaseRequest cartBaseRequest){
        if (Check.NuNObj(cartBaseRequest)){
            dto.setErrorMsg("参数异常");
        }
        if (Check.NuNObjs(cartBaseRequest.getBusinessUid(),
                cartBaseRequest.getBusinessUid(),
                cartBaseRequest.getProductCode(),
                cartBaseRequest.getSupplierProductType())){
            dto.setErrorMsg("参数为空");
        }
    }


}
