package com.taisf.services.order.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.MD5Util;
import com.jk.framework.base.utils.ValueUtil;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;
import com.taisf.services.common.util.MoneyDealUtil;
import com.taisf.services.common.valenum.*;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manger.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseInfoVO;
import com.taisf.services.order.api.CartService;
import com.taisf.services.order.api.OrderService;
import com.taisf.services.order.dto.CreatOrderRequest;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.entity.OrderEntity;
import com.taisf.services.order.entity.OrderMoneyEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.manger.OrderManagerImpl;
import com.taisf.services.order.vo.*;
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
import java.util.Date;
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

    @Resource(name = "order.orderManagerImpl")
    private OrderManagerImpl orderManager;

    @Resource(name = "product.productManagerImpl")
    private ProductManagerImpl productManager;


    @Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;

    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;

    /**
     * 获取当前订单的信息
     * @author afi
     * @param orderInfoRequest
     * @return
     */
    @Override
    public DataTransferObject<PagingResult<OrderInfoVO>> getOrderInfoPage(OrderInfoRequest orderInfoRequest){
        DataTransferObject<PagingResult<OrderInfoVO>> dto = new DataTransferObject<>();
        if (Check.NuNObj(orderInfoRequest)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //分页获取订单列表
        PagingResult<OrderInfoVO> page = orderManager.getOrderInfoPage(orderInfoRequest);
        dto.setData(page);
        return dto;
    }


    /**
     * 获取订单的详细
     * @param orderSn
     * @return
     */
    @Override
    public DataTransferObject<OrderDetailVO>  getOrderDetailBySn(String orderSn){
        DataTransferObject<OrderDetailVO> dto = new DataTransferObject<>();

        if (Check.NuNStr(orderSn)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //获取当前的购物车
        OrderDetailVO orderDetail = orderManager.getOrderDetailBySn(orderSn);
        if (Check.NuNObj(orderDetail)){
            return dto;
        }
        OrderEntity base = orderDetail.getOrderEntity();
        orderDetail.setAddressEntity(userManager.getUserAddressByFid(base.getAddressFid()));

        dto.setData(orderDetail);
        return dto;

    }

    /**
     * 下单[补单]
     * @author afi
     * @param creatOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<String> createExtOrder(CreatOrderRequest creatOrderRequest){
        DataTransferObject<String> dto = new DataTransferObject<>();

        if (Check.NuNStr(creatOrderRequest.getBusinessUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())) {
            dto.setErrorMsg("参数异常");
            return dto;
        }

        OrderSaveVO orderSaveVO = new OrderSaveVO();
        orderSaveVO.getOrderBase().setOrderType(creatOrderRequest.getOrderType());

        //1. 填充订单的信息
        this.fillExtOrderInfo(dto,orderSaveVO,creatOrderRequest,true);
        //2. 下单的逻辑
        orderManager.saveOrderSave(orderSaveVO);

        dto.setData(orderSaveVO.getOrderSn());
        return dto;
    }

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
        orderSaveVO.getOrderBase().setOrderType(creatOrderRequest.getOrderType());

        //1. 填充订单的信息
        this.fillOrderInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest,true);

        //2. 下单的逻辑
        orderManager.saveOrderSave(orderSaveVO);

        dto.setData(orderSaveVO.getOrderSn());
        return dto;
    }


    /**
     * 初始化补单
     * @author afi
     * @param creatOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<OrderSaveVO> initExtOrder(CreatOrderRequest creatOrderRequest){
        DataTransferObject<OrderSaveVO> dto = new DataTransferObject<>();
        if (Check.NuNStr(creatOrderRequest.getBusinessUid())
                || Check.NuNStr(creatOrderRequest.getUserUid())) {
            dto.setErrorMsg("参数异常");
            return dto;
        }

        OrderSaveVO orderSaveVO = new OrderSaveVO("");
        orderSaveVO.getOrderBase().setOrderType(creatOrderRequest.getOrderType());
        //填充订单的信息
        this.fillExtOrderInfo(dto,orderSaveVO,creatOrderRequest,false);
        dto.setData(orderSaveVO);
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
        orderSaveVO.getOrderBase().setOrderType(creatOrderRequest.getOrderType());
        //填充订单的信息
        this.fillOrderInfo(dto,orderSaveVO,cartInfoVO,creatOrderRequest,false);
        dto.setData(orderSaveVO);
        return dto;
    }


    /**
     * 填充补单信息
     * @param dto
     * @param orderSaveVO
     * @param creatOrderRequest
     * @param createFlag
     */
    private void  fillExtOrderInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CreatOrderRequest creatOrderRequest,boolean createFlag){
        if (!dto.checkSuccess()){
            return;
        }
        if (Check.NuNObjs(orderSaveVO)){
            return;
        }

        //1. 检查商家信息
        this.checkBusinessInfo(dto,creatOrderRequest);

        //2. 获取用户信息
        this.dealUserInfo(dto,orderSaveVO,creatOrderRequest);

        //3. 填充当前订单的收货信息
        this.dealUserAddressInfo(dto,orderSaveVO,creatOrderRequest,createFlag);

        //4. 购物车中的商品信息
        this.dealExtOrderProductInfo(dto,orderSaveVO);

        //5. 处理钱信息
        this.dealExtOrderMoneyInfo(dto,orderSaveVO,creatOrderRequest);

        //6. 处理账户信息
        this.dealBalanceInfo(dto,orderSaveVO,orderSaveVO.getExtPrice(),creatOrderRequest,createFlag);

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
        this.dealBalanceInfo(dto,orderSaveVO,cartInfoVO.getPrice(),creatOrderRequest,createFlag);

    }

    /**
     * 处理当前的余额信息
     * @author  afi
     * @param dto
     * @param orderSaveVO
     * @param cost
     * @param creatOrderRequest
     * @param createFlag
     */
    private void dealBalanceInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,int cost,CreatOrderRequest creatOrderRequest,boolean createFlag) {

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
     * 处理当前的补单的商品价格信息
     * @param dto
     * @param orderSaveVO
     * @param creatOrderRequest
     */
    private void dealExtOrderMoneyInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,CreatOrderRequest creatOrderRequest) {
        if (!dto.checkSuccess()) {
            return;
        }
        OrderMoneyEntity money =  orderSaveVO.getOrderMoney();
        money.setSumMoney(orderSaveVO.getExtPrice());
        dealOrderMoneyBase(money);
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
        dealOrderMoneyBase(money);
    }

    /**
     * 初始化当前的金额信息
     * @param money
     */
    private void dealOrderMoneyBase(OrderMoneyEntity money) {
        money.setCarryMoney(0);
        money.setRedMoney(0);
        money.setCouponMoney(0);
        money.setDiscountMoney(0);
    }

    /**
     * 处理当前的补单商品信息
     * @param dto
     * @param orderSaveVO
     */
    private void dealExtOrderProductInfo(DataTransferObject dto,OrderSaveVO orderSaveVO) {
        if (!dto.checkSuccess()) {
            return;
        }
        OrderProductEntity product = new OrderProductEntity();
        product.setOrderSn(orderSaveVO.getOrderSn());
        product.setProductCode(0);
        product.setProductType(SupplierProductTypeEnum.EXR_PRODUCT.getCode());
        product.setProductPrice(orderSaveVO.getExtPrice());
        product.setProductNum(1);
        //添加商品信息
        orderSaveVO.getList().add(product);
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

        //设置当前的用户关联的企业的用餐信息
        this.dealEnterpriseInfo( dto, orderSaveVO,userEntity, creatOrderRequest);
    }


    /**
     * 处理企业信息
     * @author afi
     * @param dto
     * @param orderSaveVO
     * @param userEntity
     * @param creatOrderRequest
     */
    private void dealEnterpriseInfo(DataTransferObject dto,OrderSaveVO orderSaveVO,UserEntity userEntity,CreatOrderRequest creatOrderRequest){
        if (!dto.checkSuccess()){
            return;
        }
        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(userEntity.getEnterpriseCode());
        if (Check.NuNObj(infoVO)){
            dto.setErrorMsg("异常的企业信息");
            return;
        }
        if (Check.NuNObj(infoVO.getEnterpriseEntity().getTillTime())){
            dto.setErrorMsg("异常的企业截止时间");
            return;
        }
        if (infoVO.getEnterpriseEntity().getTillTime().before(orderSaveVO.getNow())){
            dto.setErrorMsg("加盟时间已经失效,请联系企业管理人员");
            return;
        }
        EnterpriseConfigEntity config =infoVO.getEnterpriseConfigEntity();
        if(Check.NuNObj(config)){
            dto.setErrorMsg("异常的企业配置信息");
            return;
        }
        //获取当前的订餐类型
        OrderTypeEnum orderTypeEnum = OrderTypeEnum.getTypeByCode(creatOrderRequest.getOrderType());
        if (Check.NuNObj(orderTypeEnum)){
            dto.setErrorMsg("异常的订餐类型");
            return;
        }

        if (orderTypeEnum.isExt()){
            //补餐
            int extPrice = this.getExtprice(dto,userEntity,orderTypeEnum,config);
            orderSaveVO.setExtPrice(extPrice);
            return;
        }


        // 一下是正常的订单时间的限制

        String limtStart = null;
        String limtEnd = null;
        if (orderTypeEnum.getCode() == OrderTypeEnum.LUNCH_COMMON.getCode()){
            //正常午餐
            limtStart = config.getLunchStart();
            limtEnd = config.getLunchEnd();
        }else if (orderTypeEnum.getCode() == OrderTypeEnum.LUNCH_COMMON.getCode()){
            //正常晚餐
            limtStart = config.getDinnerStart();
            limtEnd = config.getDinnerEnd();
        }
        if (Check.NuNStr(limtStart)
                || Check.NuNStr(limtEnd)){
            dto.setErrorMsg("异常的供餐时间");
            return;
        }

        Date now = orderSaveVO.getNow();
        Date start = DateUtil.connectDate(orderSaveVO.getNow(),limtStart);
        Date end = DateUtil.connectDate(orderSaveVO.getNow(),limtEnd);
        if (now.after(start) && now.before(end)){
            //时间正常
        }else {
            String msg = "请在" + DateUtil.timestampFormat(start) +" 至 " + DateUtil.timestampFormat(end) +"时间内完成订餐";
            dto.setErrorMsg(msg);
        }
    }


    /**
     * 获取当前的补餐价格
     * @author afi
     * @param orderTypeEnum
     * @param config
     * @return
     */
    private int getExtprice(DataTransferObject dto,UserEntity userEntity,OrderTypeEnum orderTypeEnum,EnterpriseConfigEntity config){
        int extPrice = 0;
        if (!dto.checkSuccess()){
            return extPrice;
        }
        UserRoleEnum userRoleEnum =UserRoleEnum.getTypeByCode(userEntity.getUserRole());
        if (Check.NuNObj(userRoleEnum)){
            dto.setErrorMsg("异常的用户类型");
            return extPrice;
        }

        if (orderTypeEnum.getCode() == OrderTypeEnum.LUNCH_EXT.getCode()){
            //午餐补单
            extPrice = this.getRealPrice(userRoleEnum,ValueUtil.getintValue(config.getLunchBoss()),ValueUtil.getintValue(config.getLunchEmp()));
        }else if (orderTypeEnum.getCode() == OrderTypeEnum.DINNER_EXT.getCode()){
            //晚餐补单
            extPrice = this.getRealPrice(userRoleEnum,ValueUtil.getintValue(config.getDinnerBoss()),ValueUtil.getintValue(config.getDinnerEmp()));
        }else {
            dto.setErrorMsg("异常的补单类型");
        }
        //返回补餐金额
        return extPrice;
    }


    /**
     * 获取当前用户的费用
     * @author afi
     * @param userRoleEnum
     * @param bossPrice
     * @param empPrice
     * @return
     */
    private int getRealPrice(UserRoleEnum userRoleEnum,int bossPrice,int empPrice){
        int price = 0;
        if (userRoleEnum.getCode() == UserRoleEnum.BOSS.getCode()){
            price = bossPrice;
        }else {
            price = empPrice;
        }
        return price;
    }
}
