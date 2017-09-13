package com.taisf.services.test.base.dao;

import com.jk.framework.base.head.Header;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.base.dao.UpgradeDao;
import com.taisf.services.base.entity.UpgradeEntity;
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
 * @author afi on on 2017/3/27.
 * @version 1.0
 * @since 1.0
 */
public class UpgradeDaoTest extends BaseTest {



    @Resource(name = "basedata.upgradeDao")
    private UpgradeDao upgradeDao;


    @Test
    public void getVersionTest() {
        Header header = new Header();
        header.setAppName("123");
        header.setChannelId("10000");
        header.setSource(1);
        header.setApplicationCode("jkHospital");
        UpgradeEntity aa=  upgradeDao.getVersion(header);
        System.out.println(JsonEntityTransform.Object2Json(aa));
    }

}
