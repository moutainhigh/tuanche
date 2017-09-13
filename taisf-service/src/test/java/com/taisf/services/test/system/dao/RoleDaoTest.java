package com.taisf.services.test.system.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.system.dao.RoleDao;
import com.taisf.services.system.dto.RoleRequest;
import com.taisf.services.system.entity.RoleEntity;
import com.taisf.services.system.vo.RoleVo;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * <p>角色测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/25.
 * @version 1.0
 * @since 1.0
 */
public class RoleDaoTest extends BaseTest {
    @Resource(name = "ups.roleDao")
    private RoleDao roleDao;


    @Test
    public void insertRoleTest() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setFid(UUID.randomUUID().toString());
        roleEntity.setRoleName("系统管理员");
        roleEntity.setRoleDesc("超级管理员");
        roleEntity.setIsDel(0);
        roleEntity.setCreateDate(new Date());
        roleEntity.setCreateFid("1");
        roleDao.insertRole(roleEntity);
    }

    @Test
    public void findRolePageTest() {
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setLimit(5);
        roleRequest.setPage(1);
        PagingResult<RoleVo> list = roleDao.findRolePageList(roleRequest);
        System.err.println(JsonEntityTransform.Object2Json(list));
    }
}
