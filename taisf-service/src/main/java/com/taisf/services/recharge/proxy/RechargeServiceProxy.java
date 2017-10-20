package com.taisf.services.recharge.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.SnUtil;
import com.jk.framework.base.utils.UUIDGenerator;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.valenum.UserRoleEnum;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.enterprise.vo.EnterpriseInfoVO;
import com.taisf.services.order.dto.CartCleanRequest;
import com.taisf.services.recharge.api.RechargeService;
import com.taisf.services.recharge.dto.BalanceMoneyAvgRequest;
import com.taisf.services.recharge.dto.BalanceMoneyOneRequest;
import com.taisf.services.recharge.dto.ChargeHisRequest;
import com.taisf.services.recharge.dto.ChargeRequest;
import com.taisf.services.recharge.entity.RechargeEntity;
import com.taisf.services.recharge.manager.RechargeManagerImpl;
import com.taisf.services.recharge.vo.EnterpriseStatsNumber;
import com.taisf.services.supplier.manager.SupplierManagerImpl;
import com.taisf.services.user.dto.UserAccounFillRequest;
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
    private void checkEnterpriseInfo(DataTransferObject dto,EnterpriseInfoVO infoVO){
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
     * 获取企业下的员工数量和金额
     * @author afi
     * @param enterpriseCode
     * @return
     */
    public DataTransferObject<EnterpriseStatsNumber>  getEnterpriseStatsNumber(String enterpriseCode){

        DataTransferObject<EnterpriseStatsNumber> dto = new DataTransferObject<>();
        if (Check.NuNStr(enterpriseCode)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(enterpriseCode);
        //校验当前的企业状态
        this.checkEnterpriseInfo(dto,infoVO);
        if (!dto.checkSuccess()){
            return dto;
        }
        //当前企业的限制信息
        EnterpriseStatsNumber number = new EnterpriseStatsNumber();
        dto.setData(number);
        // 获取企业下的所有用户
        List<UserEntity> all =userManager.getOkUserByEntrpriseCode(enterpriseCode);
        if (Check.NuNCollection(all)){
            return dto;
        }
        UserEntity user = userManager.fillAndGetEnterpriseUser(infoVO.getEnterpriseEntity());
        if (Check.NuNObj(user)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }

        UserAccountEntity userAccountEntity =userManager.fillAndGetAccountUser(enterpriseCode);
        if (Check.NuNObj(userAccountEntity)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }
        //设置余额
        number.setDrawBalance(ValueUtil.getintValue(userAccountEntity.getDrawBalance()));

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
        number.setBossNum(bossList.size());
        number.setEmpNum(empList.size());
        return dto;
    }



    /**
     * 分配金额-对个人
     * @author afi
     * @param request
     * @return
     */
    public  DataTransferObject<Void> balanceMoneyOne(BalanceMoneyOneRequest request){
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(request.getEnterpriseCode())){
            dto.setErrorMsg("企业code异常");
            return dto;
        }

        if (Check.NuNStr(request.getUserPhone())){
            dto.setErrorMsg("请填写用户手机号");
            return dto;
        }
        if (ValueUtil.getintValue(request.getMoneyYuan()) <= 0){
            dto.setErrorMsg("异常的金额");
            return dto;
        }

        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(request.getEnterpriseCode());
        //校验当前的企业状态
        this.checkEnterpriseInfo(dto,infoVO);
        if (!dto.checkSuccess()){
            return dto;
        }
        UserAccountEntity userAccountEntity =userManager.fillAndGetAccountUser(request.getEnterpriseCode());
        if (Check.NuNObj(userAccountEntity)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }

        // 获取企业下的所有用户
        List<UserEntity> all =userManager.getOkUserByEntrpriseCode(request.getEnterpriseCode());
        if (Check.NuNCollection(all)){
            return dto;
        }
        UserEntity has = null;
        for (UserEntity userEntity : all) {
            if (request.getUserPhone().equals(userEntity.getUserPhone())){
                has = userEntity;
                break;
            }
        }
        if (Check.NuNObj(has)){
            dto.setErrorMsg("当前用户不属于当前企业");
            return dto;
        }
        if (request.getMoneyYuan()* 100 > ValueUtil.getintValue(userAccountEntity.getDrawBalance())){
            dto.setErrorMsg("当前余额不足,请确定余额信息");
            return dto;
        }

        rechargeManager.fillUserAccountOneByEnterprise(request.getEnterpriseCode(),has.getUserUid(),request.getMoneyYuan()* 100);
        return dto;
    }


    /**
     * 分配金额-平均
     * @author afi
     * @param request
     * @return
     */
    public  DataTransferObject<Void> balanceMoneyAvg(BalanceMoneyAvgRequest request){
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(request)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(request.getEnterpriseCode())){
            dto.setErrorMsg("企业code异常");
            return dto;
        }

        if (Check.NuNObjs(request.getBossNum(),request.getBossMoneyYuan(),request.getEmpNum(),request.getEmpMoneyYuan())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        EnterpriseInfoVO infoVO = enterpriseManager.getEnterpriseInfoByCode(request.getEnterpriseCode());
        //校验当前的企业状态
        this.checkEnterpriseInfo(dto,infoVO);
        if (!dto.checkSuccess()){
            return dto;
        }
        //当前企业的限制信息
        EnterpriseStatsNumber number = new EnterpriseStatsNumber();
        // 获取企业下的所有用户
        List<UserEntity> all =userManager.getOkUserByEntrpriseCode(request.getEnterpriseCode());
        if (Check.NuNCollection(all)){
            return dto;
        }
        UserEntity user = userManager.fillAndGetEnterpriseUser(infoVO.getEnterpriseEntity());
        if (Check.NuNObj(user)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }

        UserAccountEntity userAccountEntity =userManager.fillAndGetAccountUser(request.getEnterpriseCode());
        if (Check.NuNObj(userAccountEntity)){
            dto.setErrorMsg("创建企业账号信息");
            return dto;
        }
        //设置余额
        number.setDrawBalance(ValueUtil.getintValue(userAccountEntity.getDrawBalance()));

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
        number.setBossNum(bossList.size());
        number.setEmpNum(empList.size());

        if (ValueUtil.getintValue(request.getBossNum()) > 0
                && ValueUtil.getintValue(request.getBossNum()) !=  ValueUtil.getintValue(number.getBossNum())){
            dto.setErrorMsg("老板数量变化,充值失败");
            return dto;
        }
        if (ValueUtil.getintValue(request.getEmpNum()) > 0
                && ValueUtil.getintValue(request.getEmpNum()) !=  ValueUtil.getintValue(number.getEmpNum())){
            dto.setErrorMsg("员工数据发生变化,充值失败");
            return dto;
        }
        int total = 0;
        total += ValueUtil.getintValue(request.getBossNum()) * ValueUtil.getintValue(request.getBossMoneyYuan()) * 100;
        total += ValueUtil.getintValue(request.getEmpNum()) * ValueUtil.getintValue(request.getEmpMoneyYuan()) * 100;
        if (total > number.getDrawBalance()){
            dto.setErrorMsg("当前余额不足,请确定余额信息");
            return dto;
        }
        List<UserAccounFillRequest> list = new ArrayList<>();
        //老板充值金额
        if ( ValueUtil.getintValue(request.getBossNum()) * ValueUtil.getintValue(request.getBossMoneyYuan()) > 0){
            list.addAll(this.transUserFill(dto,bossList,ValueUtil.getintValue(request.getBossMoneyYuan()) * 100));
        }
        //员工充值金额
        if ( ValueUtil.getintValue(request.getEmpNum()) * ValueUtil.getintValue(request.getEmpMoneyYuan()) > 0){
            list.addAll(this.transUserFill(dto,empList,ValueUtil.getintValue(request.getEmpMoneyYuan())* 100));
        }
        if (dto.checkSuccess()){
            rechargeManager.fillUserAccountListByEnterprise(list,request.getEnterpriseCode());
        }
        return dto;
    }

    /**
     * 转化充值参数
     * @param list
     * @param money
     * @return
     */
    private List<UserAccounFillRequest> transUserFill(DataTransferObject dto,List<UserEntity>  list,int money){
        List<UserAccounFillRequest> rst = new ArrayList<>();
        if (!dto.checkSuccess()){
            return rst;
        }
        if (Check.NuNCollection(list)){
            return rst;
        }
        for (UserEntity userEntity : list) {
            UserAccountEntity accountEntity = userManager.fillAndGetAccountUser(userEntity.getUserUid());
            if (Check.NuNObj(accountEntity)){
                dto.setErrorMsg("创建用户失败");
                return rst;
            }
            UserAccounFillRequest fillRequest = new UserAccounFillRequest();
            fillRequest.setUserId(userEntity.getUserUid());
            fillRequest.setBizSn(UUIDGenerator.hexUUID());
            fillRequest.setMoney(money);
            rst.add(fillRequest);
        }
        return rst;
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

        int total = ValueUtil.getintValue(chargeRequest.getMoneyYuan() * 100);
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
        record.setTotalPrice(total);
        record.setPayMoney(total);
        rechargeManager.saveRecharge(record);
        return dto;

    }



}
