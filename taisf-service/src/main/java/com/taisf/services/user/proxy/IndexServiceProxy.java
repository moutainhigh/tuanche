package com.taisf.services.user.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.UUIDGenerator;
import com.jk.framework.base.utils.ValueUtil;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.common.valenum.*;
import com.taisf.services.enterprise.entity.EnterpriseAddressEntity;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseInfoVO;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import com.taisf.services.supplier.proxy.SupplierProductServiceProxy;
import com.taisf.services.user.api.IndexService;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;
import com.taisf.services.user.entity.LoginTokenEntity;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import com.taisf.services.user.vo.IndexBaseVO;
import com.taisf.services.user.vo.IndexUserVO;
import com.taisf.services.user.vo.IndexVO;
import com.taisf.services.user.vo.RegistInfoVO;
import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>首页接口实现</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/6.
 * @version 1.0
 * @since 1.0
 */
@Component("user.indexServiceProxy")
public class IndexServiceProxy implements IndexService {


    private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceProxy.class);

    @Resource(name = "supplier.supplierManagerImpl")
    private SupplierManagerImpl supplierManager;

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;

    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;


    /**
     * 获取用户的地址信息
     * @author afi
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<List<EnterpriseAddressEntity>> getUserAddressList(String userUid) {
        DataTransferObject<List<EnterpriseAddressEntity>> dto = new DataTransferObject<>();
        UserEntity userEntity = userManager.getUserByUid(userUid);
        if (Check.NuNObj(userEntity)) {
            dto.setErrorMsg("当前用户不存在");
            return dto;
        }
        if(Check.NuNStr(userEntity.getEnterpriseCode())){
            dto.setErrorMsg("未绑定企业信息");
            return dto;
        }
        List<EnterpriseAddressEntity> list = enterpriseManager.getEnterpriseAddressByCode(userEntity.getEnterpriseCode());
        if (!Check.NuNCollection(list)){
            for (EnterpriseAddressEntity entity : list) {
                entity.setConTel(userEntity.getUserPhone());
                entity.setConName(userEntity.getUserName() + "["+userEntity.getUserCode()+"]");
            }
        }
        dto.setData(list);
        return dto;
    }

    /**
     * 获取用户的基本信息
     * @author afi
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<IndexBaseVO> getUserBaseInfo(String userUid) {

        DataTransferObject<IndexBaseVO> dto = new DataTransferObject<>();
        IndexBaseVO indexVO = new IndexBaseVO();
        //设置属性
        dto.setData(indexVO);
        if (Check.NuNObj(userUid)) {
            dto.setErrorMsg("参数异常");
            return dto;
        }
        UserEntity userEntity = userManager.getUserByUid(userUid);
        if (Check.NuNObj(userEntity)) {
            dto.setErrorMsg("当前用户不存在");
            return dto;
        }
        //1. 2
        // 处理用户的基本信息
        this.dealUserInfo(dto, userEntity, indexVO);
        if (!dto.checkSuccess()) {
            return dto;
        }
        dto.setData(indexVO);
        return dto;
    }


    /**
     * 处理当前的用户基本信息
     * @param dto
     * @param userEntity
     * @param indexVO
     */
    private void dealUserInfo(DataTransferObject dto, UserEntity userEntity, IndexBaseVO indexVO){
        if (Check.NuNObjs(dto,userEntity,indexVO)){
            return;
        }
        if (!dto.checkSuccess()){
            return;
        }
        IndexUserVO  userVO = new IndexUserVO();
        BeanUtils.copyProperties(userEntity,userVO);
        // 1. 设置基本属性
        indexVO.setUserInfo(userVO);
        UserStatusEnum statusEnum = UserStatusEnum.getTypeByCode(userEntity.getUserStatus());
        if (Check.NuNObj(statusEnum)){
            dto.setErrorMsg("异常的用户状态");
            return ;
        }

        if (statusEnum.getCode() == UserStatusEnum.AVAILABLE.getCode()){
            dto.setErrorMsg("当前用户未激活,请联系注册激活");
            return ;
        }
        if (statusEnum.getCode() != UserStatusEnum.ACTIVITY.getCode()){
            dto.setErrorMsg("当前用户已经被冻结,请联系平台处理");
            return ;
        }

        // 2.设置账户信息
        UserAccountEntity accountEntity =userManager.fillAndGetAccountUser(userEntity.getUserUid());

        //校验当前的账户状态
        AccountStatusEnum accountStatusEnum = AccountStatusEnum.getTypeByCode(accountEntity.getAccountStatus());
        if (Check.NuNObj(accountStatusEnum)){
            dto.setErrorMsg("异常的账户状态");
            return ;
        }
        if (!accountStatusEnum.checkOk()){
            dto.setErrorMsg(accountStatusEnum.getDesc());
            return ;
        }
        //获取当前的余额
        int drawBalance = ValueUtil.getintValue(accountEntity.getDrawBalance());
        if (drawBalance < 0){
            dto.setErrorMsg("异常的账户信息");
            return ;
        }
        indexVO.setDrawBalance(drawBalance);
    }


    /**
     * 获取首页信息
     * @param userUid
     * @return
     */
    @Override
    public DataTransferObject<IndexVO> getIndex(String userUid) {

        DataTransferObject<IndexVO> dto = new DataTransferObject<>();
        IndexVO indexVO = new IndexVO();
        //设置属性
        dto.setData(indexVO);
        if (Check.NuNObj(userUid)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        UserEntity userEntity = userManager.getUserByUid(userUid);
        if (Check.NuNObj(userEntity)){
            dto.setErrorMsg("当前用户不存在");
            return dto;
        }

        //1. 2
        // 处理用户的基本信息
        this.dealUserInfo(dto,userEntity,indexVO);
        if (!dto.checkSuccess()){
            return dto;
        }

        // 3.设置企业信息
        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(userEntity.getEnterpriseCode());
        if (Check.NuNObj(infoVO)){
            dto.setErrorMsg("异常的企业信息");
            return dto;
        }
        if (Check.NuNObj(infoVO.getEnterpriseEntity().getTillTime())){
            dto.setErrorMsg("异常的企业截止时间");
            return dto;
        }

        //当前时间
        Date now = new Date();
        if (infoVO.getEnterpriseEntity().getTillTime().before(now)){
            dto.setErrorMsg("加盟时间已经失效,请联系企业管理人员");
            return dto;
        }
        EnterpriseConfigEntity config =infoVO.getEnterpriseConfigEntity();
        if(Check.NuNObj(config)){
            dto.setErrorMsg("异常的企业配置信息");
            return dto;
        }

        //获取当前的订餐类型
        OrderTypeEnum orderTypeEnum = this.dealTime4Lunch(config,now,indexVO);
        if (Check.NuNObj(orderTypeEnum)){
            orderTypeEnum = this.dealTime4Dinner(config,now,indexVO);
        }
        if (Check.NuNObj(orderTypeEnum)){
            indexVO.setTimeMsg("订餐倒计时:");
        }else {
            indexVO.setOrderFlag(true);
            indexVO.setOrderType(orderTypeEnum.getCode());
        }

        //设置供应商的code
        indexVO.setSupplierCode(infoVO.getEnterpriseEntity().getSupplierCode());
        return dto;
    }

    /**
     * 校验当前
     * @return
     */
    private OrderTypeEnum dealTime4Lunch( EnterpriseConfigEntity config ,Date now,IndexVO indexVO){
        OrderTypeEnum orderTypeEnum = null;
        if (Check.NuNObj(config)){
            return orderTypeEnum;
        }
        if (ValueUtil.getintValue(config.getForLunch()) != 1){
            return orderTypeEnum;
        }
        try {
            Date start = DateUtil.connectDate(now,config.getLunchStart());
            Date end = DateUtil.connectDate(now,config.getLunchEnd());
            if (now.after(start) && now.before(end)){
                //时间正常
                orderTypeEnum = OrderTypeEnum.LUNCH_COMMON;
                indexVO.setTimeMsg("午餐订餐倒计时:");
                indexVO.setTimeLast(end.getTime() - now.getTime());
            }else if (now.after(end) && now.before(DateUtil.jumpMinute(end,30))){
                indexVO.setTimeMsg("午餐订餐倒计时:");
                //时间正常
                orderTypeEnum = OrderTypeEnum.LUNCH_EXT;
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"处理时间错误:e:{}",e);
            return  orderTypeEnum;
        }
        return orderTypeEnum;
    }



    /**
     * 校验当前
     * @return
     */
    private OrderTypeEnum dealTime4Dinner(EnterpriseConfigEntity config ,Date now,IndexVO indexVO){
        OrderTypeEnum orderTypeEnum = null;
        if (Check.NuNObj(config)){
            return orderTypeEnum;
        }
        if (ValueUtil.getintValue(config.getForLunch()) != 1){
            return orderTypeEnum;
        }
        try {
            Date start = DateUtil.connectDate(now,config.getDinnerStart());
            Date end = DateUtil.connectDate(now,config.getDinnerEnd());
            if (now.after(start) && now.before(end)){
                //时间正常
                indexVO.setTimeMsg("晚餐订餐倒计时:");
                indexVO.setTimeLast(end.getTime() - now.getTime());
                orderTypeEnum = OrderTypeEnum.DINNER_COMMON;
            }else if (now.after(end) && now.before(DateUtil.jumpMinute(end,30))){
                indexVO.setTimeMsg("晚餐订餐倒计时:");
                //补单
                orderTypeEnum = OrderTypeEnum.DINNER_EXT;
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"处理时间错误:e:{}",e);
            return  orderTypeEnum;
        }
        return orderTypeEnum;
    }



}
