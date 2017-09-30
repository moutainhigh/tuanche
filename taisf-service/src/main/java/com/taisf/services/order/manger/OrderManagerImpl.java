package com.taisf.services.order.manger;

import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.valenum.AccountTypeEnum;
import com.taisf.services.order.dao.*;
import com.taisf.services.order.dto.OrderInfoRequest;
import com.taisf.services.order.entity.OrderEntity;
import com.taisf.services.order.entity.OrderMoneyEntity;
import com.taisf.services.order.entity.OrderPayEntity;
import com.taisf.services.order.entity.OrderProductEntity;
import com.taisf.services.order.vo.OrderDetailVO;
import com.taisf.services.order.vo.OrderInfoVO;
import com.taisf.services.order.vo.OrderSaveVO;
import com.taisf.services.user.dao.AccountLogDao;
import com.taisf.services.user.dao.UserAccountDao;
import com.taisf.services.user.entity.AccountLogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>订单先关操作</p>
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
@Service("order.orderManagerImpl")
public class OrderManagerImpl {

	@Resource(name = "order.orderBaseDao")
	private OrderBaseDao orderBaseDao;

	@Resource(name = "order.orderMoneyDao")
	private OrderMoneyDao orderMoneyDao;


	@Resource(name = "order.payDao")
	private OrderPayDao orderPayDao;

	@Resource(name = "order.orderProductDao")
	private OrderProductDao orderProductDao;


	@Resource(name = "user.accountLogDao")
	private AccountLogDao accountLogDao;

	@Resource(name = "user.accountUserDao")
	private UserAccountDao userAccountDao;

	@Resource(name = "order.orderInfoDao")
	private OrderInfoDao orderInfoDao;



	/**
	 * 获取当前订单的信息
	 * @author afi
	 * @param orderInfoRequest
	 * @return
	 */
	public PagingResult<OrderInfoVO> getOrderInfoPage(OrderInfoRequest orderInfoRequest){
		//分页获取信息
		PagingResult<OrderInfoVO> pagingResult = orderInfoDao.getOrderInfoPage(orderInfoRequest);
		if (Check.NuNObj(pagingResult)){
			pagingResult = new PagingResult<>();
		}
		if (Check.NuNCollection(pagingResult.getList())){
			return pagingResult;
		}

		for (OrderInfoVO infoVO : pagingResult.getList()) {
			infoVO.setList(orderProductDao.getOrderProductByOrderSn(infoVO.getOrderSn()));
		}
		return pagingResult;
	}



	/**
	 * 保存订单待DB
	 * @author afi
	 * @param saveVO
	 */
	public void saveOrderSave(OrderSaveVO saveVO){
		if (Check.NuNObj(saveVO)){
			throw new BusinessException("保存订单参数为空");
		}

		// 保存订单基本信息
		OrderEntity orderBase = saveVO.getOrderBase();
		int num = orderBaseDao.saveOrderBase(orderBase);
		if (num  != 1){
			throw new BusinessException("保存订单基本信息失败");
		}
		// 保存订单金额信息
		OrderMoneyEntity orderMoney = saveVO.getOrderMoney();
		int numM=orderMoneyDao.saveOrderMoney(orderMoney);
		if (numM  != 1){
			throw new BusinessException("保存订单金鹅信息失败");
		}
		//保存当前的订单商品信息
		List<OrderProductEntity> list = saveVO.getList();
		orderProductDao.batchSaveOrderProduct(list);

		int balanceCost = ValueUtil.getintValue(orderMoney.getPayBalance());
		if (balanceCost > 0){
			//如果消费余额大于0,直接消费余额
			this.costUserBalanceByOrderSn(saveVO.getUser().getUserUid(),saveVO.getOrderSn(),balanceCost);
		}
	}

	/**
	 * 消费当前的余额信息
	 * @author afi
	 * @param userUid
	 * @param orderSn
	 * @param money
	 */
	private void costUserBalanceByOrderSn(String userUid,String orderSn,int money){

		//消费当前的余额信息
		userAccountDao.changeUserBalance(userUid,-money);

		//记录当前的消费记录
		AccountLogEntity log = new AccountLogEntity();
		log.setAccountType(AccountTypeEnum.CONSUME.getCode());
		log.setBizMoney(money);
		log.setBizSn(orderSn);
		log.setUserId(userUid);
		accountLogDao.saveAccountLog(log);
	}

	/**
	 * 获取当前的订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderDetailVO getOrderDetailBySn(String orderSn){
		if (Check.NuNStr(orderSn)){
			return null;
		}

		OrderDetailVO orderDetailVO = new OrderDetailVO();
		//订单基本信息
		OrderEntity orderEntity = orderBaseDao.getOrderBaseByOrderSn(orderSn);
		orderDetailVO.setOrderEntity(orderEntity);
		if (Check.NuNObj(orderEntity)){
			return null;
		}
		//订单的支付信息
		OrderPayEntity orderPayEntity = orderPayDao.getOrderPayByOrderSn(orderSn);
		orderDetailVO.setOrderPayEntity(orderPayEntity);

		//订单的金额信息
		OrderMoneyEntity orderMoney = orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
		orderDetailVO.setOrderMoneyEntity(orderMoney);

		//订单的商品信息
		List<OrderProductEntity> list = orderProductDao.getOrderProductByOrderSn(orderSn);
		orderDetailVO.setList(list);

		//返回订单信息
		return orderDetailVO;
	}


}
