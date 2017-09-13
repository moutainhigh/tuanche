package com.taisf.services.test.system.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.base.utils.UUIDGenerator;
import com.taisf.services.system.dao.EmployeeDao;
import com.taisf.services.system.dao.EmployeeRoleDao;
import com.taisf.services.system.dto.EmployeeRequest;
import com.taisf.services.system.entity.EmployeeEntity;
import com.taisf.services.system.entity.EmployeeRoleEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>用户测试</p>
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
public class EmployeeDaoTest extends BaseTest {

    @Resource(name="ups.employeeDao")
    private EmployeeDao employeeDao;
    
    @Resource(name="ups.employeeRoleDao")
    private EmployeeRoleDao employeeRoleDao;

    @Test
    public void insertEmployeeSyscTest(){
        EmployeeEntity entity=new EmployeeEntity();
        entity.setUserId(UUIDGenerator.hexUUID());
        entity.setEmpCode("code");
        entity.setEmpMail("mail");
        entity.setEmpMobile("mobile");
        entity.setDepartName("name");
        entity.setEmpName("姓名");
        entity.setEmpSex(1);
        entity.setUserPwd("123456");
        int num = employeeDao.insertEmployeeSysc(entity);
        System.out.println(num);
    }
    
    @Test
    public void insertEmployeeRoleTest(){
        EmployeeRoleEntity entity=new EmployeeRoleEntity();
        entity.setUserId(UUIDGenerator.hexUUID());
       
        entity.setRoleFid("l1111");
        int num = employeeRoleDao.insertEmployeeRole(entity);
        System.out.println(num);
    }
    
    @Test
    public void testdeleteEmployeeRole(){
        String userId = "4028a39a5b8eadc4015b8eadc4df0000";
        int num = employeeRoleDao.deleteEmployeeRole(userId);
        System.out.println(num);
    }



    @Test
    public void findEmployeeByMailTest(){
        List<EmployeeEntity> row = employeeDao.findEmployeeByMail("mail");
        System.out.println(JsonEntityTransform.Object2Json(row));
    }

    @Test
    public void getEmployeeForPageTest(){
        EmployeeRequest entity=new EmployeeRequest();
        entity.setEmpMail("mail");
        entity.setEmpMobile("mobile");
        entity.setEmpName("姓名");
        PagingResult<EmployeeEntity> row = employeeDao.getEmployeeForPage(entity);
        System.out.println(JsonEntityTransform.Object2Json(row));
    }

    @Test
    public void getEmployeeByDepartIdTest(){

        List<EmployeeEntity> row = employeeDao.findEmployeeByDepartId("0");
        System.out.println(JsonEntityTransform.Object2Json(row));
    }


    @Test
    public void getEmployeeByNameTest(){

        List<EmployeeEntity> row = employeeDao.findEmployeeByName("张");
        System.out.println(JsonEntityTransform.Object2Json(row));
    }

}
