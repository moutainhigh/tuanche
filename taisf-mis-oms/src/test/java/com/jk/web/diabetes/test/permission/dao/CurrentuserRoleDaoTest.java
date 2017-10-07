package com.jk.web.diabetes.test.permission.dao;

import com.jk.web.diabetes.test.BaseTest;
import com.taisf.web.oms.permission.dao.CurrentuserRoleDao;
import com.taisf.web.oms.permission.entity.CurrentuserRoleEntity;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>角色测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CurrentuserRoleDaoTest extends BaseTest {


    @Resource(name = "ups.currentuserRoleDao")
    private CurrentuserRoleDao currentuserRoleDao;


    @Test
    public void TestInsertCurrentuserRole() {
        CurrentuserRoleEntity currentuserRoleEntity = new CurrentuserRoleEntity();
        currentuserRoleEntity.setRoleFid("aaa");
        currentuserRoleEntity.setCurrenuserFid("bbbb");
        currentuserRoleDao.insertCurrentuserRole(currentuserRoleEntity);
    }

}
