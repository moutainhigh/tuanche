package com.taisf.services.user.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.head.Header;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.UUIDGenerator;
import com.taisf.services.common.valenum.ApplicationCodeEnum;
import com.taisf.services.common.valenum.EnterpriseStatusEnum;
import com.taisf.services.common.valenum.UserRoleEnum;
import com.taisf.services.common.valenum.UserStatusEnum;
import com.taisf.services.enterprise.entity.EnterpriseAddressEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.manager.EnterpriseManagerImpl;
import com.taisf.services.supplier.proxy.SupplierProductServiceProxy;
import com.taisf.services.user.api.UserService;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;
import com.taisf.services.user.entity.LoginTokenEntity;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.manager.UserManagerImpl;
import com.taisf.services.user.vo.RegistInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>用户中心接口实现</p>
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
@Component("user.userServiceProxy")
public class UserServiceProxy implements UserService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierProductServiceProxy.class);

    @Resource(name = "user.userManagerImpl")
    private UserManagerImpl userManager;


    @Resource(name = "enterprise.enterpriseManagerImpl")
    private EnterpriseManagerImpl enterpriseManager;


    @Override
    public DataTransferObject<RegistInfoVO> regist(UserRegistRequest userRegistRequest) {
        DataTransferObject<RegistInfoVO> dto = new DataTransferObject<>();
        if (Check.NuNObj(userRegistRequest)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(userRegistRequest.getUserPhone())){
            dto.setErrorMsg("请输入正确的手机号");
            return dto;
        }

        if (Check.NuNStr(userRegistRequest.getPwd())){
            dto.setErrorMsg("请输入密码");
            return dto;
        }

        //TODO 验证码

        //校验头信息
        this.checkHeaderMust(dto,userRegistRequest.getHeader());
        if (!dto.checkSuccess()){
            return dto;
        }

        ApplicationCodeEnum applicationCodeEnum = ApplicationCodeEnum.getTypeByApplicationCode(userRegistRequest.getHeader().getApplicationCode());
        if (Check.NuNObj(applicationCodeEnum)){
            dto.setErrorMsg("异常的应用名称");
            return dto;
        }
        //1. 验证手机号信息
        UserEntity userEntity = userManager.getUserByUserPhone(userRegistRequest.getUserPhone());
        if (Check.NuNObj(userEntity)){
            dto.setErrorMsg("该手机号不存在");
            return dto;
        }
        //2. 判断用户状态
        UserStatusEnum userStatusEnum = UserStatusEnum.getTypeByCode(userEntity.getUserStatus());
        if (Check.NuNObj(userStatusEnum)){
            dto.setErrorMsg("异常的用户状态");
            return dto;
        }
        if (userStatusEnum.getCode() == UserStatusEnum.FORBIDDEN.getCode()){
            dto.setErrorMsg("该帐户已注销");
            return dto;
        }else if (userStatusEnum.getCode() == UserStatusEnum.FREEZE.getCode()){
            dto.setErrorMsg("该帐户已冻结");
            return dto;
        }else if (userStatusEnum.getCode() == UserStatusEnum.ACTIVITY.getCode()){
            dto.setErrorMsg("该手机号已注册");
            return dto;
        }

        //2. 获取企业信息
        EnterpriseEntity infoVO = enterpriseManager.getEnterpriseByCode(userEntity.getEnterpriseCode());
        if (Check.NuNObj(infoVO)){
            dto.setErrorMsg("异常的企业信息");
            return dto;
        }

        //3. 获取合作企业状态
        EnterpriseStatusEnum statusEnum = EnterpriseStatusEnum.getTypeByCode(infoVO.getEnterpriseStatus());
        if (Check.NuNObj(statusEnum)){
            dto.setErrorMsg("异常的企业状态信息");
            return dto;
        }
        if (!statusEnum.checkOk()){
            dto.setErrorMsg(statusEnum.getDes());
            return dto;
        }
        if (Check.NuNObj(infoVO.getTillTime())){
            dto.setErrorMsg("异常的企业合作信息,请联系管理员");
            return dto;
        }
        if (infoVO.getTillTime().before(new Date())){
            dto.setErrorMsg("该企业合作已过期");
            return dto;
        }

        //4. 修改用户状态
        userManager.updateUser2Activity(userEntity.getUserUid());

        //5. 获取企业的信息并封装企业返回信息
        this.fillEnterpriseInfo(dto,userEntity.getEnterpriseCode(),userEntity);

        return dto;
    }

    /**
     * 拼装当前用户的激活信息
     * @param dto
     * @param enterpriseCode
     * @param userEntity
     */
    private void fillEnterpriseInfo(DataTransferObject<RegistInfoVO> dto,String enterpriseCode,UserEntity userEntity){
        if (Check.NuNObj(dto)){
            return;
        }
        if (Check.NuNObj(userEntity)){
            return;
        }

        if (!dto.checkSuccess()){
            return;
        }
        RegistInfoVO vo = new RegistInfoVO();
        BeanUtils.copyProperties(userEntity,vo);

        UserRoleEnum userRoleEnum = UserRoleEnum.getTypeByCode(userEntity.getUserRole());
        if (Check.NuNObj(userRoleEnum)){
            dto.setErrorMsg("异常的用户套餐信息");
            return;
        }
        vo.setUserRoleName(userRoleEnum.getName());
        List<EnterpriseAddressEntity> list = enterpriseManager.getEnterpriseAddressByCode(enterpriseCode);
        if(!Check.NuNCollection(list)){
            for (EnterpriseAddressEntity enterpriseAddressEntity : list) {
                vo.getAddrList().add(enterpriseAddressEntity.getAddress());
            }
        }

        UserAccountEntity accountEntity = userManager.fillAndGetAccountUser(userEntity.getUserUid());
        if(Check.NuNObj(accountEntity)){
            dto.setErrorMsg("异常的账户信息");
            return;
        }
        vo.setDrawBalance(accountEntity.getDrawBalance());
        //设置属性
        dto.setData(vo);
    }



    @Override
    public DataTransferObject<String> login(UserLoginRequest userLoginRequest) {
        DataTransferObject<String> dto = new DataTransferObject<>();
        if (Check.NuNObj(userLoginRequest)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(userLoginRequest.getUserPhone())){
            dto.setErrorMsg("请输入正确的手机号");
            return dto;
        }

        if (Check.NuNStr(userLoginRequest.getPwd())){
            dto.setErrorMsg("请输入密码");
            return dto;
        }

        //校验头信息
        this.checkHeaderMust(dto,userLoginRequest.getHeader());
        if (!dto.checkSuccess()){
            return dto;
        }

        ApplicationCodeEnum applicationCodeEnum = ApplicationCodeEnum.getTypeByApplicationCode(userLoginRequest.getHeader().getApplicationCode());
        if (Check.NuNObj(applicationCodeEnum)){
            dto.setErrorMsg("异常的应用名称");
            return dto;
        }
        //1. 验证手机号信息
        UserEntity userEntity = userManager.getUserByUserPhone(userLoginRequest.getUserPhone());
        if (Check.NuNObj(userEntity)){
            dto.setErrorMsg("该手机号不存在");
            return dto;
        }
        if (!userEntity.getUserPassword().equals(userLoginRequest.getPwd())){
            dto.setErrorMsg("请输入正确的密码");
            return dto;
        }
        //2. 判断用户状态
        UserStatusEnum userStatusEnum = UserStatusEnum.getTypeByCode(userEntity.getUserStatus());
        if (Check.NuNObj(userStatusEnum)){
            dto.setErrorMsg("异常的用户状态");
            return dto;
        }
        if (userStatusEnum.getCode() == UserStatusEnum.FORBIDDEN.getCode()){
            dto.setErrorMsg("该帐户已注销");
            return dto;
        }else if (userStatusEnum.getCode() == UserStatusEnum.FREEZE.getCode()){
            dto.setErrorMsg("该帐户已冻结");
            return dto;
        }

        //2. 获取企业信息
        EnterpriseEntity infoVO = enterpriseManager.getEnterpriseByCode(userEntity.getEnterpriseCode());
        if (Check.NuNObj(infoVO)){
            dto.setErrorMsg("异常的企业信息");
            return dto;
        }

        //获取合作企业状态
        EnterpriseStatusEnum statusEnum = EnterpriseStatusEnum.getTypeByCode(infoVO.getEnterpriseStatus());
        if (Check.NuNObj(statusEnum)){
            dto.setErrorMsg("异常的企业状态信息");
            return dto;
        }
        if (!statusEnum.checkOk()){
            dto.setErrorMsg(statusEnum.getDes());
            return dto;
        }
        if (Check.NuNObj(infoVO.getTillTime())){
            dto.setErrorMsg("异常的企业合作信息,请联系管理员");
            return dto;
        }
        if (infoVO.getTillTime().before(new Date())){
            dto.setErrorMsg("该企业合作已过期");
            return dto;
        }
        //2. 获取token信息
        LoginTokenEntity token = userManager.getToken(userEntity.getUserUid(), userLoginRequest.getHeader().getDeviceUuid(), applicationCodeEnum.getCode());
        if (!Check.NuNObj(token)){
            dto.setData(token.getUserToken());
        }else {

            String userToken = UUIDGenerator.hexUUID();
            LoginTokenEntity loginTokenEntity = new LoginTokenEntity();
            loginTokenEntity.setUserId(userEntity.getUserUid());
            loginTokenEntity.setUserToken(userToken);
            userManager.saveLoginToken(transHeader2Token(userLoginRequest.getHeader(),loginTokenEntity));
            dto.setData(userToken);
        }
        return dto;
    }

    /**
     * 转化token信息
     * @param header
     * @param loginTokenEntity
     * @return
     */
    private LoginTokenEntity transHeader2Token(Header header,LoginTokenEntity loginTokenEntity){
        Date now = new Date();
        loginTokenEntity.setDeviceUuid(header.getDeviceUuid());
        loginTokenEntity.setCreateTime(now);
        loginTokenEntity.setExpireTime(DateUtil.jumpDate(now,365));
        loginTokenEntity.setUserToken(UUIDGenerator.hexUUID());
        loginTokenEntity.setVersionCode(header.getVersionCode());
        ApplicationCodeEnum applicationCodeEnum = ApplicationCodeEnum.getTypeByApplicationCode(header.getApplicationCode());
        loginTokenEntity.setLoginSource(applicationCodeEnum.getCode());
        loginTokenEntity.setSourceType(header.getSource());
        return loginTokenEntity;
    }

    /**
     * 校验当前的head必填信息
     * @param dto
     * @param header
     */
    private void checkHeaderMust(DataTransferObject dto, Header header){
        if (Check.NuNObj(dto)){
            return;
        }
        if (!dto.checkSuccess()){
            return;
        }

        if (Check.NuNObj(header)){
            dto.setErrorMsg("异常的头信息");
            return;
        }

        if (Check.NuNStr(header.getDeviceUuid())
                || Check.NuNStr(header.getApplicationCode())
                || Check.NuNStr(header.getVersionCode())
                || Check.NuNObj(header.getSource())){
            dto.setErrorMsg("异常的头信息");
            return ;
        }

        ApplicationCodeEnum applicationCodeEnum = ApplicationCodeEnum.getTypeByApplicationCode(header.getApplicationCode());
        if (Check.NuNObj(applicationCodeEnum)){
            dto.setErrorMsg("异常的应用名称");
            return;
        }
    }

    @Override
    public DataTransferObject<RegistInfoVO> loginByCode(UserLoginRequest userLoginRequest) {

        return null;
    }


    @Override
    public DataTransferObject<Void> logout(UserLogoutRequest userLogoutRequest) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(userLogoutRequest)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(userLogoutRequest.getToken())){
            dto.setErrorMsg("参数异常");
            return dto;
        }

        //校验头信息
        this.checkHeaderMust(dto,userLogoutRequest.getHeader());
        if (!dto.checkSuccess()){
            return dto;
        }
        ApplicationCodeEnum applicationCodeEnum = ApplicationCodeEnum.getTypeByApplicationCode(userLogoutRequest.getHeader().getApplicationCode());
        if (Check.NuNObj(applicationCodeEnum)){
            dto.setErrorMsg("异常的应用名称");
            return dto;
        }
        //2. 获取token信息
        LoginTokenEntity token = userManager.getTokenByToken(userLogoutRequest.getToken());
        if (Check.NuNObj(token)){
            //幂等返回
            return dto;
        }
        //3. 校验匹配
        this.checkHead2Token(dto,userLogoutRequest.getHeader(),token,applicationCodeEnum);
        if (dto.checkSuccess()){
            userManager.deleteById(token.getId());
        }
        return dto;
    }


    /**
     * 校验参数异常
     * @param dto
     * @param header
     * @param token
     */
    private void checkHead2Token(DataTransferObject dto, Header header,LoginTokenEntity token,ApplicationCodeEnum applicationCodeEnum){
        if (Check.NuNObj(dto)){
            return ;
        }
        if (Check.NuNObjs(header,token,applicationCodeEnum)){
            dto.setErrorMsg("参数异常");
            return ;
        }
        if (!header.getDeviceUuid().equals(token.getDeviceUuid())){
            dto.setErrorMsg("退出失败,参数错误");
            return ;
        }
        if (applicationCodeEnum.getCode() != token.getLoginSource()){
            dto.setErrorMsg("退出失败,参数错误");
            return ;
        }
    }
}
