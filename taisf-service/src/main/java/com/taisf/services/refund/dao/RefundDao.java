package com.taisf.services.refund.dao;

import com.jk.framework.base.page.PagingResult;
import com.jk.framework.dao.page.PageBounds;
import com.taisf.services.common.dao.BaseDao;
import com.taisf.services.refund.dto.RefundQueryRequest;
import com.taisf.services.refund.entity.RefundEntity;
import com.taisf.services.refund.vo.RefundVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:zhangzhengguang
 * @date:2017/12/21
 * @description:
 **/
@Repository("refund.refundDao")
public class RefundDao extends BaseDao {

    private String SQLID = "refund.refundDao.";

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(RefundDao.class);


    /**
     * 根据id查询
     * @param refundSn
     * @return
     */
    public RefundEntity findRefundByCode(String refundSn){
        return mybatisDaoContext.findOne(SQLID+"findRefundByCode", RefundEntity.class, refundSn);
    }
    
    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:分页条件查询
     **/
    public PagingResult<RefundVo> refundPageList(RefundQueryRequest request){
        PageBounds page = new PageBounds();
        page.setPage(request.getPage());
        page.setLimit(request.getLimit());
        return mybatisDaoContext.findForPage(SQLID+"refundPageList", RefundVo.class,request,page);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id查询详情
     **/
    public RefundEntity findRefundById(Integer id){
       return  mybatisDaoContext.findOne(SQLID+"selectByPrimaryKey",RefundEntity.class,id);
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/12/21
     * @description:根据id修改
     **/
    public int updateRefund(RefundEntity refundEntity){
        return mybatisDaoContext.update(SQLID + "updateByPrimaryKeySelective", refundEntity);
    }

    /**
     * 确认退款
	 * @param refundSn
	 * @return
     */
    public int updateRefundStatusAndRetryTimes(String refundSn,int toStatus,int oldStatus, int retryTimes) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("refundSn", refundSn);
        params.put("toStatus", toStatus);
        params.put("oldStatus", oldStatus);
        params.put("retryTimes", retryTimes);
        return mybatisDaoContext.update(SQLID+"updateRefundStatusAndRetryTimes", params);
    }

}
