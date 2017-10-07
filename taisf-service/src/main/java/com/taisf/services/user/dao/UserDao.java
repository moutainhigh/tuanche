package com.taisf.services.user.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * <p> 用户主表操作 </p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("user.userDao")
public class UserDao extends BaseDao{
    private String SQLID = "user.userDao.";

    
    public int add(UserEntity userEntity){
        return mybatisDaoContext.save(SQLID + "saveUser", userEntity);
    }
    
    public UserEntity queryBySelective(UserEntity userEntity){
        return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKeySelective", UserEntity.class, userEntity);
    }
    
    public Integer queryCountBySelective(UserEntity userEntity){
        return mybatisDaoContext.findOne(SQLID + "selectCountSelective", Integer.class, userEntity);
    }
    
    /**
     * 根据用户id更新用户信息
     * @param userEntity
     * @return
     */
    public int updateUser(UserEntity userEntity){
        return mybatisDaoContext.update(SQLID + "updateUser", userEntity);
    }


    /**
     * 根据用户userid查询用户
     * @param userId
     * @return
     */
    public UserEntity getUserByUid(String userId){

        return mybatisDaoContext.findOne(SQLID + "getUserByUid", UserEntity.class, userId);
    }


    /**
     * 激活用户信息
     * @param userId
     * @return
     */
    public int updateUser2Activity(String userId){
        Map<String,Object> par = new HashMap<>();
        par.put("userId",userId);
        return mybatisDaoContext.update(SQLID + "updateUser2Activity", par );
    }


    /**
     * 根据用户电话查询用户
     * @param userPhone
     * @return
     */
    public UserEntity getUserByUserPhone(String userPhone){
        return mybatisDaoContext.findOne(SQLID + "getUserByUserPhone", UserEntity.class, userPhone);
    }
    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    public UserEntity getUserById(Integer id){
        return mybatisDaoContext.findOne(SQLID + "selectByPrimaryKey", UserEntity.class, id);
    }



}
