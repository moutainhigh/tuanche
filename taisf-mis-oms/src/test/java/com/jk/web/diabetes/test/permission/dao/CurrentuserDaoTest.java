package com.jk.web.diabetes.test.permission.dao;


import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.base.utils.UUIDGenerator;
import com.jk.web.diabetes.test.BaseTest;
import com.taisf.web.oms.permission.dao.CurrentuserDao;
import com.taisf.web.oms.permission.dto.CurrentuserRequest;
import com.taisf.web.oms.permission.entity.CurrentuserEntity;
import com.taisf.web.oms.permission.vo.CurrentuserVo;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>用户测试</p>
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
public class CurrentuserDaoTest extends BaseTest {

    @Resource(name = "ups.currentuserDao")
    private CurrentuserDao currentuserDao;

    @Test
    public void TestGetCurrentuserByFid() {
        CurrentuserEntity currentuserEntity = currentuserDao.getCurrentuserByFid("5c3cf8c1-e884-11e5-9cf9-0050568f07f8");

        System.out.println(JsonEntityTransform.Object2Json(currentuserEntity));
    }

    @Test
    public void insertCurrentuserTest() {
        CurrentuserEntity currentuserEntity = new CurrentuserEntity();
        currentuserEntity.setFid(UUIDGenerator.hexUUID());
        currentuserEntity.setUserAccount("busj");
        currentuserEntity.setUserPassword("123456");
        currentuserEntity.setEmployeeFid("ddddd");
        currentuserEntity.setNationCode("US");
        currentuserEntity.setProvinceCode("dddd");
        currentuserEntity.setCityCode("meiguo");
        currentuserEntity.setAreaCode("area");
        currentuserDao.insertCurrentuser(currentuserEntity);
    }

    @Test
    public void findCurrentuserPageTest() {
        CurrentuserRequest currentuserRequest = new CurrentuserRequest();
        currentuserRequest.setAccountStatus(0);
        PagingResult<CurrentuserVo> list = currentuserDao.findCurrentuserPageList(currentuserRequest);
//        System.err.println(JsonEntityTransform.Object2Json(list.getRows()));
    }

    @Test
    public void findCurrentuserByAccountTest(){
        CurrentuserEntity currentuserEntity=currentuserDao.getCurrentuserEntityByAccount("busj");
        System.err.println(JsonEntityTransform.Object2Json(currentuserEntity));
    }

    @Test
    public void updateCurrentuserByFidTest(){
    	CurrentuserEntity user = new CurrentuserEntity();
    	user.setFid("8a9e9aaf5456a3aa015456a3ab8b0001");
    	user.setAccountStatus(1);
    	int upNum = currentuserDao.updateCurrentuserByFid(user);
    	System.err.println(upNum);
    }

}
