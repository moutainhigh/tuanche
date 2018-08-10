package com.taisf.services.test.activity.dao;


import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.activity.dao.ActivityDao;
import com.taisf.services.activity.entity.ActivityEntity;
import com.taisf.services.base.dao.BannerDao;
import com.taisf.services.base.dto.BannerReq;
import com.taisf.services.base.entity.BannerEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/13.
 * @version 1.0
 * @since 1.0
 */
public class ActivityDaoTest extends BaseTest {


   @Autowired
   private ActivityDao activityDao;


    @Test
    public void getLastAct4ChargeTest() {

		ActivityEntity lastAct4Charge = activityDao.getLastAct4Charge();
		System.out.println(JsonEntityTransform.Object2Json(lastAct4Charge));
    }
    

}
