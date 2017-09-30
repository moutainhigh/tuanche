package com.taisf.services.user.manager;

import com.jk.framework.base.utils.Check;
import com.taisf.services.user.dao.UserAccountDao;
import com.taisf.services.user.dao.UserAddressDao;
import com.taisf.services.user.dao.UserDao;
import com.taisf.services.user.entity.UserAccountEntity;
import com.taisf.services.user.entity.UserAddressEntity;
import com.taisf.services.user.entity.UserEntity;
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

}
