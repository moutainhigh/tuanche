package com.taisf.services.refund.service;

import com.jk.framework.base.page.PagingResult;
import com.taisf.services.refund.dao.RefundDao;
import com.taisf.services.refund.dto.RefundRequest;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.refund.vo.RefundVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author:zhangzhengguang
 * @date:2017/12/21
 * @description:退款
 **/
@Service("refund.refundServiceImpl")
public class RefundServiceImpl {

    @Resource(name = "refund.refundDao")
    private RefundDao refundDao;

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:分页条件查询
     **/
    public PagingResult<RefundVo> refundPageList(RefundRequest request) {
        return refundDao.refundPageList(request);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id查询详情
     **/
    public RefundEntity findRefundById(Integer id) {
        return refundDao.findRefundById(id);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id修改
     **/
    public int updateRefund(RefundEntity refundEntity) {
        return refundDao.updateRefund(refundEntity);
    }
}
