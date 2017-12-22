package com.jk.services.payment.handle.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jk.framework.base.utils.*;
import com.jk.framework.common.utils.CloseableHttpsUtil;
import com.jk.services.payment.constant.PayConstants;
import com.jk.services.payment.constant.TenRefundEnum;
import com.jk.services.payment.entity.PayDetailEntity;
import com.jk.services.payment.entity.PayEntity;
import com.jk.services.payment.entity.PayInfo;
import com.jk.services.payment.exception.PaymentException;
import com.jk.services.payment.handle.PaymentHandle;
import com.jk.services.payment.handle.tenpay.TenpayAppHandle;
import com.jk.services.payment.utils.PayUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * <p>支付宝申请退款</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/10/26.
 * @version 1.0
 * @since 1.0
 */

@Service
public class AliRefundHandle extends PaymentHandle {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenpayAppHandle.class);

    @Override
    public String getRequestUrl() {
        return "https://openapi.alipay.com/gateway.do";
    }



    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.get;
    }

    @Override
    public String getRequestCharset() {
        return "utf-8";
    }

    /**
     * 保存请求数据
     * @param payInfo
     * @throws Exception
     */
    public String doRefundBusiness(PayInfo payInfo) throws Exception {
        /**
         * 保存业务数据
         */
        this.doBusiness(payInfo);

        /**
         * 处理退款信息
         */
        return this.processRefund(payInfo);
    }


    /**
     * 处理退款信息
     * @param payInfo
     */
    private String processRefund(PayInfo payInfo) throws Exception {
        try{
            // 根据biz_id 查找 付款payinfo
            Map<String, Object> queryMap  = new HashMap<String, Object>();
            queryMap.put("resId", payInfo.getOrgInSerialNo());
            queryMap.put("bizId", payInfo.getOrgBizId());
            PayEntity hasPay = this.payDao.selectByResIdAndBizId(queryMap);
            if(hasPay == null){
                LOGGER.error("没有获取payId=" + payInfo.getPayId() + "的相关pay支付信息.");
                return PayConstants.FAIL;
            }
            PayDetailEntity payDetail = payDetailDao.selectByPayId(hasPay.getId());
            if (payDetail == null) {
                LOGGER.error("未获取payId=" + payInfo.getPayId() + "的相关payDetail信息.");
                return PayConstants.FAIL;
            }
            if(StringUtils.isEmpty(payDetail.getOutSerialNo())){
                LOGGER.error("未获取payId=" + payInfo.getPayId() + "的微信退款单号refund_id为空.");
                return PayConstants.FAIL;
            }

            //返回消息
            String rst = null;
            // 组装请求参数
            SortedMap<String, Object> param = getParameterMap(payInfo,payDetail);
            LOGGER.info("包装请求参数完成,par:{}",JsonEntityTransform.Object2Json(param));
            //实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(getRequestUrl(), payInfo.getPartner(), payInfo.getPassword(), "json", "UTF-8", payInfo.getPartnerKey(), "RSA");
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            request.setBizContent(JsonEntityTransform.Object2Json(param));
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                rst = PayConstants.SUCCESS;
            } else {
                rst =  response.getSubMsg();
            }
            //处理退款的回调结果信息
            this.processReturnMsg(response,payInfo);

            LOGGER.info("处理请求支付ID={}任务完成！,返回结果:{}", payInfo.getPayId(),rst);
            return  rst;
        }catch (Exception e){
            LOGGER.error("发送支付宝退款,ID="+payInfo.getPayId()+",异常"+e.getMessage(),e);
            throw new Exception(e.getMessage());
        }
    }



    /**
     * 处理退款结果
     * @param response
     * @param payInfo
     * @throws Exception
     */
    private void processReturnMsg(AlipayTradeRefundResponse response,PayInfo payInfo) throws Exception {
        try {
            //返回code
            String code = null;
            //返回code
            String codeDes = null;
            //返回结果
            String result = null;

            LOGGER.info("处理支付宝退款结果开始.");
            if (response.isSuccess()){
                //成功
                code = PayConstants.PayStatus.STATUS_SUCCESS.getCodeStr();
                codeDes = PayConstants.PayStatus.STATUS_SUCCESS.getName();
                result = PayConstants.SUCCESS;
            }else {
                //失败
                code = PayConstants.PayStatus.STATUS_40000.getCodeStr();
                codeDes = PayConstants.PayStatus.STATUS_40000.getName();
                result = response.getSubMsg();
            }
            String refund_id = response.getTradeNo();
            // 更新支付信息和新增日志
            savePayAndLog(payInfo.getPayId(),code,codeDes,PayConstants.RESPONSE,
                    result,
                    refund_id);

            PayDetailEntity payDetail = new PayDetailEntity();
            payDetail.setStatus(code);
            payDetail.setStatusDesc(codeDes);
            payDetail.setOutSerialNo(refund_id);
            payDetail.setPayId(payInfo.getPayId());
            payDetailDao.update(payDetail);

            LOGGER.info("处理退款结果结束.");
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw new Exception("处理退款结果失败,"+e.getMessage());
        }
    }


    @Override
    public String notifyHandle(PayInfo payInfo, String result) {
        try {
            LOGGER.info("支付宝退款切面:payInfo:{},result:{}",JsonEntityTransform.Object2Json(payInfo),result);
            if (PayConstants.SUCCESS.equals(result)){
                LOGGER.info("支付宝退款切面 成功:payInfo:{},result:{}",JsonEntityTransform.Object2Json(payInfo),result);
                return PayConstants.SUCCESS;
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        LOGGER.info("支付宝退款切面 失败:payInfo:{},result:{}",JsonEntityTransform.Object2Json(payInfo),result);
        return PayConstants.FAIL;
    }


    /**
     * 支付宝的重试
     */
    public void doTask() {
        try {
            LOGGER.info("支付宝退款查询任务开始：{}", DateUtil.timestampFormat(new Date()));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", PayConstants.HandleType.Handle_alipay_Refund.getCode());
            List<PayInfo> list = payDao.queryBankListByCode(map);
            if (Check.NuNCollection(list)){
                LOGGER.info("待微信退款的数据0：{}");
                return;
            }
            for(PayInfo payInfo : list){
                String rst = processRefund(payInfo);
                //调用通知机制
                this.notifyHandle(payInfo,rst);
            }
            LOGGER.info("微信退款查询任务结束：{}", DateUtil.timestampFormat(new Date()));
        }catch (Exception e){
            LOGGER.error("查询退款失败,",e);
        }
    }



    /**
     * 保存业务数据
     * @param payInfo
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void doBusiness(PayInfo payInfo) throws Exception {
        try {
            //支付宝退款
            payInfo.setCode(PayConstants.HandleType.Handle_alipay_Refund.getCode());
            PayEntity has = payDao.selectByInfoWithoutMoney(payInfo);
            if(has != null){
                payInfo.setPayId(has.getId());
                return;
            }
            LOGGER.info("业务ID:{} , 保存支付宝退款信息:{}",payInfo.getBizId(), JsonEntityTransform.Object2Json(payInfo));
            PayEntity pay = new PayEntity();
            pay.setContent(payInfo.getContent());
            pay.setAmount(payInfo.getAmount());
            pay.setBizId(payInfo.getBizId());
            pay.setCode(payInfo.getCode());
            //退款
            pay.setType(PayConstants.OpType.TYPE_REFUND.getCode());
            pay.setProductCode(payInfo.getProductCode());
            pay.setReturnUrl(payInfo.getReturnUrl());
            pay.setNotifyUrl(payInfo.getNotifyUrl());
            pay.setStatus(PayConstants.PayStatus.STATUS_10000.getCodeStr());
            pay.setStatusDesc(PayConstants.PayStatus.STATUS_10000.getName());
            pay.setResId(payInfo.getOrgInSerialNo());
            pay.setReqId(payInfo.getOrgBizId());
            pay.setAccountId(payInfo.getAccountId());
            payDao.insert(pay);

            //支付明细
            PayDetailEntity payDetail =new PayDetailEntity();
            payDetail.setAmount(payInfo.getAmount());
            payDetail.setBizId(payInfo.getOrgBizId());
            payDetail.setBizName(payInfo.getContent());
            payDetail.setStatus(PayConstants.PayStatus.STATUS_10000.getCodeStr());
            payDetail.setStatusDesc(PayConstants.PayStatus.STATUS_10000.getName());
            payDetail.setPayId(pay.getId());
            payDetail.setInSerialNo(payInfo.getOrgInSerialNo());
            payDetailDao.insert(payDetail);
            payInfo.setPayId(pay.getId());

            LOGGER.info("支付Id= {} ,保存支付宝退款信息成功！", payInfo.getPayId());

        }catch (Exception e){
            LOGGER.error("保存支付ID="+payInfo.getPayId()+",异常"+e.getMessage(),e);
            throw new Exception("保存退款请求结果失败,"+e.getMessage());
        }
    }


    /**
     *  参考官方文档：https://docs.open.alipay.com/api_1/alipay.trade.refund/
     * @param payInfo
     * @param payDetail
     * @return
     */
    private SortedMap<String, Object> getParameterMap(PayInfo payInfo,PayDetailEntity payDetail) throws Exception{
        try{
            SortedMap<String,Object> parameterMap = new TreeMap<>();
            // 公众账号ID
            parameterMap.put("out_trade_no", payDetail.getPayId());
            parameterMap.put("trade_no", payDetail.getInSerialNo());
            parameterMap.put("refund_amount", BigDecimalUtil.div(payDetail.getAmount(),100));
            parameterMap.put("refund_reason", "正常退款");
            parameterMap.put("operator_id", "bj_001");
            parameterMap.put("store_id", "bj_001");
            parameterMap.put("terminal_id", "bj_001");
            return parameterMap;
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
            throw new Exception("组装请求参数失败,"+e.getMessage());
        }
    }

    @Override
    public String returnHandle(PayInfo payInfo, String json) {
        return PayConstants.SUCCESS_STATUS;
    }


    @Override
    public Map<String, Object> getParameterMap(PayInfo payInfo, HttpServletRequest request) {
        Map<String,Object> parameterMap = new TreeMap<String,Object>();
        return parameterMap;
    }



    @Override
    public void verifyNotify(PayInfo payInfo, String json) {}

}
