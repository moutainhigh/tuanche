package com.jk.services.payment.handle.alipay;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.jk.framework.base.utils.BigDecimalUtil;
import com.jk.framework.base.utils.DateUtil;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.services.payment.constant.PayConstants;
import com.jk.services.payment.entity.PayDetailEntity;
import com.jk.services.payment.entity.PayEntity;
import com.jk.services.payment.entity.PayInfo;
import com.jk.services.payment.exception.PaymentException;
import com.jk.services.payment.handle.PaymentHandle;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>微信app支付</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/6/13.
 * @version 1.0
 * @since 1.0
 */
@Service
public class AlipayAppHandle extends PaymentHandle {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlipayAppHandle.class);


	@Override
	public String getRequestUrl() {
		return "https://openapi.alipay.com/gateway.do";
	}

	@Override
	public RequestMethod getRequestMethod() {
		return RequestMethod.post;
	}

	@Override
	public String getRequestCharset() {
		return "utf-8";
	}

	@Override
	public Map<String, Object> getParameterMap(PayInfo payInfo, HttpServletRequest request) {
		LOGGER.info("支付宝支付发送请求参数：{}",JsonEntityTransform.Object2Json(payInfo));
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(getRequestUrl(), payInfo.getPartner(), payInfo.getPassword(), "json", "UTF-8", payInfo.getPartnerKey(), "RSA");
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest aliPayrequest = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(payInfo.getContent());
		model.setSubject("健客医生");
		model.setOutTradeNo(payInfo.getPayId().toString());
		model.setTimeoutExpress("24h");
		model.setTotalAmount(String.valueOf(BigDecimalUtil.div(payInfo.getAmount(), 100)));
		model.setProductCode("QUICK_MSECURITY_PAY");

		aliPayrequest.setBizModel(model);
		aliPayrequest.setNotifyUrl(notifyAlipayUrl);
		aliPayrequest.setReturnUrl(payInfo.getReturnUrl());
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(aliPayrequest);
			String orderString = response.getBody();
			LOGGER.info("支付宝请求结果：{}", orderString);
			parameterMap.put("orderString", orderString);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw  new PaymentException(PayConstants.PayStatus.STATUS_40000.getCodeStr(), e.getMessage());
		}
		return parameterMap;
	}

	@Override
	public String notifyHandle(PayInfo payInfo,String json){
		LOGGER.info("支付宝APP支付同步通知参数：payInfo:{},json:{}",JsonEntityTransform.Object2Json(payInfo),json);
		try {
			PayEntity pay = new PayEntity();
			Integer payId = null;
			Map<String, String> param = (Map<String, String>)JsonEntityTransform.json2Map(json);
			if("TRADE_SUCCESS".equalsIgnoreCase(param.get("trade_status"))){
				payId = Integer.parseInt(param.get("out_trade_no"));
				pay.setId(payId);
				pay.setReqId(payId.toString());
				pay.setResId(param.get("trade_no"));
				String bizTimeString = param.get("gmt_payment");
				if(StringUtils.isNotEmpty(bizTimeString)){
					pay.setBizTime(DateUtil.parseDate(bizTimeString,DateUtil.timestampPattern));
				}else{
					pay.setBizTime(new Date());
				}
				pay.setStatus(PayConstants.PayStatus.STATUS_SUCCESS.getCodeStr());
				pay.setStatusDesc(PayConstants.PayStatus.STATUS_SUCCESS.getName());

				PayDetailEntity payDetail  = new PayDetailEntity();
				payDetail.setPayId(payId);
				payDetail.setOutSerialNo(payId.toString());
				payDetail.setInSerialNo(param.get("trade_no"));
				payDetail.setStatus(pay.getStatus());
				payDetail.setStatusDesc(pay.getStatusDesc());
				payDao.update(pay);
				payDetailDao.update(payDetail);
				LOGGER.info("支付ID:{} 异步通知操作成功 ：支付宝APP支付参数：{}  ",payId,json);
				LOGGER.info("支付成功保存当前支付DB.支付ID:{} pay ：{} payDetail：{}  ",payId,JsonEntityTransform.Object2Json(pay),JsonEntityTransform.Object2Json(payDetail));
				return "success";
			}else{
				LOGGER.info(" 异步通知操作失败,支付宝APP支付参数：{}  ",json);
			}
		} catch (Exception e) {
			LOGGER.error(" 异步通知异常 ",e);
		}
		return "fail";
	}

	@Override
	public String returnHandle(PayInfo payInfo,String json) {
		return PayConstants.SUCCESS_STATUS;
	}

	@Override
	public void verifyNotify(PayInfo payInfo, String json){
	}




}
