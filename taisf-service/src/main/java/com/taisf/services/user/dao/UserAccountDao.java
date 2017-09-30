package com.taisf.services.user.dao;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.user.entity.UserAccountEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>用户的账户信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/21.
 * @version 1.0
 * @since 1.0
 */
@Repository("user.accountUserDao")
public class UserAccountDao extends BaseDao {


    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(UserAccountDao.class);

    private String SQLID = "user.accountUserDao.";


    /**
     * 获取当前的账户信息
     * @author afi
     * @param userId
     * @return
     */
    public UserAccountEntity getAccountUserByUid(String userId){
        return mybatisDaoContext.findOne(SQLID + "getAccountUserByUid",UserAccountEntity.class,userId);
    }



    /**
     * 保存当前的账户信息
     * @author afi
     * @param accountUserEntity
     * @return
     */
    public int saveAccountUser(UserAccountEntity  accountUserEntity){
        if (Check.NuNObj(accountUserEntity.getCreateTime())){
            accountUserEntity.setCreateTime(new Date());
        }
        return mybatisDaoContext.save(SQLID + "saveAccountUser",accountUserEntity);
    }


    /**
     * 修改当前的账户信息
     * @author afi
     * @param accountUserEntity
     * @return
     */
    public int updateAccountUser(UserAccountEntity  accountUserEntity){
        if (Check.NuNObjs(accountUserEntity.getUserId())){
            throw new BusinessException("更新数据 id 为空,par:"+ JsonEntityTransform.Object2Json(accountUserEntity));
        }
        return mybatisDaoContext.update(SQLID + "updateAccountUser",accountUserEntity);
    }




    /**
     * 冻结当前的支付密码
     * @author afi
     * @param userId
     * @param accountPassword
     * @return
     */
    public int updateAccountPassword(String userId,String accountPassword){
        if (Check.NuNObjs(userId)){
            throw new BusinessException("更新数据 id 为空,par:"+ JsonEntityTransform.Object2Json(userId));
        }
        Map<String,Object> par = new HashMap();
        par.put("userId",userId);
        par.put("accountPassword",accountPassword);
        return mybatisDaoContext.update(SQLID + "updateAccountPassword",par);
    }


    /**
     * 冻结当前的金额
     * @author afi
     * @param userId
     * @param money
     * @return
     */
    public int frozenUserBalance(String userId,int money){
        if (Check.NuNObjs(userId)){
            throw new BusinessException("更新数据 id 为空,par:"+ JsonEntityTransform.Object2Json(userId));
        }
        Map<String,Object> par = new HashMap();
        par.put("userId",userId);
        par.put("money",money);
        return mybatisDaoContext.update(SQLID + "frozenUserBalance",par);
    }


    /**
     * 充值当前金额
     * @author afi
     * @param userId
     * @param money
     * @return
     */
    public int fillUserBalance(String userId,int money){
        if (Check.NuNObjs(userId)){
            throw new BusinessException("更新数据 id 为空,par:"+ JsonEntityTransform.Object2Json(userId));
        }
        Map<String,Object> par = new HashMap();
        par.put("userId",userId);
        par.put("money",money);
        return mybatisDaoContext.update(SQLID + "fillUserBalance",par);
    }

}
