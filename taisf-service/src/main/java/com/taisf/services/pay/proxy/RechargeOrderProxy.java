package com.taisf.services.pay.proxy;

import com.jk.framework.base.constant.YesNoEnum;
import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.base.utils.SnUtil;
import com.jk.framework.base.utils.ValueUtil;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.activity.entity.ActivityRangeEntity;
import com.taisf.services.activity.manager.ActivityManagerImpl;
import com.taisf.services.activity.vo.ActivityVO;
import com.taisf.services.pay.api.RechargeOrderService;
import com.taisf.services.pay.dto.RechargeOrderListRequest;
import com.taisf.services.pay.dto.RechargeOrderRequest;
import com.taisf.services.pay.entity.RechargeOrderEntity;
import com.taisf.services.pay.manager.RechargeOrderManagerImpl;
import com.taisf.services.pay.vo.RechargeOrderVO;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import com.taisf.services.user.vo.AccountUserLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource(name = "activity.activityManagerImpl")
    private ActivityManagerImpl activityManager;



    /**
     * 获取充值记录
     * @author afi
     * @param rechargeOrderListRequest
     * @return
     */
    @Override
    public DataTransferObject<List<RechargeOrderVO>> findRechargeOrderAll(RechargeOrderListRequest rechargeOrderListRequest){
        DataTransferObject<List<RechargeOrderVO>> dto = new DataTransferObject<>();
        if (Check.NuNObj(rechargeOrderListRequest)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //获取当前的信息
        List<RechargeOrderVO> list = rechargeOrderManager.findRechargeOrderAll(rechargeOrderListRequest);
        if (list == null) {
            list = new ArrayList<>();
        }
        dto.setData(list);
        return dto;
    }

    /**
     * 获取充值记录
     * @author afi
     * @param rechargeOrderListRequest
     * @return
     */
    @Override
    public DataTransferObject<PagingResult<RechargeOrderVO>> findRechargeOrderByPage(RechargeOrderListRequest rechargeOrderListRequest){

        DataTransferObject<PagingResult<RechargeOrderVO>> dto = new DataTransferObject<>();
        if (Check.NuNObj(rechargeOrderListRequest)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        //获取当前的信息
        PagingResult<RechargeOrderVO> page = rechargeOrderManager.findRechargeOrderByPage(rechargeOrderListRequest);
        if (page == null) {
            page = new PagingResult();
        }

        dto.setData(page);
        return dto;
    }

    /**
     * 充值的下单逻辑
     * @author  afi
     * @param rechargeOrderRequest
     * @return
     */
    @Override
    public DataTransferObject<RechargeOrderEntity> createRechargeOrder(RechargeOrderRequest rechargeOrderRequest) {
        DataTransferObject<RechargeOrderEntity> dto = new DataTransferObject();

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
            if(rechargeOrderRequest.getActivityFlag()){
                //填充当前的充值的送钱的活动
                this.fillRechargeExtMoney(rechargeOrderEntity);
            }
            //直接保存当前的充值信息
            int num = rechargeOrderManager.saveRechargeOrder(rechargeOrderEntity);
            if (num == 0){
                dto.setErrorMsg("充值失败");
                return dto;
            }
        }catch (Exception e){
            dto.setErrorMsg("充值失败");
            return dto;
        }
        dto.setData(rechargeOrderEntity);
        return dto;
    }

    /**
     * 填充当前的充值信息
     * @param rechargeOrderEntity
     */
    private void fillRechargeExtMoney(RechargeOrderEntity rechargeOrderEntity){
        if (Check.NuNObj(rechargeOrderEntity)){
            return;
        }
        //获取当前的返利情况
        rechargeOrderEntity.setExtMoney(getRechargeExtMoney(rechargeOrderEntity.getNeedMoney()));
    }


    /**
     * 获取当前充值的返利活动
     * @param money
     * @return
     */
    private int getRechargeExtMoney(Integer money){
        int ext = 0;
        if (ValueUtil.getintValue(money) <= 0){
            return ext;
        }
        ActivityVO last = activityManager.getLastAct4ChargeVo();
        if (Check.NuNObj(last)){
            return ext;
        }
        List<ActivityRangeEntity> list = last.getRangeList();
        if(Check.NuNCollection(list)){
            return ext;
        }
        for (ActivityRangeEntity activityRangeEntity : list) {
            int limit = ValueUtil.getintValue(activityRangeEntity.getRangeLimit());
            if (money >= limit){
                ext =ValueUtil.getintValue(activityRangeEntity.getRangeMoney());
                return ext;
            }
        }
        return ext;
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

        LogUtil.info(LOGGER,"dealRechargeOrder4PayReturn par:{}", JsonEntityTransform.Object2Json(rechargeOrderEntity));
        if (Check.NuNStr(rechargeOrderEntity.getOrderSn())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        if (Check.NuNStr(rechargeOrderEntity.getTradeNo())){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        RechargeOrderEntity order = rechargeOrderManager.getRechargeOrderByOrderSn(rechargeOrderEntity.getOrderSn());
        if (Check.NuNObj(order)){
            dto.setErrorMsg("当前订单不存在");
            return dto;
        }
        if (ValueUtil.getintValue(order.getPayStatus()) == YesNoEnum.YES.getCode()){
            return dto;
        }
        rechargeOrderEntity.setUserUid(order.getUserUid());
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
