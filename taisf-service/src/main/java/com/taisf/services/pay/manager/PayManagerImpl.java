package com.taisf.services.pay.manager;

import com.jk.framework.base.utils.Check;
import com.taisf.services.common.valenum.OrdersStatusEnum;
import com.taisf.services.order.dao.OrderBaseDao;
import com.taisf.services.pay.dao.PayRecordDao;
import com.taisf.services.pay.entity.PayRecordEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Service("pay.payManagerImpl")
public class PayManagerImpl {

	@Resource(name = "pay.payRecordDao")
	private PayRecordDao payRecordDao;

	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;

	/**
	 * 获取当前的配置信息
	 * @author afi
	 * @param bizSn
	 * @return
	 */
	public PayRecordEntity getPayRecordBySn(String bizSn){
		return payRecordDao.getPayRecordBySn(bizSn);
	}


	/**
	 * 保存支付回调信息
	 * @author afi
	 * @param recordEntity
	 * @return
	 */
	public int savePayRecord(PayRecordEntity  recordEntity){
		if (Check.NuNObj(recordEntity.getCreateTime())){
			recordEntity.setCreateTime(new Date());
		}
		return payRecordDao.savePayRecord(recordEntity);
	}

	/**
	 * 保存支付回调信息
	 * @author afi
	 * @param recordEntity
	 * @return
	 */
	public int updateOrderPay(PayRecordEntity  recordEntity){
		if (Check.NuNObj(recordEntity.getCreateTime())){
			recordEntity.setCreateTime(new Date());
		}
		//添加记录
		int num = payRecordDao.savePayRecord(recordEntity);
		if (num ==1){
			orderBaseDao.payOrder(recordEntity.getOrderSn(), OrdersStatusEnum.NO_PAY.getCode());
		}
		return num;
	}

}
