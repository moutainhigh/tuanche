package com.taisf.services.activity.manager;

import com.jk.framework.base.utils.Check;
import com.taisf.services.activity.dao.ActivityDao;
import com.taisf.services.activity.dao.ActivityRangeDao;
import com.taisf.services.activity.entity.ActivityEntity;
import com.taisf.services.activity.vo.ActivityVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("activity.activityManagerImpl")
public class ActivityManagerImpl {

	@Resource(name = "activity.activityDao")
	private ActivityDao activityDao;


	@Resource(name = "activity.activityRangeDao")
	private ActivityRangeDao activityRangeDao;



	/**
	 * 获取当前最新的充值活动
	 * @return
	 */
	public ActivityEntity getLastAct4Charge(){
		return activityDao.getLastAct4Charge();
	}


	/**
	 * 获取当前最新的充值活动
	 * @return
	 */
	public ActivityVO getLastAct4ChargeVo(){
		ActivityEntity activity = getLastAct4Charge();
		if (Check.NuNObj(activity)){
			return null;
		}
		ActivityVO vo = new ActivityVO();
		vo.setActivityEntity(activity);
		vo.setRangeList(activityRangeDao.getActRangeBySn(activity.getActSn()));
		return vo;
	}

}
