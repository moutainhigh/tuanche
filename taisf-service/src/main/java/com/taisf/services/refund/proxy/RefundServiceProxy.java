package com.taisf.services.refund.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.refund.api.RefundService;
import com.taisf.services.refund.constants.RefundStatusEnum;
import com.taisf.services.refund.dto.RefundQueryRequest;
import com.taisf.services.refund.dto.RefundRequest;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.refund.manager.RefundManagerImpl;
import com.taisf.services.refund.vo.RefundVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author:zhangzhengguang
 * @date:2017/12/21
 * @description:退款
 **/
@Service("refund.refundServiceProxy")
public class RefundServiceProxy implements RefundService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RefundServiceProxy.class);


    @Resource(name = "refund.refundServiceImpl")
    private RefundManagerImpl refundManagerImpl;

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:分页查询退款列表
     **/
    @Override
    public DataTransferObject<PagingResult<RefundVo>> refundPageList(RefundQueryRequest request) {
        DataTransferObject<PagingResult<RefundVo>> dto = new DataTransferObject();
        if (Check.NuNObj(request)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            PagingResult<RefundVo> refundEntityPagingResult = refundManagerImpl.refundPageList(request);
            dto.setData(refundEntityPagingResult);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("分页查询退款列表失敗");
            return dto;
        }
        return dto;
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据ID查询
     **/
    @Override
    public DataTransferObject<RefundEntity> findRefundById(Integer id) {
        DataTransferObject<RefundEntity> dto = new DataTransferObject();
        if (Check.NuNObj(id)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            RefundEntity refundEntity = refundManagerImpl.findRefundById(id);
            if (Check.NuNObj(refundEntity)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setErrorMsg("根据ID查询退款详情失败");
                return dto;
            }
            dto.setData(refundEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "根据ID查询退款详情失败 error:{}{}", e, id);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("根据ID查询退款详情失败");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/12
     * @description:根据ID修改
     **/
    @Override
    public DataTransferObject<Void> updateRefund(RefundEntity refundEntity) {
        DataTransferObject<Void> dto = new DataTransferObject();
        if (Check.NuNObj(refundEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {

            //1.校验退款单

            //2.退款单状态

            //3. 退款单金额 还有订单的金额的比对

            //4. 订单状态校验

            //5. 修改退款单

            //6. 如果驳回 修改订单的状态 REFUND_NO

            //7. 如果正常 不用修改

            int num = refundManagerImpl.updateRefund(refundEntity);
            if (num != 1) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setErrorMsg("修改退款信息失败");
                return dto;
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "修改退款信息失败 error:{}{}", e, JsonEntityTransform.Object2Json(refundEntity));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("修改退款信息失败");
            return dto;
        }
        return dto;
    }



    /**
     * 支付退款回调
     * @author afi
     * @param refundRequest
     * @return
     */
    @Override
    public DataTransferObject<Void> dealRefundBack(RefundRequest refundRequest){
        DataTransferObject<Void>  dto = new DataTransferObject();
        if (Check.NuNObj(refundRequest)){
            dto.setErrorMsg("参数异常");
            return dto;
        }
        if (Check.NuNStr(refundRequest.getRefundSn())){
            dto.setErrorMsg("异常的退款单号");
            return dto;
        }

        if (Check.NuNObjs(refundRequest.getOpName(), refundRequest.getOpUid())){
            dto.setErrorMsg("异常的操作人信息");
            return dto;
        }

        RefundStatusEnum status = RefundStatusEnum.getTypeByCode(refundRequest.getRefundStatus());
        if (Check.NuNObj(status)){
            dto.setErrorMsg("异常的退款状态");
            return dto;
        }

        RefundEntity has = refundManagerImpl.findRefundByCode(refundRequest.getRefundSn());
        if (Check.NuNObj(has)){
            dto.setErrorMsg("不存在");
            return dto;
        }

        RefundStatusEnum statusHas = RefundStatusEnum.getTypeByCode(has.getRefundStatus());
        if (Check.NuNObj(statusHas)){
            LogUtil.error(LOGGER,"异常的退款状态,退款单:{}",JsonEntityTransform.Object2Json(has));
            dto.setErrorMsg("异常的退款状态");
            return dto;
        }
        if (status.getCode() == statusHas.getCode()){
            LogUtil.error(LOGGER,"状态一致,密封返回,par:{}",JsonEntityTransform.Object2Json(refundRequest));
            return dto;
        }
        /**
         * 当前的退款状态
         */
        RefundStatusEnum refundStatus = RefundStatusEnum.getTypeByCode(has.getRefundStatus());
        if (Check.NuNObj(refundStatus)){
            dto.setErrorMsg("异常的退款状态");
            return dto;
        }
        //幂等返回
        if (refundStatus.getCode() == RefundStatusEnum.SUCCESS.getCode()){
            return dto;
        }

        boolean canRefund = false;
        if (refundStatus.getCode() == RefundStatusEnum.PASS.getCode()
                ||refundStatus.getCode() == RefundStatusEnum.SENDING.getCode()
                ||  refundStatus.getCode() == RefundStatusEnum.FAIL.getCode() ){
            canRefund = true;
            refundRequest.setRetryTime(0);
        }
        if (!canRefund){
            dto.setErrorMsg("异常的退款状态");
            return dto;
        }
        try {
            refundManagerImpl.updateRefund(refundRequest,has);
        }catch (Exception e){
            LogUtil.error(LOGGER,"退款失败:par:{}", JsonEntityTransform.Object2Json(refundRequest));
            dto.setErrorMsg("服务错误");
        }
        return dto;

    }

}
