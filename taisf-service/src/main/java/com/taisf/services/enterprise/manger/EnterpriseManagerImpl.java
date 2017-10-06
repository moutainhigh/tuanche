package com.taisf.services.enterprise.manger;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.DateUtil;
import com.taisf.services.common.valenum.DayTypeEnum;
import com.taisf.services.enterprise.dao.EnterpriseConfigDao;
import com.taisf.services.enterprise.dao.EnterpriseDao;
import com.taisf.services.enterprise.dao.EnterpriseDayDao;
import com.taisf.services.enterprise.entity.EnterpriseConfigEntity;
import com.taisf.services.enterprise.entity.EnterpriseDayEntity;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.enterprise.vo.EnterpriseInfoVO;
import com.taisf.services.order.dao.CartDao;
import com.taisf.services.order.dto.CartBaseRequest;
import com.taisf.services.order.entity.CartEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * <p>企业先关操作</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/9/23.
 * @version 1.0
 * @since 1.0
 */
@Service("enterprise.enterpriseManagerImpl")
public class EnterpriseManagerImpl {

	@Resource(name = "enterprise.enterpriseDao")
	private EnterpriseDao enterpriseDao;


	@Resource(name = "enterprise.enterpriseConfigDao")
	private EnterpriseConfigDao enterpriseConfigDao;

	@Resource(name = "enterprise.enterpriseDayDao")
	private EnterpriseDayDao enterpriseDayDao;


	/**
	 * 获取当前的企业信息
	 * @author afi
	 * @param enterpriseCode
	 * @return
	 */
	public EnterpriseEntity getEnterpriseByCode(String enterpriseCode){
		//获取购物车列表
		return enterpriseDao.getEnterpriseByCode(enterpriseCode);
	}




	/**
	 * 获取当前的企业比较全的信息
	 * @author afi
	 * @param enterpriseCode
	 * @return
	 */
	public EnterpriseInfoVO getEnterpriseInfoByCode(String enterpriseCode){
		//默认获取一个月内的配送信息
		return getEnterpriseInfoByCode(enterpriseCode,1);
	}


	/**
	 * 获取当前的企业比较全的信息
	 * @author afi
	 * @param enterpriseCode
	 * @return
	 */
	public EnterpriseInfoVO getEnterpriseInfoByCode(String enterpriseCode,int jumpMonth){
		if (Check.NuNStr(enterpriseCode)){
			return null;
		}
		//获取购物车列表
		EnterpriseEntity entity =  enterpriseDao.getEnterpriseByCode(enterpriseCode);
		if (Check.NuNObj(entity)){
			return null;
		}

		EnterpriseInfoVO info = new EnterpriseInfoVO();
		//设置基本信息
		info.setEnterpriseEntity(entity);

		//获取当前的配置信息
		EnterpriseConfigEntity enterpriseConfig = enterpriseConfigDao.getEnterpriseConfigByCode(enterpriseCode);
		if (Check.NuNObj(enterpriseConfig)){
			throw new BusinessException("异常的配置信息");
		}
		info.setEnterpriseConfigEntity(enterpriseConfig);

		//设置配送时间
		List<EnterpriseDayEntity> days = this.dealEnterpriseDays(enterpriseCode,jumpMonth);
		info.setList(days);
		return info;
	}


	/**
	 * 获取月内时间节点信息
	 * @author afi
	 * @param enterpriseCode
	 */
	public List<EnterpriseDayEntity> dealEnterpriseDays(String enterpriseCode,int jumpMonth){

		List<EnterpriseDayEntity> list = new ArrayList<>();

		Date firstDay = DateUtil.getFirstDayOfMonth(0);
		Date lastDay = DateUtil.jumpMonth(firstDay,jumpMonth);
		Map<String,EnterpriseDayEntity> map = new HashMap<>();
		//获取当前的时间表格
		List<EnterpriseDayEntity> days = enterpriseDayDao.getEnterpriseDaysByTime(enterpriseCode, firstDay, lastDay);
		if (!Check.NuNCollection(days)){
			for (EnterpriseDayEntity day : days) {
				map.put(day.getDayTime(),day);
			}
		}
		while (firstDay.before(lastDay)){
			String key = DateUtil.intFormat(firstDay)+"";
			if (map.containsKey(key)){
				//DB包含直接过
				list.add(map.get(key));
				continue;
			}
			EnterpriseDayEntity day = new EnterpriseDayEntity();
			day.setDayTime(key);
			if (DateUtil.isWorkDay(firstDay)){
				//工作日添加
				day.setDayType(DayTypeEnum.SEND.getCode());
			}else {
				//非工作日不添加
				day.setDayType(DayTypeEnum.NO.getCode());
			}
			list.add(day);
			//明天
			firstDay = DateUtil.jumpDate(firstDay,1);
		}
		return list;
	}

	public static void main(String[] args) {
		Date aa = DateUtil.getFirstDayOfMonth(0);
		System.out.println(aa);

		System.out.println(DateUtil.timestampFormat(aa));
	}

}
