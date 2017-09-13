package com.taisf.services.user.dao;

import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.user.entity.UserEntity;
import org.springframework.stereotype.Repository;


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
@Repository("core.userDao")
public class UserDao extends BaseDao{
    private String SQLID = "core.userDao.";

    
    public int add(UserEntity userEntity){
        return mybatisDaoContext.save(SQLID + "insertSelective", userEntity);
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
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", userEntity);
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
