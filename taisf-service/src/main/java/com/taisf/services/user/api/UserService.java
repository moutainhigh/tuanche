package com.taisf.services.user.api;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.taisf.services.user.dto.*;
import com.taisf.services.user.entity.AccountLogEntity;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserEntity;
import com.taisf.services.user.vo.RegistInfoVO;
import com.taisf.services.user.vo.UserAccountVO;

import java.util.List;

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
     * 禁用员工
     * @param userUid
     * @return
     */
    DataTransferObject<Void> forbiddenUser(String userUid);
    /**
     * 获取当前的账户信息
     * @param userAccountRequest
     * @return
     */
    DataTransferObject<PagingResult<UserAccountVO>> getUserAccountPage(UserAccountRequest userAccountRequest);



    /**
     * 校验电话是否注册
     * @param phone
     * @return
     */
    DataTransferObject<Void> checkRegist(String phone);

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
    DataTransferObject<String> loginByCode(UserLoginCodeRequest userLoginRequest);



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
     * 入账记录
     * @author afi
     * @param accountLogRequest
     * @return
     */
    DataTransferObject<PagingResult<AccountLogEntity>> inconmeLog(AccountLogRequest accountLogRequest);


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

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:销售管理列表
     **/
    DataTransferObject<PagingResult<UserEntity>> pageListUser(UserRequest request);


    /**
     * 销售列表
     * @param request
     * @return
     */
    DataTransferObject<PagingResult<UserEntity>> pageKnightListUser(UserRequest request);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:销售管理列表
     **/
    DataTransferObject<UserEntity> getUserById(Integer id);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:修改员工信息
     **/
    DataTransferObject<Void> updateUser(UserEntity userEntity);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:修改员工信息
     **/
    DataTransferObject<List<UserEntity>> getUserByType(Integer type);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/14
     * @description:修改员工信息
     **/
    void saveUser(UserEntity userEntity);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:企业员工管理列表
     **/
    DataTransferObject<PagingResult<UserEntity>> pageListCompanyUser(UserRequest request);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/16
     * @description:修改账户信息
     **/
    void updateAccountUser(UserAccountEntity accountUserEntity);

    /**
     * @author:zhangzhengguang
     * @date:2017/10/20
     * @description:生成二维码
     **/
    DataTransferObject<String> getQRcode(String uid);

}
