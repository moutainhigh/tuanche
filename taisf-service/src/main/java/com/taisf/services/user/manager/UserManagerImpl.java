package com.taisf.services.user.manager;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.taisf.services.user.dao.*;
import com.taisf.services.user.dto.AccountLogRequest;
import com.taisf.services.user.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>用户信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/29.
 * @version 1.0
 * @since 1.0
 */
@Service("user.userManagerImpl")
public class UserManagerImpl {

    @Resource(name = "user.userDao")
    private UserDao userDao;

    @Resource(name = "user.userAddressDao")
    private UserAddressDao userAddressDao;


    @Resource(name = "user.accountUserDao")
    private UserAccountDao userAccountDao;


    @Resource(name = "user.accountLogDao")
    private AccountLogDao accountLogDao;

    @Resource(name = "user.loginTokenDao")
    private LoginTokenDao loginTokenDao;


    /**
     * 获取当前的账户的操作记录
     * @author afi
     * @param accountLogRequest
     * @return
     */
    public PagingResult<AccountLogEntity> getAccountLogByPage(AccountLogRequest accountLogRequest){
        return accountLogDao.getAccountLogByPage(accountLogRequest);
    }

    /**
     * 保存登录信息
     * @author afi
     * @param loginTokenEntity
     * @return
     */
    public int saveLoginToken(LoginTokenEntity loginTokenEntity){
        return loginTokenDao.saveLoginToken(loginTokenEntity);
    }

    /**
     * 查询token信息
     * @param id
     * @return
     */
    public int deleteById(Integer id){
        return loginTokenDao.deleteById(id);
    }

    /**
     * 查询token信息
     * @param token
     * @return
     */
    public LoginTokenEntity getTokenByToken(String token){
        return loginTokenDao.getTokenByToken(token);
    }

    /**
     * 查询token信息
     * @param userId
     * @param deviceUuid
     * @return
     */
    public LoginTokenEntity getToken(String userId, String deviceUuid,int loginSource){
        return loginTokenDao.getToken(userId,deviceUuid,loginSource);
    }



    /**
     * 获取当前的账户信息,如果当前账户信息不存在直接创建账户信息
     * @author afi
     * @param userId
     * @return
     */
    public UserAccountEntity fillAndGetAccountUser(String userId){
        /**
         * 当前的账户信息是否存在
         */
        UserAccountEntity has =  userAccountDao.getAccountUserByUid(userId);

        if (Check.NuNObj(has)){
            has = new UserAccountEntity();
            has.setUserId(userId);
            userAccountDao.saveAccountUser(has);
        }
        return has;
    }


    /**
     * 获取当前收货的信息
     * @author afi
     * @param fid
     * @return
     */
    public UserAddressEntity getUserAddressByFid(String fid){
        if(Check.NuNStr(fid)){
            return null;
        }
        return userAddressDao.getUserAddressByFid(fid);
    }

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    public UserEntity getUserById(Integer id){
        if (Check.NuNObj(id)){
            return null;
        }
        return userDao.getUserById(id);
    }


    /**
     * 根据用户userid查询用户
     * @param userId
     * @return
     */
    public UserEntity getUserByUid(String userId){
        if (Check.NuNStr(userId)){
            return null;
        }
        return userDao.getUserByUid(userId);
    }


    /**
     * 根据用户电话查询用户
     * @param userPhone
     * @return
     */
    public UserEntity getUserByUserPhone(String userPhone){
        if (Check.NuNStr(userPhone)){
            return null;
        }
        return userDao.getUserByUserPhone(userPhone);
    }


    /**
     * 激活用户信息
     * @param userId
     * @return
     */
    public int updateUser2Activity(String userId){
        return userDao.updateUser2Activity(userId);
    }
}
