package com.taisf.services.activity.dao;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;

import com.taisf.services.activity.constant.ActStatusEnum;
import com.taisf.services.activity.entity.ActivityEntity;
import com.taisf.services.common.dao.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lunan  on 2018/3/21.
 * @version 1.0
 * @since 1.0
 */
@Repository("activity.activityDao")
public class ActivityDao extends BaseDao {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(ActivityDao.class);

    private String SQLID = "activity.activityDao.";

    /**
     * 获取活动
     * @param actSn
     * @return
     */
    public ActivityEntity getActDetailBySn(String actSn){
        return mybatisDaoContext.findOne(SQLID+ "getActDetailBySn",ActivityEntity.class,actSn);
    }


    /**
     * 获取当前最新的充值活动
     * @return
     */
    public ActivityEntity getLastAct4Charge(){
        Map<String,Object> par = new HashMap<>();
        par.put("actStatus", ActStatusEnum.ENABLE.getCode());

        return mybatisDaoContext.findOne(SQLID+"getLastAct4Charge",ActivityEntity.class,par);
    }


}
