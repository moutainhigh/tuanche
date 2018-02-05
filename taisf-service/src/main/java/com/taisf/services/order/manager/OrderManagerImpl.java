package com.taisf.services.order.manager;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.exception.BusinessException;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.SnUtil;
import com.jk.framework.base.utils.ValueUtil;
import com.taisf.services.common.valenum.AccountTypeEnum;
import com.taisf.services.common.valenum.OrdersStatusEnum;
import com.taisf.services.common.valenum.RecordPayTypeEnum;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.vo.EnterpriseOrderStatsVO;
import com.taisf.services.order.dao.*;
import com.taisf.services.order.dto.*;
import com.taisf.services.order.entity.*;
import com.taisf.services.order.vo.*;
import com.taisf.services.pay.dao.PayRecordDao;
import com.taisf.services.pay.entity.PayRecordEntity;
import com.taisf.services.refund.constants.RefundStatusEnum;
import com.taisf.services.refund.dao.RefundDao;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.user.dao.AccountLogDao;
import com.taisf.services.user.dao.UserAccountDao;
import com.taisf.services.user.entity.AccountLogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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


	@Resource(name = "refund.refundDao")
	private RefundDao refundDao;


	@Resource(name = "order.orderLogDao")
	private OrderLogDao orderLogDao;


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

	@Resource(name = "pay.payRecordDao")
	private PayRecordDao payRecordDao;



	/**
	 * 获取企业订单的统计信息
	 * @author afi
	 * @param request
	 * @return
	 */
	public List<EnterpriseOrderStatsVO> getEnterpriseOrderStats(EnterpriseStatsRequest request){

		return orderInfoDao.getEnterpriseOrderStats(request);
	}


	/**
	 * 申请退款
	 * @param order
	 * @param payRecord
	 */
	public  String refundOrder(OrderEntity order,PayRecordEntity payRecord){
		int count = orderBaseDao.refundOrder(order.getOrderSn(),order.getOrderStatus());
		if (count == 1){
			//生成退款
			RefundEntity entity = new RefundEntity();
			entity.setSourceType(order.getOrderSource());
			entity.setOrderSn(order.getOrderSn());
			entity.setCardNo(payRecord.getTradeNo());
			entity.setCardType(payRecord.getPayType());
			entity.setRecordId(payRecord.getId());
			entity.setRefundFee(payRecord.getTotalFee());
			entity.setPayFee(payRecord.getTotalFee());
			entity.setRefundName(order.getUserName());
			entity.setRefundSn(SnUtil.getRefundSn());
			entity.setRefundUid(order.getUserUid());
			entity.setRefundStatus(RefundStatusEnum.WAIT.getCode());
			entity.setSupplierCode(order.getSupplierCode());
			int row =  refundDao.saveRefund(entity);
			return entity.getRefundSn();
		}
		return null;
	}



	/**
	 * 结束当前订单
	 * @param finishOrderRequest
	 * @param base
	 */
	public  void finishOrder(FinishOrderRequest finishOrderRequest,OrderEntity base){
		int count = orderBaseDao.finishOrder(base.getOrderSn(),base.getOrderStatus(),finishOrderRequest.getOpId());
		if (count == 1){
			OrderLogEntity log = new OrderLogEntity();
			log.setOrderSn(base.getOrderSn());
			log.setFromStatus(base.getOrderStatus());
			log.setToStatus(OrdersStatusEnum.RECEIVE.getCode());
			log.setCreateId(finishOrderRequest.getOpId());
			log.setCreateDate(new Date());
			log.setTitle("配送完成");
			orderLogDao.insertOrderLog(log);
		}
	}


	/**
	 * 获取当前用户的待完成订单
	 * @param userUid
	 * @return
	 */
	public List<OrderInfoVO> getOrderInfoWaitingList(String userUid){
		//获取列表
		List<OrderInfoVO> waitingList = orderInfoDao.getOrderInfoWaitingList(userUid);
		if (Check.NuNCollection(waitingList)){
			return waitingList;
		}
		for (OrderInfoVO infoVO : waitingList) {
			infoVO.setList(orderProductDao.getOrderProductByOrderSn(infoVO.getOrderSn()));
		}
		return waitingList;
	}

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

	public PagingResult<OrderInfoVO> getOrderListPageByEnterprisCode(OrderInfoRequest orderInfoRequest){
		//分页获取信息
		PagingResult<OrderInfoVO> pagingResult = orderInfoDao.getOrderListPageByEnterprisCode(orderInfoRequest);
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
	 * @author:zhangzhengguang
	 * @date:2017/10/16
	 * @description:分页查询订单列表
	 **/
	public PagingResult<OrderInfoVO> pageListOrder(OrderInfoRequest orderInfoRequest){
		PagingResult<OrderInfoVO> pagingResult = orderInfoDao.pageListOrder(orderInfoRequest);
		return pagingResult;
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/18
	 * @description:企业订单配送
	 **/
	public PagingResult<OrderSendStatsVo> finOrderDistributionList(EnterpriseListRequest enterpriseListRequest){
		PagingResult<OrderSendStatsVo> orderListVoPagingResult = orderInfoDao.finOrderDistributionList(enterpriseListRequest);
		return orderListVoPagingResult;
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/17
	 * @description:分页查询订单详情商品列表
	 **/
	public PagingResult<OrderProductEntity>  getOrderProductPageList(OrderProductListRequest orderProductListRequest){
		PagingResult<OrderProductEntity> orderProductPageList = orderProductDao.getOrderProductPageList(orderProductListRequest);
		return orderProductPageList;
	}

	/**
	 * 获取当前的统计情况
	 * @param request
	 * @return
	 */
	public List<DayTaskVO>  getEverydayTaskPgeList(DayTaskRequest request){
		List<DayTaskVO> list = orderProductDao.getEverydayTaskPgeList(request);
		return list;
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/18
	 * @description:修改订单状态根据企业编号
	 **/
	public void sendByEnterpriseCode(OrderEntity orderEntity){
		orderInfoDao.sendByEnterpriseCode(orderEntity);
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/19
	 * @description:据企业code查询企业下所有待配送订单
	 **/
	public PagingResult<OrderEntity>  findListByEnterpriseCode(OrderInfoRequest orderInfoRequest){
		PagingResult<OrderEntity> listByEnterpriseCode = orderInfoDao.findListByEnterpriseCode(orderInfoRequest);
		return listByEnterpriseCode;
	}

	/**
	 * @author:zhangzhengguang
	 * @date:2017/10/20
	 * @description:配送记录
	 **/
	public PagingResult<OrderListVo>  findPageLsit(EnterpriseListRequest enterpriseListRequest){
		PagingResult<OrderListVo> pagingResult = orderInfoDao.findPageLsit(enterpriseListRequest);
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

			//保存支付记录
			PayRecordEntity record = new PayRecordEntity();
			record.setRecordUid(saveVO.getUser().getUserUid());
			record.setNeedMoney(balanceCost);
			record.setOrderSn(saveVO.getOrderSn());
			record.setPayTime(new Date());
			record.setPayType(RecordPayTypeEnum.YUE.getCode());
			record.setTotalFee(balanceCost);
			record.setTradeNo(saveVO.getOrderSn());
			record.setPayCode(DataTransferObject.SUCCESS +"");
			payRecordDao.savePayRecord(record);

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
		log.setBizMoney(-money);
		log.setBizSn(orderSn);
		log.setUserId(userUid);
		log.setTitle("下单消费");
		accountLogDao.saveAccountLog(log);
	}

	/**
	 * 获取当前的订单金额信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderMoneyEntity getOrderMoneyByOrderSn(String orderSn){
		if (Check.NuNStr(orderSn)){
			return null;
		}
		return orderMoneyDao.getOrderMoneyByOrderSn(orderSn);
	}


	/**
	 * 获取当前的订单基本信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderEntity getOrderBaseBySn(String orderSn){
		if (Check.NuNStr(orderSn)){
			return null;
		}
		return orderBaseDao.getOrderBaseByOrderSn(orderSn);
	}



	/**
	 * 获取当前的订单信息
	 * @author afi
	 * @param orderSn
	 * @return
	 */
	public OrderInfoVO getOrderInfoByOrderSn(String orderSn){
		if (Check.NuNStr(orderSn)){
			return null;
		}
		return orderInfoDao.getOrderInfoByOrderSn(orderSn);
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

		//支付信息
		PayRecordEntity record = payRecordDao.getPayRecordByOrderSn(orderSn);
		if (!Check.NuNObj(record)){
			RecordPayTypeEnum payTypeEnum = RecordPayTypeEnum.getTypeByCode(record.getPayType());
			if (!Check.NuNObj(payTypeEnum)){
				orderDetailVO.setPayInfo(payTypeEnum.getName());
			}
		}

		//返回订单信息
		return orderDetailVO;
	}


	/**
	 * 更新订单的信息
	 * @author afi
	 * @param orderEntity
	 * @return
	 */
	public int updateOrderBaseByOrderSn(OrderEntity orderEntity){
		return orderBaseDao.updateOrderBaseByOrderSn(orderEntity);
	}



}
