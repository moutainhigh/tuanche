package com.taisf.services.recharge.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.SnUtil;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.valenum.UserRoleEnum;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseInfoVO;
import com.taisf.services.order.dto.CartCleanRequest;
import com.taisf.services.recharge.api.RechargeService;
import com.taisf.services.recharge.dto.ChargeHisRequest;
import com.taisf.services.recharge.dto.ChargeRequest;
import com.taisf.services.recharge.entity.RechargeEntity;
import com.taisf.services.recharge.manager.RechargeManagerImpl;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>充值相关</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/12.
 * @version 1.0
 * @since 1.0
 */
@Component("recharge.rechargeServiceProxy")
public class RechargeServiceProxy implements RechargeService {


    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;


    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;


    @Resource(name = "recharge.rechargeManagerImpl")
    private RechargeManagerImpl rechargeManager;



    /**
     * 校验当前的企业状态
     * @param dto
     * @param infoVO
     */
    private void checkEnterpriseInfo(DataTransferObject<Void> dto,EnterpriseInfoVO infoVO){
        if (Check.NuNObj(dto)){
            return;
        }
        if (!dto.checkSuccess()){
            return;
        }

        if (Check.NuNObj(infoVO)){
            dto.setErrorMsg("异常的企业信息");
            return ;
        }
        if (Check.NuNObj(infoVO.getEnterpriseEntity().getTillTime())){
            dto.setErrorMsg("异常的企业截止时间");
            return;
        }
        if (infoVO.getEnterpriseEntity().getTillTime().before(new Date())){
            dto.setErrorMsg("加盟时间已经失效,请联系企业管理人员");
            return;
        }
        EnterpriseConfigEntity config =infoVO.getEnterpriseConfigEntity();
        if(Check.NuNObj(config)){
            dto.setErrorMsg("异常的企业配置信息");
            return;
        }
    }


    /**
     * 分页查询充值记录
     * @author afi
     * @param chargeHisRequest
     * @return
     */
    public DataTransferObject<PagingResult<RechargeEntity>> getRechargeByPage(ChargeHisRequest chargeHisRequest){
        DataTransferObject<PagingResult<RechargeEntity>> dto = new DataTransferObject<>();
        if (Check.NuNObj(chargeHisRequest)){
            chargeHisRequest = new ChargeHisRequest();
        }
        PagingResult<RechargeEntity> page = rechargeManager.getRechargeByPage(chargeHisRequest);
        dto.setData(page);
        return dto;
    }
    /**
     * 企业充值
     * @author afi
     * @param chargeRequest
     * @return
     */
    public  DataTransferObject<Void> chargeMoney(ChargeRequest chargeRequest){
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNStr(chargeRequest.getEnterpriseCode())
                || Check.NuNObj(chargeRequest.getMoneyYuan())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        if (Check.NuNStr(chargeRequest.getEnterpriseCode())
                || Check.NuNObj(chargeRequest.getMoneyYuan())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        int total = chargeRequest.getMoneyYuan() * 100;
        if (total <= 0){
            dto.setErrorMsg("异常的充值金额");
            return dto;
        }

        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(chargeRequest.getEnterpriseCode());
        //校验当前的企业状态
        this.checkEnterpriseInfo(dto,infoVO);
        if (!dto.checkSuccess()){
            return dto;
        }
        // 获取企业下的所有用户
        List<UserEntity> all =userManager.getOkUserByEntrpriseCode(chargeRequest.getEnterpriseCode());
        if (Check.NuNCollection(all)){
            dto.setErrorMsg("当前企业未有员工信息");
            return dto;
        }
        UserEntity user = userManager.fillAndGetEnterpriseUser(infoVO.getEnterpriseEntity());
        if (Check.NuNObj(user)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }

        UserAccountEntity userAccountEntity =userManager.fillAndGetAccountUser(chargeRequest.getEnterpriseCode());
        if (Check.NuNObj(userAccountEntity)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }
        List<UserEntity>  bossList = new ArrayList<>();
        List<UserEntity>  empList = new ArrayList<>();
        if (!Check.NuNCollection(all)){
            for (UserEntity userEntity : all) {
                UserRoleEnum userRoleEnum = UserRoleEnum.getTypeByCode(userEntity.getUserRole());
                if (Check.NuNObj(userRoleEnum)){
                    userRoleEnum = UserRoleEnum.USER;
                }
                if (userRoleEnum.getCode() == UserRoleEnum.BOSS.getCode()){
                    bossList.add(userEntity);
                }else {
                    empList.add(userEntity);
                }
            }
        }
        String rechargeSn = "CH"+SnUtil.getOrderSn();
        RechargeEntity record = new RechargeEntity();
        record.setEnterpriseCode(infoVO.getEnterpriseEntity().getEnterpriseCode());
        record.setEnterpriseName(infoVO.getEnterpriseEntity().getEnterpriseName());
        record.setCreateId(chargeRequest.getOpId());
        record.setCreateName(chargeRequest.getOpName());
        record.setPayMoney(total);
        record.setBossNum(bossList.size());
        record.setCommonNum(empList.size());
        record.setRechargeSn(rechargeSn);
        rechargeManager.saveRecharge(record);
        return dto;

    }



}
