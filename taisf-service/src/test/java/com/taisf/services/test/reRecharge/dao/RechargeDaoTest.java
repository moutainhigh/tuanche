package com.taisf.services.test.reRecharge.dao;

import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.recharge.dao.RechargeDao;
import com.taisf.services.recharge.entity.RechargeEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/25.
 * @version 1.0
 * @since 1.0
 */
public class RechargeDaoTest extends BaseTest {


    @Resource(name = "recharge.rechargeDao")
    private RechargeDao rechargeDao;


    @Test
    public void saveRechargeTest() {
        RechargeEntity record = new RechargeEntity();
        record.setRechargeSn("sv");
        record.setArechargeStatus(1);
        record.setBossNum(1);
        record.setCommonNum(1);
        record.setEnterpriseCode("code");
        record.setPayMoney(1);
        record.setTotalPrice(11);
        record.setEnterpriseName("name");
        record.setCreateId("creatid");
        record.setCreateName("name");
        rechargeDao.saveRecharge(record);
    }

    @Test
    public void updateRechargeTest() {
        RechargeEntity record = new RechargeEntity();
        record.setRechargeSn("sv");
        record.setArechargeStatus(1);
        record.setBossNum(1);
        record.setCommonNum(1);
        record.setEnterpriseCode("code");
        record.setPayMoney(1);
        record.setTotalPrice(11);
        record.setEnterpriseName("name");
        record.setCreateId("creatid");
        record.setCreateName("name");
        rechargeDao.updateRecharge(record);
    }


    @Test
    public void getRechargeEntityBySnTest() {

        RechargeEntity rechargeEntity = rechargeDao.getRechargeEntityBySn("sv");

        System.out.println(JsonEntityTransform.Object2Json(rechargeEntity));
    }

}
