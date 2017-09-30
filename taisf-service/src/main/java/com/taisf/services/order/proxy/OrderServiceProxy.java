package com.taisf.services.order.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.MD5Util;
import com.jk.framework.base.utils.ValueUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.taisf.services.common.util.MoneyDealUtil;
import com.taisf.services.common.valenum.AccountStatusEnum;
import com.taisf.services.common.valenum.OrdersStatusEnum;
import com.taisf.services.common.valenum.SupplierStatusEnum;
import com.taisf.services.common.valenum.UserStatusEnum;
import com.taisf.services.order.api.CartService;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.CreatOrderRequest;
import com.taisf.services.order.entity.OrderEntity;
import com.taisf.services.order.entity.OrderMoneyEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.vo.CartVO;
import com.taisf.services.order.vo.CartInfoVO;
import com.taisf.services.order.vo.InitOrderVO;
import com.taisf.services.order.vo.OrderSaveVO;
import com.taisf.services.product.manager.ProductManagerImpl;
import com.taisf.services.supplier.entity.SupplierEntity;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserAddressEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>订单相关</p>
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
@Component("order.orderServiceProxy")
public class OrderServiceProxy implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceProxy.class);


    @Resource(name = "order.cartServiceProxy")
    private CartService cartService;


    @Resource(name = "product.productManagerImpl")
    private ProductManagerImpl productManager;


    @Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;


    /**
     * 下单
     *
     * @param creatOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<String> createOrder(CreatOrderRequest creatOrderRequest){
        DataTransferObject<String> dto = new DataTransferObject<>();

        if (Check.NuNStr(creatOrderRequest.getBusinessUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //获取当前的购物车
        DataTransferObject<CartInfoVO> cartDto=cartService.cartInfo(creatOrderRequest.getUserUid(), creatOrderRequest.getBusinessUid());
        if (!cartDto.checkSuccess()){
            dto.setErrorMsg(cartDto.getMsg());
            return dto;
        }
        //获取当前的购物车信息
        CartInfoVO cartInfoVO = cartDto.getData();
        List<CartVO> cartList = cartInfoVO.getList();
        if (Check.NuNCollection(cartList)){
            dto.setErrorMsg("请添加商品");
            return dto;
        }
        OrderSaveVO orderSaveVO = new OrderSaveVO();
        //1. 填充订单的信息
        this.fillOrderInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest,true);
        //2. 下单的逻辑
        //TODO

        dto.setData(orderSaveVO.getOrderSn());
        return dto;
    }


    /**
     * 初始化订单
     *
     * @param creatOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<OrderSaveVO> initOrder(CreatOrderRequest creatOrderRequest) {
        DataTransferObject<OrderSaveVO> dto = new DataTransferObject<>();

        if (Check.NuNStr(creatOrderRequest.getBusinessUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //获取当前的购物车
        DataTransferObject<CartInfoVO> cartDto=cartService.cartInfo(creatOrderRequest.getUserUid(), creatOrderRequest.getBusinessUid());
        if (!cartDto.checkSuccess()){
            dto.setErrorMsg(cartDto.getMsg());
            return dto;
        }
        //获取当前的购物车信息
        CartInfoVO cartInfoVO = cartDto.getData();
        List<CartVO> cartList = cartInfoVO.getList();
        if (Check.NuNCollection(cartList)){
            dto.setErrorMsg("请添加商品");
            return dto;
        }
        OrderSaveVO orderSaveVO = new OrderSaveVO("");
        //填充订单的信息
        this.fillOrderInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest,false);
        dto.setData(orderSaveVO);
        return dto;
    }

    /**
     * 将当前购物车中的信息转化成订单相关的信息
     * @author afi
     * @param orderSaveVO
     * @param cartInfoVO
     */
    private void  fillOrderInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CartInfoVO cartInfoVO,CreatOrderRequest creatOrderRequest,boolean createFlag){
        if (!dto.checkSuccess()){
            return;
        }
        if (Check.NuNObjs(orderSaveVO,cartInfoVO)){
            return;
        }

        List<CartVO> cartList = cartInfoVO.getList();
        if (Check.NuNCollection(cartList)){
            return;
        }

        //1. 检查商家信息
        this.checkBusinessInfo(dto,creatOrderRequest);

        //2. 获取用户信息
        this.dealUserInfo(dto,orderSaveVO,creatOrderRequest);

        //3. 填充当前订单的收货信息
        this.dealUserAddressInfo(dto,orderSaveVO,creatOrderRequest,createFlag);

        //4. 购物车中的商品信息
        this.dealProductInfo(dto,orderSaveVO,cartInfoVO);

        //5. 处理钱信息
        this.dealMoneyInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest);

        //6. 处理账户信息
        this.dealBalanceInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest,createFlag);

    }

    /**
     * 处理当前的余额信息
     * @param dto
     * @param orderSaveVO
     * @param cartInfoVO
     */
    private void dealBalanceInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CartInfoVO cartInfoVO,CreatOrderRequest creatOrderRequest,boolean createFlag) {

        if (!dto.checkSuccess()) {
            return;
        }
        OrderMoneyEntity money =  orderSaveVO.getOrderMoney();

        UserAccountEntity accountEntity =userManager.fillAndGetAccountUser(creatOrderRequest.getUserUid());

        //校验当前的账户状态
        AccountStatusEnum accountStatusEnum = AccountStatusEnum.getTypeByCode(accountEntity.getAccountStatus());
        if (Check.NuNObj(accountStatusEnum)){
            dto.setErrorMsg("异常的账户状态");
            return;
        }
        if (!accountStatusEnum.checkOk()){
            dto.setErrorMsg(accountStatusEnum.getDesc());
            return;
        }
        //获取当前的余额
        int drawBalance = ValueUtil.getintValue(accountEntity.getDrawBalance());
        if (drawBalance < 0){
            dto.setErrorMsg("异常的账户信息");
            return;
        }
        orderSaveVO.setDrawBalance(drawBalance);

        int cost = cartInfoVO.getPrice();
        if (drawBalance >= cost){
            //全部用余额支付

            money.setPayBalance(cost);

            orderSaveVO.getOrderBase().setOrderStatus(OrdersStatusEnum.HAS_PAY.getCode());
            money.setNeedPay(0);
        }else if (drawBalance <= 0){
            //余额为空
            money.setPayBalance(0);

            orderSaveVO.getOrderBase().setOrderStatus(OrdersStatusEnum.NO_PAY.getCode());
            money.setNeedPay(cost);
        }else {
            //部分余额支付
            money.setPayBalance(drawBalance);

            orderSaveVO.getOrderBase().setOrderStatus(OrdersStatusEnum.PART_PAY.getCode());
            money.setNeedPay(cost-drawBalance);
        }


        if (!createFlag){
            //非创建订单,直接返回
            return;
        }
        if (drawBalance <= 0){
            //不需要余额支付,就不需要密码
            return;
        }
        if (Check.NuNStr(creatOrderRequest.getPwd())){
            dto.setErrorMsg("请输入交易密码");
            return;
        }
        if (!checkMD5(creatOrderRequest.getPwd(),accountEntity.getAccountPassword())){
            dto.setErrorMsg("支付密码错误");
            return;
        }
    }


    /**
     * 校验是否匹配
     * @param org
     * @param md
     * @return
     */
    private boolean checkMD5(String org,String md){
       return MD5Util.MD5Encode(org).equals(md);
    }

    /**
     * 处理当前的商品信息
     * @param dto
     * @param orderSaveVO
     * @param cartInfoVO
     */
    private void dealMoneyInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CartInfoVO cartInfoVO,CreatOrderRequest creatOrderRequest) {

        if (!dto.checkSuccess()) {
            return;
        }
        OrderMoneyEntity money =  orderSaveVO.getOrderMoney();
        money.setSumMoney(cartInfoVO.getPrice());
        money.setCarryMoney(0);
        money.setRedMoney(0);
        money.setCouponMoney(0);
        money.setDiscountMoney(0);
    }

    /**
     * 处理当前的商品信息
     * @param dto
     * @param orderSaveVO
     * @param cartInfoVO
     */
    private void dealProductInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CartInfoVO cartInfoVO) {
        if (!dto.checkSuccess()) {
            return;
        }
        List<CartVO> cartList = cartInfoVO.getList();
        if (Check.NuNCollection(cartList)){
            dto.setErrorMsg("请选择商品");
            return;
        }

        //购物车中的商品
        for (CartVO vo : cartList) {
            OrderProductEntity product = new OrderProductEntity();
            product.setOrderSn(orderSaveVO.getOrderSn());
            product.setProductCode(vo.getProductCode());
            product.setProductType(vo.getSupplierProductType());
            product.setProductPrice(vo.getProductPrice());
            product.setProductNum(vo.getProductNum());
            //添加商品信息
            orderSaveVO.getList().add(product);
        }

    }


    /**
     * 处理当前的的下单的收货地址信息
     * @param dto
     * @param orderSaveVO
     * @param creatOrderRequest
     */
    private void dealUserAddressInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CreatOrderRequest creatOrderRequest,boolean createFlag){
        if (!dto.checkSuccess()){
            return;
        }

        if (createFlag && Check.NuNStr(creatOrderRequest.getAddressFid())){
            dto.setErrorMsg("请选择收货地址");
            return;
        }
        if (Check.NuNStr(creatOrderRequest.getAddressFid())){
            return;
        }
        //获取收货地址信息
        UserAddressEntity address = userManager.getUserAddressByFid(creatOrderRequest.getAddressFid());
        if (Check.NuNObj(address)){
            dto.setErrorMsg("当前收货地址不存在");
            return;
        }
        //基本的订单信息
        OrderEntity order =orderSaveVO.getOrderBase();
        order.setUserName(address.getUserName());
        order.setUserTel(address.getUserTel());

        order.setProvinceCode(address.getProvinceCode());
        order.setCityCode(address.getCityCode());
        order.setAreaCode(address.getAreaCode());
        //收货地址
        order.setAddressFid(creatOrderRequest.getAddressFid());
    }






    /**
     * 处理当前的的下单用户信息
     * @param dto
     * @param creatOrderRequest
     */
    private void checkBusinessInfo(DataTransferObject dto,CreatOrderRequest creatOrderRequest){
        if (!dto.checkSuccess()){
            return;
        }
        SupplierEntity supplier = supplierManager.getSupplierByCode(creatOrderRequest.getBusinessUid());
        if (Check.NuNObj(supplier)){
            dto.setErrorMsg("当前商家不存在");
            return;
        }
        SupplierStatusEnum statusEnum = SupplierStatusEnum.getTypeByCode(supplier.getSupplierStatus());
        if (Check.NuNObj(statusEnum)){
            dto.setErrorMsg("异常的用户状态");
            return;
        }
        if (statusEnum.getCode() != UserStatusEnum.AVAILABLE.getCode()){
            dto.setErrorMsg("当前上架已经下架,请联系平台");
            return;
        }
    }


    /**
     * 处理当前的的下单用户信息
     * @param dto
     * @param creatOrderRequest
     */
    private void dealUserInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CreatOrderRequest creatOrderRequest){
        if (!dto.checkSuccess()){
            return;
        }
        UserEntity userEntity = userManager.getUserByUid(creatOrderRequest.getUserUid());
        if (Check.NuNObj(userEntity)){
            dto.setErrorMsg("当前用户不存在");
            return;
        }
        UserStatusEnum statusEnum = UserStatusEnum.getTypeByCode(userEntity.getUserStatus());
        if (Check.NuNObj(statusEnum)){
            dto.setErrorMsg("异常的用户状态");
            return;
        }
        if (statusEnum.getCode() != UserStatusEnum.AVAILABLE.getCode()){
            dto.setErrorMsg("当前用户已经被冻结,请联系平台处理");
            return;
        }
        //当前的用户信息
        orderSaveVO.setUser(userEntity);




    }
}
