package com.taisf.services.user.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.user.entity.LoginTokenEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * <p> 用户登录token操作 </p>
 * <PRE>
 * <BR>	修改记录∫
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("user.loginTokenDao")
public class LoginTokenDao extends BaseDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(LoginTokenDao.class);

    private String SQLID = "user.loginTokenDao.";



    public int add(LoginTokenEntity loginTokenEntity){
        return mybatisDaoContext.save(SQLID + "insert", loginTokenEntity);
    }
    
    public int addSelective(LoginTokenEntity loginTokenEntity){
        Map<String,Object> par = new HashMap<>();
        return mybatisDaoContext.save(SQLID + "insertSelective", loginTokenEntity);
    }
    /**
     * 用户退出
     * @param loginTokenEntity
     * @return
     */
    public int logout(LoginTokenEntity loginTokenEntity) {
    	return mybatisDaoContext.update(SQLID + "logout", loginTokenEntity);
    }
    
    /**
     * 查询用户token是否失效
     * @param loginTokenEntity
     * @return
     */
    public Integer queryCountExpireToken(LoginTokenEntity loginTokenEntity){
        return mybatisDaoContext.findOne(SQLID + "selectExpireTokenCount", Integer.class, loginTokenEntity);
    }
    
    /**
     * 查询token信息
     * @param loginTokenEntity
     * @return
     */
    public LoginTokenEntity queryTokenBySelective(LoginTokenEntity loginTokenEntity){
        return mybatisDaoContext.findOne(SQLID + "selectBySelective", LoginTokenEntity.class, loginTokenEntity);
    }
    
    /**
     * 查询token信息
     * @param loginTokenEntity
     * @return
     */
    public int upateTokenById(LoginTokenEntity loginTokenEntity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", loginTokenEntity);
    }
    
    
    
    
    
    
    
}
