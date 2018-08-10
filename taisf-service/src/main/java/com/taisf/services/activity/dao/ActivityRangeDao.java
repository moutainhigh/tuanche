package com.taisf.services.activity.dao;


import com.taisf.services.activity.entity.ActivityRangeEntity;
import com.taisf.services.common.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>活动区间</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author 活动的区间  on 2018/3/21.
 * @version 1.0
 * @since 1.0
 */
@Repository("activity.activityRangeDao")
public class ActivityRangeDao extends BaseDao {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActivityRangeDao.class);

    private String SQLID = "activity.activityRangeDao.";

    /**
     * 获取当前活动的范围
     * @param actSn
     * @return
     */
    public List<ActivityRangeEntity> getActRangeBySn(String actSn){
        return mybatisDaoContext.findAll(SQLID+"getActRangeBySn",ActivityRangeEntity.class,actSn);
    }

    /**
     * 保存当前的活动区间
     * @param list
     * @return
     */
    public int saveActRangeByList(List<ActivityRangeEntity> list){
        return mybatisDaoContext.batchSave(SQLID+"saveActRange",list);
    }



}
