package com.taisf.services.user.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.user.dto.AccountLogRequest;
import com.taisf.services.user.entity.AccountLogEntity;
import com.taisf.services.user.vo.RegistInfoVO;
import com.taisf.services.user.dto.UserLoginRequest;
import com.taisf.services.user.dto.UserLogoutRequest;
import com.taisf.services.user.dto.UserRegistRequest;

/**
 * <p>用户</p>
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
public interface UserService {

    /**
     * 用户注册
     * @param userRegistRequest
     * @return
     */
    DataTransferObject<RegistInfoVO> regist(UserRegistRequest userRegistRequest);


    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    DataTransferObject<String> login(UserLoginRequest userLoginRequest);

    /**
     * 用户登录 [验证码登录]
     * @param userLoginRequest
     * @return
     */
    DataTransferObject<RegistInfoVO> loginByCode(UserLoginRequest userLoginRequest);



    /**
     * 用户退出
     * @param userLogoutRequest
     * @return
     */
    DataTransferObject<Void> logout(UserLogoutRequest userLogoutRequest);




    /**
     * 充值记录
     * @author afi
     * @param accountLogRequest
     * @return
     */
    DataTransferObject<PagingResult<AccountLogEntity>> rechargeLog(AccountLogRequest accountLogRequest);


    /**
     * 修改支付密码
     * @param userId
     * @param accountPassword
     * @return
     */
    DataTransferObject<Void> updateAccountPassword(String userId,String accountPassword );


    /**
     * 修改登录密码
     * @param userId
     * @param userPassword
     * @return
     */
    DataTransferObject<Void> updateUserPwd(String userId,String userPassword,String oldUserPassword);

}
