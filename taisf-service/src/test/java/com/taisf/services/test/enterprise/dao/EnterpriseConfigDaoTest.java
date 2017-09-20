package com.taisf.services.test.enterprise.dao;

import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.enterprise.dao.EnterpriseAddressDao;
import com.taisf.services.enterprise.dao.EnterpriseConfigDao;
import com.taisf.services.enterprise.entity.EnterpriseAddressEntity;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/20.
 * @version 1.0
 * @since 1.0
 */
public class EnterpriseConfigDaoTest  extends BaseTest {


    @Resource(name = "enterprise.enterpriseConfigDao")
    private EnterpriseConfigDao enterpriseConfigDao;


    @Test
    public void saveEnterpriseConfigTest() {
        EnterpriseConfigEntity entity = new EnterpriseConfigEntity();
        entity.setEnterpriseCode("code");
        entity.setDinnerBoss(1);
        entity.setDinnerEmp(1);
        entity.setDinnerEnd(new Date());
        entity.setDinnerStart(new Date());
        entity.setLunchBoss(1);
        entity.setLunchEmp(1);
        entity.setLunchStart(new Date());
        entity.setLunchEnd(new Date());

        enterpriseConfigDao.saveEnterpriseConfig(entity);
    }


    @Test
    public void updateEnterpriseConfigTest() {

        EnterpriseConfigEntity entity = new EnterpriseConfigEntity();

        entity.setId(1);
        entity.setEnterpriseCode("code");
        entity.setDinnerBoss(1);
        entity.setDinnerEmp(1);
        entity.setDinnerEnd(new Date());
        entity.setDinnerStart(new Date());
        entity.setLunchBoss(1);
        entity.setLunchEmp(1);
        entity.setLunchStart(new Date());
        entity.setLunchEnd(new Date());

        enterpriseConfigDao.updateEnterpriseConfig(entity);
    }



    @Test
    public void getEnterpriseConfigByCodeTest() {
        EnterpriseConfigEntity rst = enterpriseConfigDao.getEnterpriseConfigByCode("code");
        System.out.println(JsonEntityTransform.Object2Json(rst));
    }



}
