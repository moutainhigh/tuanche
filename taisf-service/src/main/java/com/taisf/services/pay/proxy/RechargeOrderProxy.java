package com.taisf.services.pay.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.SnUtil;
import com.jk.framework.base.utils.ValueUtil;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.pay.api.RechargeOrderService;
import com.taisf.services.pay.dto.RechargeOrderRequest;
import com.taisf.services.pay.entity.RechargeOrderEntity;
import com.taisf.services.pay.manager.RechargeOrderManagerImpl;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2018/4/16.
 * @version 1.0
 * @since 1.0
 */
@Component("pay.rechargeOrderProxy")
public class RechargeOrderProxy  implements RechargeOrderService {


    private static final Logger LOGGER = LoggerFactory.getLogger(PayServiceProxy.class);

    @Resource(name = "pay.rechargeOrderManagerImpl")
    private RechargeOrderManagerImpl rechargeOrderManager;


    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;

    /**
     * 充值的下单逻辑
     * @author  afi
     * @param rechargeOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<String> createRechargeOrder(RechargeOrderRequest rechargeOrderRequest) {
        DataTransferObject dto = new DataTransferObject();

        if (Check.NuNObj(rechargeOrderRequest)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(rechargeOrderRequest.getUserUid())){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (ValueUtil.getintValue(rechargeOrderRequest.getNeedMoney()) <= 0){
            dto.setErrorMsg("请输入需要充值的金额");
            return dto;
        }
        UserEntity userEntity = userManager.getUserByUid(rechargeOrderRequest.getUserUid());
        if (Check.NuNObj(userEntity)){
            dto.setErrorMsg("当前用户不存在");
            return dto;
        }
        if (Check.NuNStr(userEntity.getEnterpriseCode())){
            dto.setErrorMsg("异常的用户信息");
            return dto;
        }
        if (Check.NuNStr(userEntity.getBizCode())){
            dto.setErrorMsg("异常的用户信息");
            return dto;
        }
        String orderSn = "CZ"+ SnUtil.getOrderSn();
        RechargeOrderEntity rechargeOrderEntity = new RechargeOrderEntity();
        rechargeOrderEntity.setNeedMoney(rechargeOrderRequest.getNeedMoney());
        rechargeOrderEntity.setUserUid(rechargeOrderRequest.getUserUid());
        rechargeOrderEntity.setOrderSn(orderSn);
        rechargeOrderEntity.setSupplierCode(userEntity.getEnterpriseCode());
        rechargeOrderEntity.setEnterpriseCode(userEntity.getBizCode());
        try{
            int num = rechargeOrderManager.saveRechargeOrder(rechargeOrderEntity);
            if (num == 0){
                dto.setErrorMsg("充值失败");
                return dto;
            }
        }catch (Exception e){
            dto.setErrorMsg("充值失败");
            return dto;
        }
        dto.setData(orderSn);
        return dto;
    }


    /**
     * 处理回调
     * @author  afi
     * @param rechargeOrderEntity
     * @return
     */
    @Override
    public DataTransferObject<Void> dealRechargeOrder4PayReturn(RechargeOrderEntity rechargeOrderEntity) {
        DataTransferObject dto = new DataTransferObject();

        if (Check.NuNObjs(rechargeOrderEntity.getOrderSn(),rechargeOrderEntity.getUserUid())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        try{
            int num = rechargeOrderManager.updateOrderPayAndAccount(rechargeOrderEntity);
            if (num == 0){
                dto.setErrorMsg("更新失败");
                return dto;
            }
        }catch (Exception e){
            dto.setErrorMsg("更新失败");
            return dto;
        }
        return dto;
    }



    /**
     * 获取充值信息
     * @author  afi
     * @param orderSn
     * @return
     */
    @Override
    public DataTransferObject<RechargeOrderEntity> getRechargeOrderByOrderSn(String orderSn) {
        DataTransferObject<RechargeOrderEntity> dto = new DataTransferObject();

        if (Check.NuNObj(orderSn)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try{
            RechargeOrderEntity rechargeOrderEntity = rechargeOrderManager.getRechargeOrderByOrderSn(orderSn);
            dto.setData(rechargeOrderEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER,"获取订单信息异常:par:{}",orderSn);
            dto.setErrorMsg("获取充值信息");
        }
        return dto;
    }




}
