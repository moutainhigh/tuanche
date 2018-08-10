package com.taisf.services.activity.vo;

import com.jk.framework.base.entity.BaseEntity;
import com.taisf.services.activity.entity.ActivityEntity;
import com.taisf.services.activity.entity.ActivityRangeEntity;

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
 * @author afi on on 2018/8/10.
 * @version 1.0
 * @since 1.0
 */
public class ActivityVO  extends BaseEntity{

    /**
     * 活动类
     */
    private ActivityEntity activityEntity;

    /**
     * 区间列表
     */
    private List<ActivityRangeEntity> rangeList;

    public List<ActivityRangeEntity> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<ActivityRangeEntity> rangeList) {
        this.rangeList = rangeList;
    }

    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }

    public void setActivityEntity(ActivityEntity activityEntity) {
        this.activityEntity = activityEntity;
    }
}
