package com.taisf.services.refund.proxy;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.refund.api.RefundService;
import com.taisf.services.refund.dto.RefundRequest;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.refund.service.RefundServiceImpl;
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
    private RefundServiceImpl refundServiceImpl;

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:分页查询退款列表
     **/
    @Override
    public DataTransferObject<PagingResult<RefundVo>> refundPageList(RefundRequest request) {
        DataTransferObject<PagingResult<RefundVo>> dto = new DataTransferObject();
        if (Check.NuNObj(request)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            PagingResult<RefundVo> refundEntityPagingResult = refundServiceImpl.refundPageList(request);
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
            RefundEntity refundEntity = refundServiceImpl.findRefundById(id);
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
            int num = refundServiceImpl.updateRefund(refundEntity);
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
}
