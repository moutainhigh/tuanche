package com.taisf.services.test.activity.dao;


import com.jk.framework.base.utils.JsonEntityTransform;
import com.taisf.services.activity.dao.ActivityDao;
import com.taisf.services.activity.dao.ActivityRangeDao;
import com.taisf.services.activity.entity.ActivityEntity;
import com.taisf.services.activity.entity.ActivityRangeEntity;
import com.taisf.services.test.common.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
 * @author afi on on 2017/3/13.
 * @version 1.0
 * @since 1.0
 */
public class ActivityRangeDaoTest extends BaseTest {


   @Autowired
   private ActivityRangeDao activityRangeDao;


    @Test
    public void saveActRangeByListTest() {

        List<ActivityRangeEntity> list = new ArrayList<>();
        ActivityRangeEntity range = new ActivityRangeEntity();
        range.setActSn("afi");
        range.setRangeLimit(100);
        range.setRangeMoney(1);
        list.add(range);

		Integer num = activityRangeDao.saveActRangeByList(list);
		System.out.println(JsonEntityTransform.Object2Json(num));
    }


    @Test
    public void getActRangeBySnTest() {


        List<ActivityRangeEntity> num = activityRangeDao.getActRangeBySn("afi");
        System.out.println(JsonEntityTransform.Object2Json(num));
    }



}
